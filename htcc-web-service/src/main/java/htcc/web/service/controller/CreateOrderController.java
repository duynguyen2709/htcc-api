package htcc.web.service.controller;

import htcc.web.service.entity.BaseResponse;
import htcc.web.service.entity.CreateOrderRequest;
import htcc.web.service.enums.ReturnCodeEnum;
import htcc.web.service.service.CreateOrderService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
public class CreateOrderController {

    @Autowired
    private CreateOrderService createOrderService;

    @PostMapping("/api/createorder")
    public BaseResponse createOrder(@RequestBody CreateOrderRequest request) {
        BaseResponse response = new BaseResponse(ReturnCodeEnum.SUCCESS);
        try {
            return createOrderService.createOrder(request);
        } catch (Exception e) {
            log.error("[createOrder] {} ex", request.toString(), e);
            return new BaseResponse(ReturnCodeEnum.EXCEPTION);
        }
    }
}
