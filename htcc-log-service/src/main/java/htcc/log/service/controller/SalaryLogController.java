package htcc.log.service.controller;

import htcc.common.component.LoggingConfiguration;
import htcc.common.component.kafka.KafkaProducerService;
import htcc.common.constant.*;
import htcc.common.entity.base.BaseResponse;
import htcc.common.entity.icon.NotificationIconConfig;
import htcc.common.entity.notification.NotificationModel;
import htcc.common.entity.order.DetailOrderModel;
import htcc.common.entity.order.OrderLogEntity;
import htcc.common.entity.order.OrderPaymentResponse;
import htcc.common.entity.order.UpdateOrderStatusModel;
import htcc.common.entity.payslip.ManagerLockSalaryRequest;
import htcc.common.entity.payslip.SalaryFormula;
import htcc.common.entity.payslip.SalaryLogEntity;
import htcc.common.util.StringUtil;
import htcc.log.service.entity.jpa.LogCounter;
import htcc.log.service.repository.BaseLogDAO;
import htcc.log.service.repository.LogCounterRepository;
import htcc.log.service.repository.OrderLogRepository;
import htcc.log.service.repository.SalaryLogRepository;
import htcc.log.service.service.icon.IconService;
import htcc.log.service.service.salary.SalaryService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@Log4j2
@RequestMapping("/internal/logs")
public class SalaryLogController {

    @Autowired
    private SalaryLogRepository salaryLogRepository;

    @Autowired
    private BaseLogDAO baseLogDAO;

    @Autowired
    private SalaryService salaryService;

    @Autowired
    private IconService iconService;

    @Autowired
    private KafkaProducerService kafka;

    @GetMapping("/salary/{companyId}/{username}/{yyyyMM}")
    public BaseResponse getEmployeePayslip(@PathVariable String companyId, @PathVariable String username, @PathVariable String yyyyMM){
        BaseResponse<List<SalaryLogEntity>> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        try {
            List<SalaryLogEntity> list = salaryLogRepository.getEmployeePayslip(companyId, username, yyyyMM);
            response.setData(list);
        } catch (Exception e) {
            log.error(String.format("[getEmployeePayslip] [%s-%s-%s] ex", companyId, username, yyyyMM), e);
            return new BaseResponse(e);
        }
        return response;
    }

    @GetMapping("/salary/{companyId}/{yyyyMM}")
    public BaseResponse getSalaryLogForManager(@PathVariable String companyId, @PathVariable String yyyyMM){
        BaseResponse<List<SalaryLogEntity>> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        try {
            List<SalaryLogEntity> list = salaryLogRepository.getSalaryLogForManager(companyId, yyyyMM);
            response.setData(list);
        } catch (Exception e) {
            log.error(String.format("[getSalaryLogForManager] [%s-%s] ex", companyId, yyyyMM), e);
            return new BaseResponse(e);
        }
        return response;
    }

    @GetMapping("/salary/delete/{yyyyMM}/{paySlipId}")
    public BaseResponse deleteSalaryLog(@PathVariable String yyyyMM,@PathVariable String paySlipId) {
        BaseResponse response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        response.setReturnMessage("Xóa bảng lương thành công");
        try {
            int result = salaryLogRepository.deleteSalaryLog(yyyyMM, paySlipId);
            if (result != 1) {
                throw new Exception("salaryLogRepository.deleteSalaryLog return " + result);
            }
        } catch (Exception e) {
            log.error(String.format("[deleteSalaryLog] [%s-%s] ex", paySlipId, yyyyMM), e);
            return new BaseResponse(e);
        }
        return response;
    }

    @PostMapping("/salary")
    public BaseResponse insertPayslip(@RequestBody SalaryFormula formula,
                                      @RequestParam long totalIncome,
                                      @RequestParam long totalDeduction) {
        BaseResponse<Long> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        try {
            SalaryLogEntity logEntity = new SalaryLogEntity(formula);

            String payslipId = salaryService.genPayslipId(logEntity);
            logEntity.setPaySlipId(payslipId);
            logEntity.setTotalIncome(totalIncome);
            logEntity.setTotalDeduction(totalDeduction);
            logEntity.setTotalNetPay(totalIncome - totalDeduction);

            long id = baseLogDAO.insertLog(logEntity);
            response.setData(id);
        } catch (Exception e) {
            log.error(String.format("[insertPayslip] %s ex", StringUtil.toJsonString(formula)), e);
            return new BaseResponse(e);
        }
        return response;
    }

    @PostMapping("/salary/lock")
    public BaseResponse lockSalaryLog(@RequestBody ManagerLockSalaryRequest request) {
        BaseResponse<Long> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        response.setReturnMessage("Chốt bảng lương thành công");
        Set<String> usernameList = new HashSet<>();
        String      companyId    = "";

        try {
            List<SalaryLogEntity> list = salaryLogRepository.getSalaryLogInList(request.getPaySlipIdList(), request.getYyyyMM());
            if (list == null || list.isEmpty()) {
                throw new Exception("salaryLogRepository.getSalaryLogInList return empty");
            }
            companyId = list.get(0).getCompanyId();
            for (SalaryLogEntity log : list) {
                usernameList.add(log.getUsername());
            }

            salaryLogRepository.lockSalaryLog(request);
        } catch (Exception e) {
            log.error(String.format("[lockSalaryLog] %s ex", StringUtil.toJsonString(request)), e);
            response = new BaseResponse<>(e);
        } if (response.getReturnCode() == ReturnCodeEnum.SUCCESS.getValue()) {
            for (String username : usernameList) {
                NotificationModel model = new NotificationModel();
                model.setRequestId(LoggingConfiguration.getTraceId());
                model.setSourceClientId(0);
                model.setTargetClientId(ClientSystemEnum.MOBILE.getValue());
                model.setReceiverType(2);
                model.setSender("Hệ thống");
                model.setCompanyId(companyId);
                model.setUsername(username);
                model.setSendTime(System.currentTimeMillis());

                int screenId = ScreenEnum.PAYCHECK.getValue();
                model.setScreenId(screenId);
                NotificationIconConfig icon = iconService.getIcon(screenId);
                if (icon != null) {
                    model.setIconId(icon.getIconId());
                    model.setIconUrl(icon.getIconURL());
                }

                model.setStatus(NotificationStatusEnum.INIT.getValue());
                model.setHasRead(false);
                model.setTitle("Thông báo về bảng lương");
                model.setContent("Bảng lương tháng mới của bạn đã sẵn sàng. Vào kiểm tra ngay thôi");

                kafka.sendMessage(kafka.getBuzConfig().getEventPushNotification().getTopicName(), model);
            }
        }
        return response;
    }
}
