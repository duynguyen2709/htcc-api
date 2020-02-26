package htcc.gateway.service.controller;

import htcc.gateway.service.entity.base.BaseResponse;
import htcc.gateway.service.entity.request.LoginRequest;
import htcc.gateway.service.util.StringUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

@RestController
@Log4j2
@RequestMapping("/public")
public class GatewayController {

    @GetMapping("/hello/{var}")
    public String hello(@PathVariable String var) {
        log.info("test");
        return var;
    }

    @PostMapping("/login")
    public BaseResponse login(@RequestBody LoginRequest request) {
        BaseResponse response = BaseResponse.SUCCESS;
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
