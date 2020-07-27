package htcc.log.service.controller;

import htcc.common.constant.OrderStatusEnum;
import htcc.common.constant.ReturnCodeEnum;
import htcc.common.entity.base.BaseResponse;
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
import htcc.log.service.service.salary.SalaryService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public BaseResponse insertPayslip(@RequestBody SalaryFormula formula) {
        BaseResponse<Long> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        try {
            SalaryLogEntity logEntity = new SalaryLogEntity(formula);

            String payslipId = salaryService.genPayslipId(logEntity);
            logEntity.setPaySlipId(payslipId);

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
        try {
            salaryLogRepository.lockSalaryLog(request);
        } catch (Exception e) {
            log.error(String.format("[lockSalaryLog] %s ex", StringUtil.toJsonString(request)), e);
            return new BaseResponse(e);
        }
        return response;
    }
}
