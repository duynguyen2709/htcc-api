package htcc.gateway.service.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
public class GatewayController {

    @GetMapping("/hello/{var}")
    public String hello(@PathVariable String var){
        log.info("test");
        return var;
    }
}
