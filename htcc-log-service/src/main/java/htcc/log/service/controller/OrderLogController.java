package htcc.log.service.controller;

import htcc.common.constant.OrderStatusEnum;
import htcc.common.constant.ReturnCodeEnum;
import htcc.common.entity.base.BaseResponse;
import htcc.common.entity.order.DetailOrderModel;
import htcc.common.entity.order.OrderLogEntity;
import htcc.common.entity.order.OrderPaymentResponse;
import htcc.common.entity.order.UpdateOrderStatusModel;
import htcc.common.util.StringUtil;
import htcc.log.service.entity.jpa.LogCounter;
import htcc.log.service.repository.LogCounterRepository;
import htcc.log.service.repository.OrderLogRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Log4j2
@RequestMapping("/internal/logs")
public class OrderLogController {

    @Autowired
    private OrderLogRepository orderLogRepo;

    @Autowired
    private LogCounterRepository logCounterRepo;

    @GetMapping("/orders/{yyyyMM}")
    public BaseResponse getOrderLog(@PathVariable String yyyyMM){
        BaseResponse<List<OrderPaymentResponse>> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        try {
            List<OrderLogEntity> data = orderLogRepo.getOrderLog(yyyyMM);
            if (data == null) {
                return new BaseResponse(ReturnCodeEnum.LOG_NOT_FOUND);
            }

            List<DetailOrderModel> modelList = data.stream()
                    .filter(c -> c.getOrderStatus() != OrderStatusEnum.CREATED.getValue() &&
                            c.getOrderStatus() != OrderStatusEnum.DELIVERING.getValue())
                    .map(DetailOrderModel::new)
                    .collect(Collectors.toList());

            List<OrderPaymentResponse> dataResponse = modelList.stream()
                                       .map(OrderPaymentResponse::new)
                    .collect(Collectors.toList());

            response.setData(dataResponse);
        } catch (Exception e) {
            log.error(String.format("[getOrderLog] [%s] ex", yyyyMM), e);
            return new BaseResponse(e);
        }
        return response;
    }





    @GetMapping("/orders/count")
    public BaseResponse countPendingOrderLog(){
        BaseResponse response = new BaseResponse(ReturnCodeEnum.SUCCESS);
        int count = 0;
        try {
            final String logType = "OrderLog";
            String params = "";

            List<LogCounter> list = logCounterRepo.findByLogTypeAndParams(logType, params);
            if (!list.isEmpty()) {
                for (LogCounter counter : list) {
                    count += counter.count;
                }
            }

            response.data = count;
        } catch (Exception e) {
            log.error("[countPendingOrderLog] ex", e);
            return new BaseResponse(e);
        }
        return response;
    }




    // update complaint status, call by web
    @PostMapping("/orders/status")
    public BaseResponse updateOrderStatus(@RequestBody UpdateOrderStatusModel request){
        BaseResponse response = new BaseResponse(ReturnCodeEnum.SUCCESS);
        response.setReturnMessage("Xử lý đơn hàng thành công");

        try {
            OrderLogEntity oldEnt = orderLogRepo.getOrder(request);
            if (oldEnt == null) {
                log.warn("[orderLogRepo.getOrder] {} return null", StringUtil.toJsonString(request));
                return new BaseResponse<>(ReturnCodeEnum.LOG_NOT_FOUND);
            }

            if (oldEnt.getOrderStatus() == OrderStatusEnum.SUCCESS.getValue() ||
                    oldEnt.getOrderStatus() == OrderStatusEnum.FAILED.getValue()) {
                log.warn("[orderLogRepo.getOrder] oldEntity {}: status = [{}]", oldEnt.getOrderId(), oldEnt.getOrderStatus());
                response = new BaseResponse<>(ReturnCodeEnum.PARAM_DATA_INVALID);
                response.setReturnMessage("Đơn hàng đã được xử lý trước đó");
                return response;
            }

            orderLogRepo.updateOrderLogStatus(request);
        } catch (Exception e){
            log.error(String.format("[updateOrderStatus] %s ex", StringUtil.toJsonString(request)), e);
            return new BaseResponse(e);
        }

        return response;
    }
}
