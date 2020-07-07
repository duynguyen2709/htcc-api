package htcc.admin.service.controller.order;

import htcc.admin.service.service.jpa.FeatureComboService;
import htcc.admin.service.service.order.OrderService;
import htcc.admin.service.service.rest.EmployeeCompanyService;
import htcc.common.constant.FeatureEnum;
import htcc.common.constant.OrderStatusEnum;
import htcc.common.constant.ReturnCodeEnum;
import htcc.common.entity.base.BaseResponse;
import htcc.common.entity.jpa.Company;
import htcc.common.entity.order.CreateOrderRequest;
import htcc.common.entity.order.DetailOrderModel;
import htcc.common.entity.order.OrderPaymentResponse;
import htcc.common.entity.order.SubmitTransRequest;
import htcc.common.util.DateTimeUtil;
import htcc.common.util.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Log4j2
@Api(tags = "API để tạo đơn hàng")
public class OrderController {

    @Value("${security.hashKey}")
    private String hashKey;

    @Autowired
    private EmployeeCompanyService companyService;

    @Autowired
    private FeatureComboService featureComboService;

    @Autowired
    private OrderService orderService;

    @ApiOperation(value = "Đăng ký sử dụng hệ thống", response = OrderPaymentResponse.class)
    @PostMapping("/public/requestfeature")
    public BaseResponse requestFeature(@RequestBody CreateOrderRequest request) {
        BaseResponse response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        response.setReturnMessage("Một email đã được gửi đến hộp thư của bạn. " +
                "Vui lòng xác nhận để thanh toán cho gói chức năng bạn đã chọn");
        try {
            String error = request.isValid();
            if (!error.isEmpty()) {
                response = new BaseResponse(ReturnCodeEnum.PARAM_DATA_INVALID);
                response.setReturnMessage(error);
                return response;
            }

            if (!request.getRequestedFeatures().isEmpty()) {
                for (FeatureEnum featureEnum : FeatureEnum.values()) {
                    if (!request.getRequestedFeatures().containsKey(featureEnum.getValue())) {
                        request.getRequestedFeatures().put(featureEnum.getValue(), false);
                    }
                }
            }

            error = validateData(request);
            if (!error.isEmpty()) {
                response = new BaseResponse<>(ReturnCodeEnum.PARAM_DATA_INVALID);
                response.setReturnMessage(error);
                return response;
            }

            List<Company> companyList = companyService.getListCompanyModel();
            if (companyList == null) {
                throw new Exception("getListCompanyModel return null");
            }

            Company company = companyList.stream()
                    .filter(c -> c.getCompanyId().equals(request.getCompanyId()))
                    .findAny()
                    .orElse(null);

            if (company != null) {
                response = new BaseResponse(ReturnCodeEnum.DATA_ALREADY_EXISTED);
                response.setReturnMessage(String.format("Mã công ty %s đã tồn tại. Vui lòng thử lại", request.getCompanyId()));
                return response;
            }

            if (!StringUtil.isEmpty(request.getComboId())) {
                if (featureComboService.findById(request.getComboId()) == null) {
                    response = new BaseResponse(ReturnCodeEnum.DATA_NOT_FOUND);
                    response.setReturnMessage("Không tìm thấy gói combo " + request.getComboId());
                    return response;
                }
            }

            orderService.sendMailCreateOrder(request);
        } catch (Exception e) {
            log.error("[requestFeature] {} ex", StringUtil.toJsonString(request), e);
            response = new BaseResponse(e);
        }

        return response;
    }

    @PostMapping("/public/nextpay/{companyId}")
    public BaseResponse nextPay(@PathVariable String companyId) {
        BaseResponse response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        response.setReturnMessage("Một email đã được gửi đến hộp thư của công ty. " +
                "Vui lòng xác nhận để thanh toán");
        try {
            List<Company> companyList = companyService.getListCompanyModel();
            if (companyList == null) {
                throw new Exception("getListCompanyModel return null");
            }

            Company company = companyList.stream()
                    .filter(c -> c.getCompanyId().equals(companyId))
                    .findAny()
                    .orElse(null);

            if (company == null) {
                response = new BaseResponse(ReturnCodeEnum.DATA_NOT_FOUND);
                response.setReturnMessage(String.format("Không tim thấy công ty %s", companyId));
                return response;
            }

            CreateOrderRequest request = new CreateOrderRequest();
            request.setCompanyId(companyId);
            request.setEmail(company.getEmail());
            request.setFirstPay(false);

            orderService.sendMailCreateOrder(request);
        } catch (Exception e) {
            log.error("[requestFeature] [{}] ex", companyId, e);
            response = new BaseResponse(e);
        }

        return response;
    }

    @PostMapping("/public/createorder")
    public BaseResponse createOrder(@RequestBody CreateOrderRequest request, @RequestParam String sig) {
        BaseResponse<DetailOrderModel> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        response.setReturnMessage("Đổi mật khẩu thành công");
        DetailOrderModel dataResponse = null;
        try {
            String error = request.isValid();
            if (!error.isEmpty()) {
                response = new BaseResponse<>(ReturnCodeEnum.PARAM_DATA_INVALID);
                response.setReturnMessage(error);
                return response;
            }

            if (!validateSig(request, sig)) {
                response = new BaseResponse<>(ReturnCodeEnum.PARAM_DATA_INVALID);
                return response;
            }

            error = validateData(request);
            if (!error.isEmpty()) {
                response = new BaseResponse<>(ReturnCodeEnum.PARAM_DATA_INVALID);
                response.setReturnMessage(error);
                return response;
            }

            dataResponse = orderService.createOrder(request);
            response.setData(dataResponse);

        } catch (Exception e) {
            log.error("[createOrder] {}, sig = [{}] ex", StringUtil.toJsonString(request), sig, e);
            response = new BaseResponse<>(e);
        } finally {
            if (response.getReturnCode() == ReturnCodeEnum.SUCCESS.getValue()) {
                orderService.saveOrder(dataResponse);
            }
        }
        return response;
    }

    private String validateData(CreateOrderRequest request) {
        if (request.isFirstPay() && StringUtil.isEmpty(request.getComboId())) {
            Map<String, Object> supportedFeatures = request.getRequestedFeatures();
            try {
                for (FeatureEnum featureEnum : FeatureEnum.values()) {
                    if (!supportedFeatures.containsKey(featureEnum.getValue())) {
                        return "Thiếu thông tin tính năng " + featureEnum.getValue() + " trong gói";
                    }

                    if (!featureEnum.getValue().equals(FeatureEnum.EMPLOYEE_MANAGE.getValue())) {
                        boolean isActive = Boolean.parseBoolean(StringUtil.valueOf(supportedFeatures.get(featureEnum.getValue())));
                    }
                }

                Object value = supportedFeatures.get(FeatureEnum.EMPLOYEE_MANAGE.getValue());
                int    numEmployees = (int) (Float.parseFloat(String.valueOf(value)));
                if (numEmployees <= 0) {
                    return "Số nhân viên trong gói phải lớn hơn 0";
                }
            } catch (Exception e) {
                log.error("[validateData] {} ex", StringUtil.toJsonString(supportedFeatures), e);
                return "Thông tin trong gói không hợp lệ";
            }
        }

        if (!StringUtil.isEmpty(request.getOrderId())) {
            String otherFormat = DateTimeUtil.convertToOtherFormat(request.getOrderId().substring(0, 6), "yyMMdd", "yyyyMMdd");
            if (DateTimeUtil.isBeforeToday(otherFormat)) {
                return "Đơn hàng không hợp lệ hoặc đã quá hạn. Vui lòng tạo lại đơn hàng khác";
            }

            DetailOrderModel order = orderService.getOrder(request.getOrderId());
            if (order != null && order.getOrderStatus() != OrderStatusEnum.CREATED.getValue()) {
                return "Đơn hàng đã được xử lý trước đó. Vui lòng tạo lại đơn hàng khác";
            }
        }

        return StringUtil.EMPTY;
    }

    private boolean validateSig(CreateOrderRequest request, String sig) throws Exception {
        if (StringUtil.isEmpty(sig)) {
            throw new Exception("clientSig is empty");
        }

        String serverSig = StringUtil.hashSHA256(String.format("%s|%s", StringUtil.toJsonString(request), hashKey));

        if (!serverSig.equals(sig)) {
            log.error("\nserverSig [{}] != clientSig [{}]\n", serverSig, sig);
        }

        return serverSig.equals(sig);
    }

    @PostMapping("/public/submittrans")
    public BaseResponse submitTrans(@RequestBody SubmitTransRequest request, @RequestParam String sig) {
        BaseResponse response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        response.setReturnMessage("Thanh toán thành công. Vui lòng chờ quản trị viên xác nhận");
        try {
            String error = request.isValid();
            if (!error.isEmpty()) {
                response = new BaseResponse<>(ReturnCodeEnum.PARAM_DATA_INVALID);
                response.setReturnMessage(error);
                return response;
            }

            if (!validateSig(request, sig)) {
                response = new BaseResponse<>(ReturnCodeEnum.PARAM_DATA_INVALID);
                return response;
            }

            error = validateData(request);
            if (!error.isEmpty()) {
                response = new BaseResponse<>(ReturnCodeEnum.PARAM_DATA_INVALID);
                response.setReturnMessage(error);
                return response;
            }

            orderService.submitTrans(request);

        } catch (Exception e) {
            log.error("[submitTrans] {}, sig = [{}] ex", StringUtil.toJsonString(request), sig, e);
            response = new BaseResponse<>(e);
        }
        return response;
    }

    private String validateData(SubmitTransRequest request) {
        DetailOrderModel order = orderService.getOrder(request.getOrderId());
        if (order == null) {
            return "Đơn hàng không hợp lệ hoặc đã quá hạn. Vui lòng tạo lại đơn hàng khác";
        }

        if (order.getOrderStatus() != OrderStatusEnum.CREATED.getValue()) {
            return "Đơn hàng đã được xử lý trước đó";
        }
        return StringUtil.EMPTY;
    }

    private boolean validateSig(SubmitTransRequest request, String sig) throws Exception {
        if (StringUtil.isEmpty(sig)) {
            throw new Exception("clientSig is empty");
        }

        String serverSig = StringUtil.hashSHA256(String.format("%s|%s", request.getOrderId(), hashKey));

        if (!serverSig.equals(sig)) {
            log.error("\nserverSig [{}] != clientSig [{}]\n", serverSig, sig);
        }

        return serverSig.equals(sig);
    }
}
