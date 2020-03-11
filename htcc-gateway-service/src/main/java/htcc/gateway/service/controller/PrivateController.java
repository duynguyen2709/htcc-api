package htcc.gateway.service.controller;

import htcc.common.constant.ClientSystemEnum;
import htcc.common.constant.Constant;
import htcc.common.constant.ReturnCodeEnum;
import htcc.common.entity.base.BaseResponse;
import htcc.gateway.service.config.file.RedisBuzConfig;
import htcc.gateway.service.entity.jpa.BaseUser;
import htcc.gateway.service.entity.request.ChangePasswordRequest;
import htcc.gateway.service.entity.request.LoginRequest;
import htcc.gateway.service.service.RedisService;
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

    @Autowired
    private JwtTokenService tokenService;

    @Autowired
    private AuthenticationService authService;

    @Autowired
    private RedisService redis;

    @Autowired
    private RedisBuzConfig redisConf;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @ApiOperation(value = "API Logout")
    @PostMapping("/logout/{clientId}")
    public BaseResponse logout(@ApiParam(value = "[Path] clientId", defaultValue = "1", required = true) @PathVariable("clientId") int clientId,
                               @ApiParam(value = "[Query] companyId", required = false) @RequestParam(required = false) String companyId,
                               @ApiParam(value = "[Query] username", required = true) @RequestParam(required = true) String username,
                               @RequestHeader("Authorization") String authorization) {
        BaseResponse response = new BaseResponse(ReturnCodeEnum.SUCCESS);
        try {
            if (!validPermission(authorization, clientId, companyId, username)) {
                log.warn(String.format("Logout failed for client %s | company %s | username %s | token %s",
                        clientId, companyId, username, authorization.substring(7)));
                response = new BaseResponse(ReturnCodeEnum.PERMISSION_DENIED);
            }

            redis.delete(redisConf.tokenFormat, clientId, companyId, username);
        } catch (Exception e) {
            log.error("[logout] ex", e);
            response = new BaseResponse(e);
        }
        return response;
    }

    @ApiOperation(value = "API ChangePassword")
    @PutMapping("/changepassword/{clientId}")
    public BaseResponse changePassword(@ApiParam(value = "[Path] clientId", defaultValue = "1", required = true) @PathVariable("clientId") int clientId,
                                       @ApiParam(value = "[Body] Mật khẩu mới cần update", required = true) @RequestBody ChangePasswordRequest req,
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

            redis.delete(redisConf.tokenFormat, clientId, req.companyId, req.username);
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

    @ApiOperation(value = "Test token")
    @GetMapping("/test")
    public BaseResponse test(){
        log.info("Private Test OK");
        return BaseResponse.SUCCESS;
    }
}
