package htcc.employee.service.controller;

import entity.base.BaseResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Employee APIs",
     description = "API của nhân viên")
@RestController
@RequestMapping("/public")
@Log4j2
public class TestController {

    @ApiOperation(value = "Test")
    @GetMapping("/test")
    public BaseResponse home() {
        log.info("test");
        return BaseResponse.SUCCESS;
    }
}
