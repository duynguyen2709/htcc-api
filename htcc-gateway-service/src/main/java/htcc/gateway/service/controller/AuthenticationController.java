package htcc.gateway.service.controller;

import htcc.common.constant.ClientSystemEnum;
import htcc.common.constant.ReturnCodeEnum;
import htcc.common.entity.base.BaseResponse;
import htcc.common.util.StringUtil;
import htcc.gateway.service.entity.request.LoginRequest;
import htcc.gateway.service.entity.response.LoginResponse;
import htcc.gateway.service.feign.AdminServiceClient;
import htcc.gateway.service.feign.EmployeeServiceClient;
import htcc.gateway.service.service.authentication.JwtTokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Login API (Không cần gửi token trong header)",
     value = "AuthenticationController",
     description = "API public để đăng nhập")
@RestController
@Log4j2
@RequestMapping("/public")
public class AuthenticationController {

    //<editor-fold defaultstate="collapsed" desc="Autowired">
    @Autowired
    private AuthenticationManager authenManager;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private UserDetailsService jwtUserDetailService;

    @Autowired
    private AdminServiceClient adminClient;

    @Autowired
    private EmployeeServiceClient employeeClient;
    //</editor-fold>

    @ApiOperation(value = "Đăng nhập")
    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<LoginResponse> login(@RequestBody LoginRequest request) {
        BaseResponse<LoginResponse> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        try {
            authenManager.authenticate(new UsernamePasswordAuthenticationToken(StringUtil.toJsonString(request), request.password));

            LoginResponse data = new LoginResponse();
            data.token = jwtTokenService.getToken(request);
            data.user = getUserInfo(request);

            response.data = data;

        } catch (BadCredentialsException e) {
            // wrong password
            response = new BaseResponse<>(ReturnCodeEnum.WRONG_USERNAME_OR_PASSWORD);

        } catch (LockedException e) {
            // account is locked
            log.warn(String.format("[login] Account Locked [%s-%s-%s]", request.clientId, request.companyId, request.username));
            response = new BaseResponse<>(ReturnCodeEnum.ACCOUNT_LOCKED);

        } catch (Exception e) {
            log.error("[login] ex", e);
            response = new BaseResponse<>(e);
        }

        return response;
    }

    private Object getUserInfo(LoginRequest request){
        BaseResponse response = new BaseResponse(ReturnCodeEnum.SUCCESS);
        ClientSystemEnum e = ClientSystemEnum.fromInt(request.clientId);
        switch (e) {
            case ADMIN_WEB:
                response = adminClient.getUserInfo(request.username);
                break;
            case MOBILE:
            case MANAGER_WEB:
                response = employeeClient.getUserInfo(request.companyId, request.username);
                break;
            default:
                return null;
        }
        return (response != null) ? response.data : null;
    }

}
