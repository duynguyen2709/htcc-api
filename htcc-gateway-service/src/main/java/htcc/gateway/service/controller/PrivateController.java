package htcc.gateway.service.controller;

import constant.ReturnCodeEnum;
import entity.base.BaseResponse;
import htcc.gateway.service.service.JwtTokenService;
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

    @ApiOperation(value = "API Logout")
    @PostMapping("/logout/{username}")
    public BaseResponse<Object> logout(@PathVariable("username") String username) {
        BaseResponse response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        try {
            String currentUsername = tokenService.getAuthentication().getName();
            if (!currentUsername.equals(username)){
                response = new BaseResponse(ReturnCodeEnum.PERMISSION_DENIED);
            }

        } catch (Exception e) {
            log.error("[logout] ex", e);
            response = new BaseResponse(e);
        }
        return response;
    }

    @ApiOperation(value = "Test token")
    @GetMapping("/test")
    public BaseResponse test(){
        return BaseResponse.SUCCESS;
    }
}
