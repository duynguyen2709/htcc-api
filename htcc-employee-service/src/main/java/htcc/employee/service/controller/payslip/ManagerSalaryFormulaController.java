package htcc.employee.service.controller.payslip;

import htcc.common.constant.Constant;
import htcc.common.constant.ReturnCodeEnum;
import htcc.common.entity.base.BaseResponse;
import htcc.common.entity.jpa.EmployeeInfo;
import htcc.common.entity.payslip.ManagerLockSalaryRequest;
import htcc.common.entity.payslip.SalaryModel;
import htcc.common.entity.payslip.SalaryFormula;
import htcc.common.util.DateTimeUtil;
import htcc.common.util.StringUtil;
import htcc.employee.service.repository.PermissionRepository;
import htcc.employee.service.service.jpa.EmployeeInfoService;
import htcc.employee.service.service.salary.SalaryCalculationService;
import htcc.employee.service.service.salary.SalaryFormulaService;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@Log4j2
public class ManagerSalaryFormulaController {

    @Autowired
    private SalaryFormulaService salaryFormulaService;

    @Autowired
    private SalaryCalculationService salaryCalculationService;

    @Autowired
    private PermissionRepository permissionRepo;

    @Autowired
    private EmployeeInfoService employeeInfoService;

    @GetMapping("/salary/formula/{companyId}/{username}")
    public BaseResponse getSalaryFormula(@PathVariable String companyId, @PathVariable String username) {
        BaseResponse<SalaryFormula> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        try {
            SalaryFormula formula = salaryFormulaService.findById(new SalaryFormula.Key(companyId, username));
            if (formula == null) {
                response = new BaseResponse<>(ReturnCodeEnum.DATA_NOT_FOUND);
                return null;
            }

            response.setData(formula);
        } catch (Exception e) {
            log.error("[getSalaryFormula] [{}-{}-{}] ex", companyId, username, e);
            response = new BaseResponse<>(e);
        }

        return response;
    }

    @PutMapping("/salary/formula/{companyId}/{username}")
    public BaseResponse updateSalaryFormula(@PathVariable String companyId, @PathVariable String username,
                                            @RequestBody SalaryFormula request) {
        BaseResponse<SalaryFormula> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        response.setReturnMessage("Cập nhật công thức tính lương thành công");
        try {
            request.setCompanyId(companyId);
            request.setUsername(username);

            String error = request.isValid();
            if (!error.isEmpty()) {
                response = new BaseResponse<>(ReturnCodeEnum.PARAM_DATA_INVALID);
                response.setReturnMessage(error);
                return response;
            }

            SalaryFormula oldEntity = salaryFormulaService.findById(new SalaryFormula.Key(companyId, username));
            if (oldEntity == null) {
                response = new BaseResponse<>(ReturnCodeEnum.DATA_NOT_FOUND);
                return response;
            }

            SalaryFormula newEntity = salaryFormulaService.update(request);
            response.setData(newEntity);

        } catch (Exception e) {
            log.error("[updateSalaryFormula] {} ex", StringUtil.toJsonString(request), e);
            response = new BaseResponse<>(e);
        }
        return response;
    }

    @PostMapping("/salary/{companyId}/{username}")
    public BaseResponse calculateSalary(@PathVariable String companyId, @PathVariable String username) {
        BaseResponse<SalaryFormula> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        response.setReturnMessage("Tính lương cho nhân viên thành công. Xem chi tiết tại trang Bảng lương");
        try {
            SalaryFormula formula = salaryFormulaService.findById(new SalaryFormula.Key(companyId, username));
            if (formula == null) {
                response = new BaseResponse<>(ReturnCodeEnum.DATA_NOT_FOUND);
                response.setReturnMessage("Không tìm thấy công thức tính lương cho nhân viên " + username);
                return response;
            }

            if (!salaryCalculationService.calculateSalary(formula)) {
                throw new Exception("salaryCalculationService.calculateSalary return false");
            }
        } catch (Exception e) {
            log.error("[calculateSalary] [{}-{}] ex", companyId, username, e);
            response = new BaseResponse<>(e);
        }
        return response;
    }

    @GetMapping("/salary/{companyId}/{yyyyMM}")
    public BaseResponse getSalaryLog(@PathVariable String companyId, @PathVariable String yyyyMM,
                                     @ApiParam(hidden = true) @RequestHeader(Constant.USERNAME) String actor) {
        BaseResponse<List<SalaryModel>> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        try {
            if (!DateTimeUtil.isRightFormat(yyyyMM, "yyyyMM")) {
                response = new BaseResponse<>(ReturnCodeEnum.DATE_WRONG_FORMAT,
                        String.format("Tháng %s không phù hợp định dạng", yyyyMM));
                return response;
            }

            List<EmployeeInfo> canManageEmployeeList = permissionRepo.getCanManageEmployees(companyId, actor);
            EmployeeInfo self = employeeInfoService.findById(new EmployeeInfo.Key(companyId, actor));
            canManageEmployeeList.add(self);
            Set<String> canManageEmployee = canManageEmployeeList
                        .stream().map(EmployeeInfo::getUsername).collect(Collectors.toSet());

            Map<String, String> fullNameMap = new HashMap<>();
            for (EmployeeInfo employee : canManageEmployeeList) {
                fullNameMap.put(employee.getUsername(), String.format("%s (%s)", employee.getFullName(), employee.getUsername()));
            }

            List<SalaryModel> salaryResponse = salaryCalculationService.getSalaryLog(companyId, yyyyMM);
            List<SalaryModel> dataResponse   = new ArrayList<>();

            for (SalaryModel data : salaryResponse) {
                if (canManageEmployee.contains(data.getUsername())) {
                    if (fullNameMap.containsKey(data.getUsername())) {
                        data.setUsername(fullNameMap.get(data.getUsername()));
                    }
                    dataResponse.add(data);
                }
            }
            response.setData(dataResponse);
        } catch (Exception e) {
            log.error("[getSalaryLog] [{}-{}-{}] ex", companyId, actor, yyyyMM, e);
            response = new BaseResponse<>(e);
        }
        return response;
    }

    // Xóa phiếu lương
    @DeleteMapping("/salary/{yyyyMM}/{paySlipId}")
    public BaseResponse deleteSalaryLog(@PathVariable String yyyyMM, @PathVariable String paySlipId) {
        BaseResponse response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        response.setReturnMessage("Xóa bảng lương thành công");
        try {
            if (!DateTimeUtil.isRightFormat(yyyyMM, "yyyyMM")) {
                response = new BaseResponse<>(ReturnCodeEnum.DATE_WRONG_FORMAT,
                        String.format("Tháng %s không phù hợp định dạng", yyyyMM));
                return response;
            }

            return salaryCalculationService.deleteSalaryLog(yyyyMM, paySlipId);
        } catch (Exception e) {
            log.error("[deleteSalaryLog] [{}-{}] ex", yyyyMM, paySlipId, e);
            response = new BaseResponse<>(e);
        }
        return response;
    }

    // Chốt bảng lương
    @PutMapping("/salary/lock")
    public BaseResponse lockSalaryLog(@RequestBody ManagerLockSalaryRequest request) {
        BaseResponse response = new BaseResponse(ReturnCodeEnum.SUCCESS);
        response.setReturnMessage("Chốt bảng lương thành công");
        try {
            String error = request.isValid();
            if (!error.isEmpty()) {
                response = new BaseResponse<>(ReturnCodeEnum.PARAM_DATA_INVALID);
                response.setReturnMessage(error);
                return response;
            }
            return salaryCalculationService.lockSalaryLog(request);
        } catch (Exception e) {
            log.error("[lockSalaryLog] {} ex", StringUtil.toJsonString(request), e);
            response = new BaseResponse<>(e);
        }
        return response;
    }
}
