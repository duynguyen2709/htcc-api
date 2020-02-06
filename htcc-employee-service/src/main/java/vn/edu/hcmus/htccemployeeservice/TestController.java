package vn.edu.hcmus.htccemployeeservice;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@Log4j2
public class TestController {

    @GetMapping("/")
    public String home() {
        log.info("test");
        return "Hello";
    }
}
