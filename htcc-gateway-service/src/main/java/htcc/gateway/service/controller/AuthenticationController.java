package htcc.gateway.service.controller;

import htcc.gateway.service.component.authentication.JwtTokenUtil;
import htcc.gateway.service.constant.ReturnCodeEnum;
import htcc.gateway.service.entity.base.BaseResponse;
import htcc.gateway.service.entity.request.LoginRequest;
import htcc.gateway.service.entity.response.LoginResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@Api(tags = "Gateway APIs",
     value = "AuthenticationController",
     description = "API public để đăng nhập, đăng xuất.")
@RestController
@Log4j2
@RequestMapping("/public")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService jwtInMemoryUserDetailsService;

    @ApiOperation(value = "Đăng nhập")
    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<LoginResponse> login(@RequestBody LoginRequest request) {
        BaseResponse<LoginResponse> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        try {
            authenManager.authenticate(new UsernamePasswordAuthenticationToken(request.username, request.password));

            UserDetails userDetails = jwtInMemoryUserDetailsService
                    .loadUserByUsername(request.getUsername());

            String token = jwtTokenUtil.generateToken(userDetails);

            response.data = new LoginResponse(token);
            return response;
        } catch (Exception e) {
            log.error("[login] ex", e);
            return new BaseResponse<LoginResponse>(e);
        }
    }

    @GetMapping("/hello/{var}")
    public String hello(@PathVariable String var) {
        log.info("test");
        return var;
    }

}
