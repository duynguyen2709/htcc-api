package htcc.gateway.service.controller;

import htcc.common.component.kafka.KafkaProducerService;
import htcc.common.component.redis.RedisService;
import htcc.common.constant.ClientSystemEnum;
import htcc.common.constant.Constant;
import htcc.common.constant.ReturnCodeEnum;
import htcc.common.entity.base.BaseResponse;
import htcc.common.entity.notification.NotificationBuz;
import htcc.common.util.StringUtil;
import htcc.gateway.service.config.file.SecurityConfig;
import htcc.common.entity.base.BaseUser;
import htcc.common.entity.request.ChangePasswordRequest;
import htcc.common.entity.request.LoginRequest;
import htcc.gateway.service.service.authentication.AuthenticationService;
import htcc.gateway.service.service.authentication.JwtTokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@Api(tags = "Private APIs",
     value = "PrivateController",
     description = "API private để đổi mật khẩu, đăng xuất.\n(cần gửi token trong header" +
            " Authorization: Bearer \"token\")")
@RestController
@Log4j2
@RequestMapping("/private")
public class PrivateController {

    //<editor-fold defaultstate="collapsed" desc="Autowired">
    @Autowired
    private JwtTokenService tokenService;

    @Autowired
    private AuthenticationService authService;

    @Autowired
    private RedisService redis;

    @Autowired
    private SecurityConfig securityConfig;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private KafkaProducerService kafka;
    //</editor-fold>

    @ApiOperation(value = "API Logout")
    @PostMapping("/logout")
    public BaseResponse logout(@RequestHeader("Authorization") String authorization,
                               @RequestParam(required = false) String tokenPush) {
        BaseResponse response = new BaseResponse(ReturnCodeEnum.SUCCESS);
        String token = authorization.substring(Constant.BEARER.length());
        LoginRequest loginInfo = null;
        try {
            loginInfo = tokenService.getLoginInfo(token);
            loginInfo.setTokenPush(StringUtil.valueOf(tokenPush));

            redis.delete(redis.buzConfig.tokenFormat,
                    loginInfo.clientId,
                    StringUtil.valueOf(loginInfo.companyId),
                    loginInfo.username);

        } catch (Exception e) {
            log.error("[logout] ex", e);
            response = new BaseResponse(e);
        } finally {
            // blacklist token
            if (response.returnCode == ReturnCodeEnum.SUCCESS.getValue()) {
                if (loginInfo != null) {

                    blackListToken(token, loginInfo);

                    sendKafkaLogOut(loginInfo);
                }
            }
        }
        return response;
    }

    private void sendKafkaLogOut(LoginRequest loginInfo) {
        NotificationBuz notiEntity = new NotificationBuz();
        notiEntity.setClientId(loginInfo.getClientId());
        notiEntity.setCompanyId(StringUtil.valueOf(loginInfo.getCompanyId()));
        notiEntity.setUsername(loginInfo.getUsername());
        notiEntity.setTokens(loginInfo.getTokenPush());
        notiEntity.setIsLoggedIn(0);

        kafka.sendMessage(kafka.getBuzConfig().getEventChangeLogInStatus().getTopicName(), notiEntity);
    }

    private void blackListToken(String token, LoginRequest loginInfo) {
        long ttl = 0;
        if (loginInfo.clientId != ClientSystemEnum.MOBILE.getValue()) {
            ttl = securityConfig.jwt.expireSecond;
        } else {
            ttl = securityConfig.jwt.expireSecond * 7;
        }

        redis.set(token, ttl, redis.buzConfig.blacklistTokenFormat,
                loginInfo.clientId, StringUtil.valueOf(loginInfo.companyId), loginInfo.username);

        // delete old token
        redis.delete(redis.buzConfig.tokenFormat,
                loginInfo.clientId, StringUtil.valueOf(loginInfo.companyId), loginInfo.username);
    }

    @ApiOperation(value = "API ChangePassword")
    @PutMapping("/changepassword/{clientId}")
    public BaseResponse changePassword(@ApiParam(value = "[Path] clientId", defaultValue = "1", required = true)
                                           @PathVariable("clientId") int clientId,
                                       @ApiParam(value = "[Body] Mật khẩu mới cần update", required = true)
                                       @RequestBody ChangePasswordRequest req,
                                       @RequestHeader("Authorization") String authorization) {
        BaseResponse response = new BaseResponse(ReturnCodeEnum.SUCCESS);
        String token = authorization.substring(Constant.BEARER.length());
        try {
            if (!validPermission(token, clientId, req.companyId, req.username)) {
                log.warn(String.format("changePassword failed for client %s | company %s | username %s | token %s",
                        clientId, req.companyId, req.username, token));
                return new BaseResponse(ReturnCodeEnum.PERMISSION_DENIED);
            }

            BaseUser user = authService.getUser(req);
            if (user == null) {
                return new BaseResponse(ReturnCodeEnum.USER_NOT_FOUND);
            }

            if (!passwordEncoder.matches(req.oldPassword, user.password)) {
                return new BaseResponse(ReturnCodeEnum.OLD_PASSWORD_NOT_MATCH);
            }

            if (req.oldPassword.equals(req.newPassword)) {
                return new BaseResponse(ReturnCodeEnum.NEW_PASSWORD_MUST_DIFFER_FROM_OLD_PASSWORD);
            }

            if (!authService.updatePassword(req)) {
                return new BaseResponse(ReturnCodeEnum.EXCEPTION);
            }

            redis.delete(redis.buzConfig.tokenFormat, clientId, req.companyId, req.username);
        } catch (Exception e) {
            log.error("[changePassword] ex", e);
            response = new BaseResponse(e);
        }
        return response;
    }

    private boolean validPermission(String token, int clientId, String companyId, String username) {
        LoginRequest loginInfo = tokenService.getLoginInfo(token);

        ClientSystemEnum e = ClientSystemEnum.fromInt(clientId);
        switch (e) {
            case MOBILE:
            case MANAGER_WEB:
                return (loginInfo.companyId.equals(companyId) && loginInfo.username.equals(username));

            case ADMIN_WEB:
                return (loginInfo.username.equals(username));
        }

        return false;
    }
}
