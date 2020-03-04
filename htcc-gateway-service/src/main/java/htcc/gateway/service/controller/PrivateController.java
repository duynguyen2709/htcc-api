package htcc.gateway.service.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
@RequestMapping("/private")
public class PrivateController {

    @GetMapping("/test")
    public String test(){
        log.info("PrivateController");
        return "PrivateController";
    }
}
