package htcc.employee.service.controller.role;

import com.google.gson.reflect.TypeToken;
import htcc.common.constant.Constant;
import htcc.common.constant.ManagerActionEnum;
import htcc.common.constant.ManagerRoleGroupEnum;
import htcc.common.constant.ReturnCodeEnum;
import htcc.common.entity.base.BaseResponse;
import htcc.common.entity.jpa.Company;
import htcc.common.entity.role.ManagerRole;
import htcc.common.entity.role.ManagerRoleResponse;
import htcc.common.util.StringUtil;
import htcc.employee.service.service.jpa.CompanyService;
import htcc.employee.service.service.jpa.ManagerRoleService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Log4j2
public class ManagerRoleController {

    @Autowired
    private ManagerRoleService managerRoleService;

    @Autowired
    private CompanyService companyService;

    @GetMapping("/roles/{companyId}")
    public BaseResponse getManagerRoles(@PathVariable String companyId) {
        BaseResponse<ManagerRoleResponse> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        try {
            Company company = companyService.findById(companyId);
            if (company == null) {
                throw new Exception("companyService.findById return null");
            }

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
                        defaultRoleDetail.get(group.getRoleGroup()).put(action.getValue(), false);
                    }
                }
            }

            List<ManagerRole> roleList = managerRoleService.findByCompanyId(companyId);
            if (!roleList.isEmpty()) {
                // sort ROLE_SUPER_ADMIN to first position
                for (int i = 1; i < roleList.size(); i++) {
                    if (roleList.get(i).getRoleId().equals(Constant.ROLE_SUPER_ADMIN)) {
                        ManagerRole temp = roleList.get(0);
                        roleList.set(0, roleList.get(i));
                        roleList.set(i, temp);
                        break;
                    }
                }
            }

            ManagerRoleResponse dataResponse = new ManagerRoleResponse();
            dataResponse.setRoleList(roleList);
            dataResponse.setDefaultRoleDetail(defaultRoleDetail);

            response.setData(dataResponse);
        } catch (Exception e) {
            log.error("[getManagerRoles] [{}] ex", companyId, e);
            return new BaseResponse(e);
        }
        return response;
    }

    @PostMapping("/roles")
    public BaseResponse createManagerRole(@RequestBody ManagerRole request) {
        BaseResponse<ManagerRole> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        response.setReturnMessage("Thêm nhóm quyền thành công");
        try {
            String error = request.isValid();
            if (!error.isEmpty()) {
                response = new BaseResponse<>(ReturnCodeEnum.PARAM_DATA_INVALID);
                response.setReturnMessage(error);
                return response;
            }

            Company company = companyService.findById(request.getCompanyId());
            if (company == null) {
                throw new Exception("companyService.findById return null");
            }
            List<Integer> supportedScreens = StringUtil.json2Collection(company.getSupportedScreens(),
                    new TypeToken<List<Integer>>() {}.getType());

            if (supportedScreens == null) {
                throw new Exception("parse supportedScreens return null");
            }
            for (String group : request.getRoleDetail().keySet()) {
                ManagerRoleGroupEnum role = ManagerRoleGroupEnum.getEnum(group);
                if (role == null) {
                    response = new BaseResponse<>(ReturnCodeEnum.PARAM_DATA_INVALID);
                    return response;
                }
                Map<String, Boolean> actions = request.getRoleDetail().get(group);
                for (boolean value : actions.values()) {
                    if (value) {
                        if (!supportedScreens.contains(role.getScreenId())) {
                            response = new BaseResponse<>(ReturnCodeEnum.PARAM_DATA_INVALID);
                            response.setReturnMessage("Gói chức năng không hỗ trợ phân quyền");
                            return response;
                        }
                    }
                }
            }

            ManagerRole entity = managerRoleService.create(request);
            response.setData(entity);
        } catch (Exception e) {
            log.error("[createManagerRole] {} ex", StringUtil.toJsonString(request), e);
            return new BaseResponse(e);
        }
        return response;
    }

    @PutMapping("/roles/{companyId}/{roleId}")
    public BaseResponse updateManagerRole(@RequestBody ManagerRole request,
                                          @PathVariable String companyId,
                                          @PathVariable String roleId) {
        BaseResponse<ManagerRole> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        response.setReturnMessage("Cập nhật nhóm quyền thành công");
        try {
            String error = request.isValid();
            if (!error.isEmpty()) {
                response = new BaseResponse<>(ReturnCodeEnum.PARAM_DATA_INVALID);
                response.setReturnMessage(error);
                return response;
            }

            Company company = companyService.findById(companyId);
            if (company == null) {
                throw new Exception("companyService.findById return null");
            }
            List<Integer> supportedScreens = StringUtil.json2Collection(company.getSupportedScreens(),
                    new TypeToken<List<Integer>>() {}.getType());

            if (supportedScreens == null) {
                throw new Exception("parse supportedScreens return null");
            }
            for (String group : request.getRoleDetail().keySet()) {
                ManagerRoleGroupEnum role = ManagerRoleGroupEnum.getEnum(group);
                if (role == null) {
                    response = new BaseResponse<>(ReturnCodeEnum.PARAM_DATA_INVALID);
                    return response;
                }

                Map<String, Boolean> actions = request.getRoleDetail().get(group);
                for (boolean value : actions.values()) {
                    if (value) {
                        if (!supportedScreens.contains(role.getScreenId())) {
                            response = new BaseResponse<>(ReturnCodeEnum.PARAM_DATA_INVALID);
                            response.setReturnMessage("Gói chức năng không hỗ trợ phân quyền");
                            return response;
                        }
                    }
                }
            }

            ManagerRole oldEntity = managerRoleService.findById(new ManagerRole.Key(companyId, roleId));
            if (oldEntity == null) {
                response = new BaseResponse<>(ReturnCodeEnum.DATA_NOT_FOUND);
                response.setReturnMessage("Không tìm thấy nhóm quyền " + roleId);
                return response;
            }

            request.setCompanyId(companyId);
            request.setRoleId(roleId);
            request.setRoleName(oldEntity.getRoleName());

            ManagerRole entity = managerRoleService.update(request);
            response.setData(entity);
        } catch (Exception e) {
            log.error("[updateManagerRole] {} ex", StringUtil.toJsonString(request), e);
            return new BaseResponse(e);
        }
        return response;
    }

    @DeleteMapping("/roles/{companyId}/{roleId}")
    public BaseResponse deleteManagerRole(@PathVariable String companyId,
                                          @PathVariable String roleId) {
        BaseResponse response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        response.setReturnMessage("Xóa nhóm quyền thành công");
        try {
            ManagerRole oldEntity = managerRoleService.findById(new ManagerRole.Key(companyId, roleId));
            if (oldEntity == null) {
                response = new BaseResponse<>(ReturnCodeEnum.DATA_NOT_FOUND);
                response.setReturnMessage("Không tìm thấy nhóm quyền " + roleId);
                return response;
            }
            managerRoleService.delete(new ManagerRole.Key(companyId, roleId));
        } catch (Exception e) {
            log.error("[deleteManagerRole] [{}-{}] ex", companyId, roleId, e);
            return new BaseResponse(e);
        } finally {
            if (response.getReturnCode() == ReturnCodeEnum.SUCCESS.getValue()) {
                // TODO : Delete all related role assign to employee
            }
        }
        return response;
    }
}
