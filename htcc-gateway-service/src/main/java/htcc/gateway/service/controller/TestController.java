package htcc.gateway.service.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
    public String hello() {
        log.info("OK");
        return "OK";
    }

}
