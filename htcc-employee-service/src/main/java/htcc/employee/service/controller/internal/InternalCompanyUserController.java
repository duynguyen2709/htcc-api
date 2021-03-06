package htcc.employee.service.controller.internal;

import htcc.common.comparator.EmployeeIdComparator;
import htcc.common.constant.Constant;
import htcc.common.constant.PaymentCycleTypeEnum;
import htcc.common.constant.ReturnCodeEnum;
import htcc.common.constant.SalaryFormulaEnum;
import htcc.common.entity.base.BaseResponse;
import htcc.common.entity.companyuser.CompanyUserModel;
import htcc.common.entity.jpa.EmployeeInfo;
import htcc.common.entity.payslip.SalaryFormula;
import htcc.common.entity.role.EmployeePermission;
import htcc.common.util.StringUtil;
import htcc.employee.service.service.jpa.EmployeeInfoService;
import htcc.employee.service.service.jpa.EmployeePermissionService;
import htcc.employee.service.service.salary.SalaryCalculationService;
import htcc.employee.service.service.salary.SalaryFormulaService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@Log4j2
@RequestMapping("/internal")
@ApiIgnore
public class InternalCompanyUserController {

    @Autowired
    private EmployeeInfoService service;

    @Autowired
    private EmployeePermissionService employeePermissionService;

    @Autowired
    private SalaryCalculationService salaryCalculationService;

    @PostMapping("/employeeinfo")
    public BaseResponse createDefaultEmployee(@RequestBody CompanyUserModel model) {
        BaseResponse<EmployeeInfo> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        try {
            List<EmployeeInfo> listEmployeeByCompany = service.findByCompanyId(model.getCompanyId());
            int newId = 1;
            if (!listEmployeeByCompany.isEmpty()) {
                listEmployeeByCompany.sort(new EmployeeIdComparator());
                String lastId = listEmployeeByCompany.get(listEmployeeByCompany.size() - 1).getEmployeeId();

                newId = Integer.parseInt(lastId.substring(lastId.length() - 5)) + 1;
            }

            String employeeId = StringUtil.genEmployeeId(model.getCompanyId(), newId);

            EmployeeInfo employee = new EmployeeInfo();
            employee.setCompanyId(model.getCompanyId());
            employee.setUsername(model.getUsername());
            employee.setEmail(model.getEmail());
            employee.setPhoneNumber(model.getPhoneNumber());
            employee.setEmployeeId(employeeId);
            employee.setOfficeId(StringUtil.EMPTY);
            employee.setDepartment(StringUtil.EMPTY);
            employee.setTitle(StringUtil.EMPTY);
            employee.setLevel(0.0f);
            employee.setFullName(StringUtil.EMPTY);
            employee.setGender(1);
            employee.birthDate = new Date(0);
            employee.setIdentityCardNo(StringUtil.EMPTY);
            employee.setAddress(StringUtil.EMPTY);
            employee.setAvatar(Constant.USER_DEFAULT_AVATAR);
            employee = service.create(employee);

            if (employee != null) {
                EmployeePermission permission = new EmployeePermission();
                permission.setCompanyId(model.getCompanyId());
                permission.setUsername(model.getUsername());
                permission.setLineManager(StringUtil.EMPTY);
                permission.setSubManagers(new ArrayList<>());
                permission.setSubordinates(new ArrayList<>());
                permission.setCanManageOffices(new ArrayList<>());
                permission.setCanManageDepartments(new ArrayList<>());
                permission.setManagerRole(Constant.ROLE_SUPER_ADMIN);
                permission = employeePermissionService.create(permission);

                if (permission != null) {
                    SalaryFormula formula = salaryCalculationService.createDefaultSalaryFormula(model.getCompanyId(), model.getUsername());
                    if (formula == null) {
                        rollbackEmployeePermission(model.getCompanyId(), model.getUsername());
                        rollbackEmployeeInfo(model.getCompanyId(), model.getUsername());
                        throw new Exception("createDefaultSalaryFormula return null");
                    }
                }
                else {
                    rollbackEmployeeInfo(model.getCompanyId(), model.getUsername());
                    throw new Exception("employeePermissionService.create return null");
                }
            }

            response.setData(employee);
        } catch (Exception e) {
            log.error("[createDefaultEmployee] {} ex", StringUtil.toJsonString(model), e);
            response = new BaseResponse<>(e);
        }

        return response;
    }

    private void rollbackEmployeeInfo(String companyId, String username) {
        service.delete(new EmployeeInfo.Key(companyId, username));
    }

    private void rollbackEmployeePermission(String companyId, String username) {
        employeePermissionService.delete(new EmployeePermission.Key(companyId, username));
    }


}
