package htcc.admin.service.service.order;

import htcc.admin.service.controller.CompanyUserController;
import htcc.admin.service.service.jpa.CustomerOrderService;
import htcc.admin.service.service.jpa.FeatureComboService;
import htcc.admin.service.service.jpa.FeaturePriceService;
import htcc.admin.service.service.rest.EmployeeCompanyService;
import htcc.admin.service.service.rest.EmployeeInfoService;
import htcc.admin.service.service.rest.GatewayCompanyUserService;
import htcc.common.component.kafka.KafkaProducerService;
import htcc.common.component.redis.RedisService;
import htcc.common.constant.FeatureEnum;
import htcc.common.constant.OrderStatusEnum;
import htcc.common.constant.PaymentCycleTypeEnum;
import htcc.common.constant.ReturnCodeEnum;
import htcc.common.entity.base.BaseResponse;
import htcc.common.entity.companyuser.CompanyUserModel;
import htcc.common.entity.feature.FeatureCombo;
import htcc.common.entity.feature.FeaturePrice;
import htcc.common.entity.jpa.Company;
import htcc.common.entity.order.*;
import htcc.common.util.DateTimeUtil;
import htcc.common.util.StringUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;

@Service
@Log4j2
public class OrderService {

    @Autowired
    private KafkaProducerService kafka;

    @Autowired
    private CustomerOrderService customerOrderService;

    @Autowired
    private FeatureComboService featureComboService;

    @Autowired
    private FeaturePriceService featurePriceService;

    @Autowired
    private EmployeeCompanyService companyService;

    @Autowired
    private CompanyUserController companyUserController;

    @Autowired
    private RedisService redis;

    public void saveOrder(DetailOrderModel order) {
        redis.set(StringUtil.toJsonString(order), DateTimeUtil.getSecondUntilEndOfDay() + 86400,
                redis.buzConfig.getOrderFormat(), order.getOrderId());
    }

    public DetailOrderModel getOrder(String orderId) {
        String rawValue = StringUtil.valueOf(redis.get(redis.buzConfig.getOrderFormat(), orderId));
        if (rawValue.isEmpty()) {
            return null;
        }
        return StringUtil.fromJsonString(rawValue, DetailOrderModel.class);
    }

    public void sendMailCreateOrder(CreateOrderRequest request) {
        request.setOrderId(genOrderId());
        kafka.sendMessage(kafka.getBuzConfig().getEventCreateOrder().getTopicName(), request);
    }

    public DetailOrderModel createOrder(CreateOrderRequest request) throws Exception {
        DetailOrderModel model = null;
        if (request.isFirstPay()) {
            model = createFirstOrder(request);
        }
        else {
            CustomerOrder order = customerOrderService.findById(request.getCompanyId());
            model = new DetailOrderModel(order);
        }
        String date = DateTimeUtil.parseTimestampToString(System.currentTimeMillis(), "yyyy-MM-dd");
        model.setDate(date);
        model.setOrderId(request.getOrderId());
        return model;
    }

    private String genOrderId() {
        String orderId = StringUtil.valueOf(redis.get(redis.buzConfig.getOrderIdFormat()));
        if (orderId.isEmpty()) {
            orderId = String.format("%s0001", DateTimeUtil.parseTimestampToString(System.currentTimeMillis(), "yyMMdd"));
        }

        long nextOrderId = Long.parseLong(orderId) + 1;
        redis.set(StringUtil.valueOf(nextOrderId), DateTimeUtil.getSecondUntilEndOfDay(), redis.buzConfig.getOrderIdFormat());

        return orderId;
    }

    private DetailOrderModel createFirstOrder(CreateOrderRequest request) throws Exception {
        DetailOrderModel model = new DetailOrderModel();
        model.setCompanyId(request.getCompanyId());
        model.setEmail(request.getEmail());
        model.setFirstPay(true);
        model.setSupportedFeatures(new ArrayList<>());
        model.setOrderStatus(OrderStatusEnum.CREATED.getValue());

        if (!StringUtil.isEmpty(request.getComboId())) {
            FeatureCombo combo = featureComboService.findById(request.getComboId());
            if (combo == null) {
                throw new Exception("featureComboService.findById return null");
            }
            model.setComboDetail(combo);
            model.setDiscountPercentage(combo.getDiscountPercentage());
            int numEmployees = (int)(Float.parseFloat(String.valueOf(
                    combo.getComboDetail().get(FeatureEnum.EMPLOYEE_MANAGE.getValue()))));
            long totalPrice = 0L;
            for (Map.Entry<String, Object> entry : combo.getComboDetail().entrySet()) {
                FeaturePrice featurePrice = featurePriceService.findById(entry.getKey());
                if (featurePrice == null) {
                    throw new Exception("featurePriceService.findById return null");
                }

                if (entry.getKey().equals(FeatureEnum.EMPLOYEE_MANAGE.getValue())) {
                    totalPrice += featurePrice.getUnitPrice() * numEmployees;
                }
                else {
                    boolean isActive = Boolean.parseBoolean(String.valueOf(entry.getValue()));
                    if (isActive) {
                        if (featurePrice.getCalcByEachEmployee() == 1) {
                            totalPrice += featurePrice.getUnitPrice() * numEmployees;
                        }
                        else {
                            totalPrice += featurePrice.getUnitPrice();
                        }
                    }
                }

                DetailOrderModel.SupportedFeature supportedFeature = new DetailOrderModel.SupportedFeature();
                supportedFeature.setFeature(featurePrice);
                supportedFeature.setValue(entry.getValue());

                model.getSupportedFeatures().add(supportedFeature);
            }
            totalPrice -= (long)(totalPrice * combo.getDiscountPercentage() / 100);
            model.setTotalPrice(totalPrice);
        }
        else {
            model.setComboDetail(null);
            model.setDiscountPercentage(0.0f);

            Map<String, Object> detail = request.getRequestedFeatures();
            int numEmployees = (int)(Float.parseFloat(String.valueOf(detail.get(FeatureEnum.EMPLOYEE_MANAGE.getValue()))));
            long totalPrice = 0L;
            for (Map.Entry<String, Object> entry : detail.entrySet()) {
                FeaturePrice featurePrice = featurePriceService.findById(entry.getKey());
                if (featurePrice == null) {
                    throw new Exception("featurePriceService.findById return null");
                }

                if (entry.getKey().equals(FeatureEnum.EMPLOYEE_MANAGE.getValue())) {
                    totalPrice += featurePrice.getUnitPrice() * numEmployees;
                }
                else {
                    boolean isActive = Boolean.parseBoolean(String.valueOf(entry.getValue()));
                    if (isActive) {
                        if (featurePrice.getCalcByEachEmployee() == 1) {
                            totalPrice += featurePrice.getUnitPrice() * numEmployees;
                        }
                        else {
                            totalPrice += featurePrice.getUnitPrice();
                        }
                    }
                }

                DetailOrderModel.SupportedFeature supportedFeature = new DetailOrderModel.SupportedFeature();
                supportedFeature.setFeature(featurePrice);
                supportedFeature.setValue(entry.getValue());

                model.getSupportedFeatures().add(supportedFeature);
            }
            model.setTotalPrice(totalPrice);
        }
        return model;
    }

    public void submitTrans(SubmitTransRequest request) {
        DetailOrderModel order = getOrder(request.getOrderId());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String today = DateTimeUtil.parseTimestampToString(System.currentTimeMillis(), "yyyyMMdd");
        LocalDate startDate = LocalDate.parse(today, formatter);
        if (!StringUtil.isEmpty(order.getNextPaymentDate())) {
            startDate = LocalDate.parse(order.getNextPaymentDate(), formatter);
        }
        LocalDate endDate = startDate.plusDays(request.getPaymentCycleType() == PaymentCycleTypeEnum.MONTHLY.getValue() ?
                startDate.lengthOfMonth() : startDate.lengthOfYear());
        String nextPaymentDate = formatter.format(endDate);

        order.setPaymentName(request.getPaymentName());
        order.setPaymentId(request.getPaymentId());
        order.setPaymentCycleType(request.getPaymentCycleType());
        order.setPaymentTime(System.currentTimeMillis());
        order.setLastPaymentDate(today);
        order.setNextPaymentDate(nextPaymentDate);
        order.setOrderStatus(OrderStatusEnum.PAY_SUCCESS.getValue());

        kafka.sendMessage(kafka.getBuzConfig().getEventSubmitTrans().getTopicName(), order);

        saveOrder(order);
    }

    public void updateOrderStatus(String orderId, int orderStatus) {
        DetailOrderModel model = null;
        try {
            model = getOrder(orderId);
            model.setOrderStatus(orderStatus);

            if (model.getOrderStatus() == OrderStatusEnum.SUCCESS.getValue()) {
                delivery(model);
            }
        } catch (Exception e) {
            log.error("[updateOrderStatus] {} ex", StringUtil.toJsonString(model), e);
        }
    }

    private void delivery(DetailOrderModel model) {
        try {
            if (model.isFirstPay()) {
                CustomerOrder customerOrder = new CustomerOrder(model);
                customerOrderService.create(customerOrder);

                // create company
                Company company = new Company();
                company.setCompanyId(model.getCompanyId());
                company.setEmail(model.getEmail());
                company.setStatus(1);
                company.setPhoneNumber(StringUtil.EMPTY);
                company.setCompanyName(StringUtil.EMPTY);
                company.setAddress(StringUtil.EMPTY);
                BaseResponse companyResponse = companyService.createCompany(company);
                log.info(StringUtil.toJsonString(companyResponse));
                if (companyResponse != null && companyResponse.getReturnCode() == ReturnCodeEnum.SUCCESS.getValue()) {
                    CompanyUserModel user = new CompanyUserModel();
                    user.setCompanyId(model.getCompanyId());
                    user.setEmail(model.getEmail());
                    user.setUsername("admin");
                    user.setPassword("123456");
                    user.setPhoneNumber(StringUtil.EMPTY);
                    user.setStatus(1);

                    BaseResponse userResponse = companyUserController.createCompanyUser(user);
                    log.info(StringUtil.toJsonString(userResponse));
                }
            }
            else {
                CustomerOrder customerOrder = customerOrderService.findById(model.getCompanyId());
                customerOrder.setLastPaymentDate(model.getLastPaymentDate());
                customerOrder.setNextPaymentDate(model.getNextPaymentDate());
                customerOrderService.update(customerOrder);
            }
        } catch (Exception e) {
            log.error("[delivery] {} ex", StringUtil.toJsonString(model), e);
        }
    }
}
