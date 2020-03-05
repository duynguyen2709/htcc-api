package htcc.gateway.service.controller;

import entity.base.BaseResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Pirivate APIs",
     value = "PrivateController",
     description = "API private để test send token, đăng xuất.")
@RestController
@Log4j2
@RequestMapping("/private")
public class PrivateController {

    @ApiOperation(value = "Test token (cần gửi token trong header " +
            "Authorization: Bearer <token>")
    @GetMapping("/test")
    public BaseResponse test(){
        return BaseResponse.SUCCESS;
    }
}
