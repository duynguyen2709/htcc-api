package htcc.admin.service.controller.order;

import htcc.admin.service.service.order.OrderService;
import htcc.admin.service.service.rest.LogService;
import htcc.common.constant.OrderStatusEnum;
import htcc.common.constant.ReturnCodeEnum;
import htcc.common.entity.base.BaseResponse;
import htcc.common.entity.order.OrderPaymentResponse;
import htcc.common.entity.order.UpdateOrderStatusModel;
import htcc.common.util.DateTimeUtil;
import htcc.common.util.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "API để xử lý đơn hàng")
@RestController
@Log4j2
public class UpdateOrderController {

    @Autowired
    private LogService logService;

    @Autowired
    private OrderService orderService;

    @ApiOperation(value = "Lấy danh sách đơn hàng", response = OrderPaymentResponse.class)
    @GetMapping("/orders/{month}")
    public BaseResponse getListOrder(@ApiParam(value = "[Path] Tháng (yyyyMM)", required = true)
                                         @PathVariable String month) {
        BaseResponse<List<OrderPaymentResponse>> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        try {
            String yyyyMM = StringUtil.valueOf(month);
            if (!DateTimeUtil.isRightFormat(yyyyMM, "yyyyMM")) {
                return new BaseResponse<>(ReturnCodeEnum.DATE_WRONG_FORMAT, String.format("Tháng %s không phù hợp định dạng yyyyMM", month));
            }

            return logService.getListOrderLogByMonth(yyyyMM);
        } catch (Exception e) {
            log.error(String.format("getListOrder [%s] ex", month), e);
            response = new BaseResponse<>(e);
        }
        return response;
    }




    @ApiOperation(value = "Cập nhật trạng thái đơn hàng", response = BaseResponse.class)
    @PutMapping("/orders/status")
    public BaseResponse updateOrderStatus(@ApiParam(value = "[Body] Trạng thái mới cần update", required = true)
                                                  @RequestBody UpdateOrderStatusModel request) {
        BaseResponse response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        try {
            if (request.getStatus() != OrderStatusEnum.SUCCESS.getValue() &&
                    request.getStatus() != OrderStatusEnum.FAILED.getValue()) {
                return new BaseResponse(ReturnCodeEnum.PARAM_DATA_INVALID, String.format("Trạng thái %s không hợp lệ", request.getStatus()));
            }

            response = logService.updateOrderStatus(request);
        } catch (Exception e) {
            log.error(String.format("[updateOrderStatus] [%s] ex", StringUtil.toJsonString(request)), e);
            response = new BaseResponse<>(e);
        } finally {
            if (response != null && response.getReturnCode() == ReturnCodeEnum.SUCCESS.getValue()) {
                orderService.updateOrderStatus(request.getOrderId(), request.getStatus());
            }
        }
        return response;
    }
}
