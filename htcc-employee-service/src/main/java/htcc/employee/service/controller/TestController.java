package htcc.employee.service.controller;

import htcc.common.entity.base.BaseResponse;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
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
