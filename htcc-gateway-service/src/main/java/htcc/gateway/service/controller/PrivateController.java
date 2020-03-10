package htcc.gateway.service.controller;

import htcc.common.constant.ClientSystemEnum;
import htcc.common.constant.ReturnCodeEnum;
import htcc.common.entity.base.BaseResponse;
import htcc.gateway.service.config.file.RedisBuzConfig;
import htcc.gateway.service.entity.request.LoginRequest;
import htcc.gateway.service.service.RedisService;
import htcc.gateway.service.service.authentication.JwtTokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "Private APIs",
     value = "PrivateController",
     description = "API private để test send token, đăng xuất.\n(cần gửi token trong header" +
            " Authorization: Bearer \"token\")")
@RestController
@Log4j2
@RequestMapping("/private")
public class PrivateController {

    @Autowired
    private JwtTokenService tokenService;

    @Autowired
    private RedisService redis;

    @Autowired
    private RedisBuzConfig redisConf;

    @ApiOperation(value = "API Logout")
    @PostMapping("/logout/{clientId}")
    public BaseResponse logout(@PathVariable("clientId") int clientId,
                                       @RequestParam(required = false) String companyId,
                                       @RequestParam(required = true) String username,
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

    private boolean validPermission(String authorization, int clientId, String companyId, String username) {
        String           token     = authorization.substring(7);
        LoginRequest     loginInfo = tokenService.getLoginInfo(token);
        ClientSystemEnum e         = ClientSystemEnum.fromInt(clientId);
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
