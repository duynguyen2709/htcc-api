package htcc.employee.service.controller.internal;

import htcc.common.constant.Constant;
import htcc.common.constant.ReturnCodeEnum;
import htcc.common.entity.base.BaseResponse;
import htcc.common.entity.companyuser.CompanyUserModel;
import htcc.common.entity.jpa.Company;
import htcc.common.entity.jpa.EmployeeInfo;
import htcc.common.util.StringUtil;
import htcc.employee.service.service.jpa.CompanyService;
import htcc.employee.service.service.jpa.EmployeeInfoService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
@Log4j2
@RequestMapping("/internal")
@ApiIgnore
public class InternalCompanyUserController {

    @Autowired
    private EmployeeInfoService service;




    @PostMapping("/employeeinfo")
    public BaseResponse createDefaultEmployee(@RequestBody CompanyUserModel model) {
        BaseResponse<EmployeeInfo> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        try {
            List<EmployeeInfo> listEmployeeByCompany = service.findByCompanyId(model.getCompanyId());
            int newId = listEmployeeByCompany.size() + 1;
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
            employee.setFullName(StringUtil.EMPTY);
            employee.birthDate = new Date(0);
            employee.setIdentityCardNo(StringUtil.EMPTY);
            employee.setAddress(StringUtil.EMPTY);
            employee.setAvatar(Constant.USER_DEFAULT_AVATAR);

            employee = service.create(employee);
            response.setData(employee);
        } catch (Exception e) {
            log.error("[createDefaultEmployee] {} ex", StringUtil.toJsonString(model), e);
            response = new BaseResponse<>(e);
        }

        return response;
    }
//
//
//
//
//    @GetMapping("/companies")
//    public BaseResponse getCompanies() {
//        BaseResponse response = new BaseResponse(ReturnCodeEnum.SUCCESS);
//        try {
//            List<Company> list = companyService.findAll();
//            response.setData(list);
//
//        } catch (Exception e) {
//            log.error("[getCompanies] ex", e);
//            response = new BaseResponse(e);
//        }
//
//        return response;
//    }
//
//
//
//
//    @PostMapping("/companies/update")
//    public BaseResponse updateCompany(@RequestBody @Valid Company company) {
//        BaseResponse<Company> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
//        try {
//            Company oldCompany = companyService.findById(company.companyId);
//            if (oldCompany == null) {
//                response = new BaseResponse<>(ReturnCodeEnum.DATA_NOT_FOUND,
//                        String.format("Không tìm thấy công ty %s", company.companyId));
//                return response;
//            }
//
//            company.setStatus(oldCompany.getStatus());
//            company = companyService.update(company);
//
//            response.setData(company);
//
//        } catch (Exception e) {
//            log.error("[updateCompany] {} ex", StringUtil.toJsonString(company), e);
//            response = new BaseResponse<>(e);
//        }
//
//        return response;
//    }
//
//
//
//
//    @PostMapping("/companies/status")
//    public BaseResponse updateCompanyStatus(@RequestBody Company company) {
//        BaseResponse<Company> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
//        try {
//            Company oldCompany = companyService.findById(company.companyId);
//            if (oldCompany == null) {
//                response = new BaseResponse<>(ReturnCodeEnum.DATA_NOT_FOUND,
//                        String.format("Không tìm thấy công ty %s", company.companyId));
//                return response;
//            }
//
//            if (oldCompany.getStatus() == company.getStatus()) {
//                response = new BaseResponse<>(ReturnCodeEnum.PARAM_DATA_INVALID,
//                        String.format("Trạng thái mới [%s] không được giống trạng thái cũ", company.getStatus()));
//                return response;
//            }
//
//            oldCompany.setStatus(company.getStatus());
//
//            company = companyService.update(oldCompany);
//
//            response.setData(company);
//
//        } catch (Exception e) {
//            log.error("[updateCompanyStatus] {} ex", StringUtil.toJsonString(company), e);
//            response = new BaseResponse<>(e);
//        }
//
//        return response;
//    }
}
