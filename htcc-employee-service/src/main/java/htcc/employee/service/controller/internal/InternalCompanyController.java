package htcc.employee.service.controller.internal;

import com.google.gson.reflect.TypeToken;
import htcc.common.constant.Constant;
import htcc.common.constant.ManagerActionEnum;
import htcc.common.constant.ManagerRoleGroupEnum;
import htcc.common.constant.ReturnCodeEnum;
import htcc.common.entity.base.BaseResponse;
import htcc.common.entity.jpa.Company;
import htcc.common.entity.role.ManagerRole;
import htcc.common.util.StringUtil;
import htcc.employee.service.service.jpa.BuzConfigService;
import htcc.employee.service.service.jpa.CompanyService;
import htcc.employee.service.service.jpa.ManagerRoleService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Log4j2
@RequestMapping("/internal")
@ApiIgnore
public class InternalCompanyController {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private BuzConfigService buzConfigService;

    @Autowired
    private ManagerRoleService managerRoleService;


    @PostMapping("/companies")
    public BaseResponse createCompany(@RequestBody @Valid Company company) {
        BaseResponse<Company> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        try {
            Company oldCompany = companyService.findById(company.companyId);
            if (oldCompany != null) {
                response = new BaseResponse<>(ReturnCodeEnum.DATA_ALREADY_EXISTED,
                        String.format("Công ty %s đã tồn tại", company.companyId));
                return response;
            }

            Company newCompany = companyService.create(company);
            if (newCompany == null) {
                throw new Exception("companyService.create return null");
            }

            // TODO : Create all default info when creating company
            buzConfigService.createDefaultDayOffInfo(newCompany.getCompanyId());
            createSuperAdminRole(newCompany);

            response.setData(newCompany);

        } catch (Exception e) {
            log.error("[createCompany] {} ex", StringUtil.toJsonString(company), e);
            response = new BaseResponse<>(e);
        }

        return response;
    }

    private void createSuperAdminRole(Company company) {
        try {
            ManagerRole role = new ManagerRole();
            role.setCompanyId(company.getCompanyId());
            role.setRoleId(Constant.ROLE_SUPER_ADMIN);
            role.setRoleName("Quản lý tổng");
            Map<String, Map<String, Boolean>> defaultRoleDetail = new HashMap<>();
            List<Integer> supportedScreens = StringUtil.json2Collection(company.getSupportedScreens(),
                    new TypeToken<List<Integer>>() {}.getType());

            if (supportedScreens == null) {
                throw new Exception("parse supportedScreens return null");
            }

            for (ManagerRoleGroupEnum group : ManagerRoleGroupEnum.values()) {
                if (supportedScreens.contains(group.getScreenId())) {
                    defaultRoleDetail.put(group.getRoleGroup(), new HashMap<>());
                    List<ManagerActionEnum> actions = group.getActions();
                    for (ManagerActionEnum action : actions) {
                        defaultRoleDetail.get(group.getRoleGroup()).put(action.getValue(), true);
                    }
                }
            }
            role.setRoleDetail(defaultRoleDetail);
            managerRoleService.create(role);
        } catch (Exception e) {
            log.error("[createSuperAdminRole] {} ex", StringUtil.toJsonString(company), e);
        }
    }

    @GetMapping("/companies")
    public BaseResponse getCompanies() {
        BaseResponse response = new BaseResponse(ReturnCodeEnum.SUCCESS);
        try {
            List<Company> list = companyService.findAll();
            response.setData(list);

        } catch (Exception e) {
            log.error("[getCompanies] ex", e);
            response = new BaseResponse(e);
        }

        return response;
    }




    @PostMapping("/companies/update")
    public BaseResponse updateCompany(@RequestBody @Valid Company company) {
        BaseResponse<Company> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        try {
            Company oldCompany = companyService.findById(company.companyId);
            if (oldCompany == null) {
                response = new BaseResponse<>(ReturnCodeEnum.DATA_NOT_FOUND,
                        String.format("Không tìm thấy công ty %s", company.companyId));
                return response;
            }

            company.setStatus(oldCompany.getStatus());
            company.setSupportedScreens(oldCompany.getSupportedScreens());
            company = companyService.update(company);

            response.setData(company);

        } catch (Exception e) {
            log.error("[updateCompany] {} ex", StringUtil.toJsonString(company), e);
            response = new BaseResponse<>(e);
        }

        return response;
    }




    @PostMapping("/companies/status")
    public BaseResponse updateCompanyStatus(@RequestBody Company company) {
        BaseResponse<Company> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        try {
            Company oldCompany = companyService.findById(company.companyId);
            if (oldCompany == null) {
                response = new BaseResponse<>(ReturnCodeEnum.DATA_NOT_FOUND,
                        String.format("Không tìm thấy công ty %s", company.companyId));
                return response;
            }

            if (oldCompany.getStatus() == company.getStatus()) {
                response = new BaseResponse<>(ReturnCodeEnum.PARAM_DATA_INVALID,
                        String.format("Trạng thái mới [%s] không được giống trạng thái cũ", company.getStatus()));
                return response;
            }

            oldCompany.setStatus(company.getStatus());

            company = companyService.update(oldCompany);

            response.setData(company);

        } catch (Exception e) {
            log.error("[updateCompanyStatus] {} ex", StringUtil.toJsonString(company), e);
            response = new BaseResponse<>(e);
        }

        return response;
    }
}
