package htcc.gateway.service.controller;

import htcc.gateway.service.entity.base.BaseResponse;
import htcc.gateway.service.entity.request.LoginRequest;
import htcc.gateway.service.entity.response.LoginResponse;
import htcc.gateway.service.util.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@Log4j2
@RequestMapping("/public")
@Api(tags = "Gateway APIs",
     value = "GatewayController",
     description = "API public để đăng nhập, đăng xuất. \n Authen bằng clientid + sig")
public class GatewayController {

    @GetMapping("/hello/{var}")
    public String hello(@PathVariable String var) {
        log.info("test");
        return var;
    }

    @ApiOperation(value = "Đăng nhập")
    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<LoginResponse> login(@RequestBody LoginRequest request) {
        BaseResponse<LoginResponse> response = BaseResponse.SUCCESS;
        try {
            log.info(StringUtil.toJsonString(request));
            return response;
        }
        catch (Exception e) {
            log.error("[login] ex", e);
            return new BaseResponse(e);
        }
    }
}
