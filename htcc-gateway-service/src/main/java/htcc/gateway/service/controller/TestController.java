package htcc.gateway.service.controller;

import entity.base.BaseResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RestController
@Log4j2
@RequestMapping("/public")
public class TestController {

    @GetMapping("/test")
    public BaseResponse test() {
        log.info("OK");
        return BaseResponse.SUCCESS;
    }

}
