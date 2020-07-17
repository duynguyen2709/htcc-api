package htcc.employee.service.controller.role;

import htcc.common.constant.Constant;
import htcc.common.constant.ReturnCodeEnum;
import htcc.common.entity.base.BaseResponse;
import htcc.common.entity.jpa.Department;
import htcc.common.entity.jpa.EmployeeInfo;
import htcc.common.entity.jpa.MiniEmployeeInfo;
import htcc.common.entity.jpa.Office;
import htcc.common.entity.payslip.EmployeePayslipResponse;
import htcc.common.entity.role.EmployeePermission;
import htcc.common.entity.role.EmployeePermissionResponse;
import htcc.common.entity.role.ManagerRole;
import htcc.common.util.StringUtil;
import htcc.employee.service.repository.PermissionRepository;
import htcc.employee.service.service.jpa.*;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@Log4j2
public class EmployeePermissionController {

    @Autowired
    private EmployeePermissionService employeePermissionService;

    @Autowired
    private ManagerRoleService managerRoleService;

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private EmployeeInfoService employeeInfoService;

    @Autowired
    private OfficeService officeService;

    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/permissions/{companyId}/{username}")
    public BaseResponse getEmployeePermission(@PathVariable String companyId, @PathVariable String username) {
        BaseResponse<EmployeePermissionResponse> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        try {
            EmployeePermission permission = employeePermissionService.findById(new EmployeePermission.Key(companyId, username));
            if (permission == null) {
                throw new Exception("employeePermissionService.findById return null");
            }

            EmployeePermissionResponse dataResponse = new EmployeePermissionResponse();
            EmployeePermissionResponse.DataView dataView = new EmployeePermissionResponse.DataView();

            // set Self Info
            EmployeeInfo self = employeeInfoService.findById(new EmployeeInfo.Key(companyId, username));
            if (self == null) {
                throw new Exception("employeeInfoService.findById return null : " + username);
            }
            dataView.setLineManager(new MiniEmployeeInfo(self));

            // set LineManager
            if (!permission.getLineManager().isEmpty()) {
                EmployeeInfo lineManager = employeeInfoService.findById(new EmployeeInfo.Key(companyId, permission.getLineManager()));
                if (lineManager == null) {
                    throw new Exception("employeeInfoService.findById return null : " + permission.getLineManager());
                }
                dataView.setLineManager(new MiniEmployeeInfo(lineManager));
            }

            // set subManagers
            List<MiniEmployeeInfo> subManagers = new ArrayList<>();
            for (String subManager : permission.getSubManagers()) {
                EmployeeInfo manager = employeeInfoService.findById(new EmployeeInfo.Key(companyId, subManager));
                if (manager == null) {
                    throw new Exception("employeeInfoService.findById return null : " + subManager);
                }
                subManagers.add(new MiniEmployeeInfo(manager));
            }
            dataView.setSubManagers(subManagers);

            // set subordinates
            List<MiniEmployeeInfo> subordinates = permissionRepository.getCanManageEmployees(companyId, username)
                                   .stream().map(MiniEmployeeInfo::new).collect(Collectors.toList());
            dataView.setSubordinates(subordinates);

            // set ManagerRole
            ManagerRole managerRole = managerRoleService.findById(new ManagerRole.Key(companyId, permission.getManagerRole()));
            if (managerRole == null) {
                throw new Exception("managerRoleService.findById return null : " + permission.getManagerRole());
            }
            dataView.setManagerRole(managerRole);

            // set offices
            List<Office> officeList = new ArrayList<>();
            for (String officeId : permissionRepository.getCanManageOffices(companyId, username)) {
                Office office = officeService.findById(new Office.Key(companyId, officeId));
                if (office == null) {
                    throw new Exception("officeService.findById return null : " + officeId);
                }
                officeList.add(office);
            }
            dataView.setCanManageOffices(officeList);

            // set departments
            List<Department> departmentList = new ArrayList<>();
            for (String departmentId : permissionRepository.getCanManageDepartments(companyId, username)) {
                Department department = departmentService.findById(new Department.Key(companyId, departmentId));
                if (department == null) {
                    throw new Exception("departmentService.findById return null : " + departmentId);
                }
                departmentList.add(department);
            }
            dataView.setCanManageDepartments(departmentList);

            dataResponse.setDataView(dataView);
            dataResponse.setDataEdit(permission);
            response.setData(dataResponse);
        } catch (Exception e) {
            log.error("[getEmployeePermission] [{}-{}] ex", companyId, username, e);
            response = new BaseResponse<>(e);
        }
        return response;
    }

    @PutMapping("/permissions/{companyId}/{username}")
    public BaseResponse updateEmployeePermission(@PathVariable String companyId, @PathVariable String username,
                                                 @RequestBody EmployeePermission request,
                                                 @ApiParam(hidden = true) @RequestHeader(Constant.USERNAME) String actor) {
        BaseResponse<EmployeePermission> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        try {
            request.setCompanyId(companyId);
            request.setUsername(username);
            if (request.getLineManager() == null) {
                request.setLineManager(StringUtil.EMPTY);
            }
            if (request.getSubManagers() == null) {
                request.setSubManagers(new ArrayList<>());
            }
            if (request.getManagerRole() == null) {
                request.setManagerRole(StringUtil.EMPTY);
            }
            if (request.getCanManageDepartments() == null) {
                request.setCanManageDepartments(new ArrayList<>());
            }
            if (request.getCanManageOffices() == null) {
                request.setCanManageOffices(new ArrayList<>());
            }

            String error = request.isValid();
            if (!error.isEmpty()) {
                response = new BaseResponse<>(ReturnCodeEnum.PARAM_DATA_INVALID);
                response.setReturnMessage(error);
                return response;
            }

            EmployeePermission oldPermission = employeePermissionService.findById(new EmployeePermission.Key(companyId, username));
            if (oldPermission == null) {
                response = new BaseResponse<>(ReturnCodeEnum.DATA_NOT_FOUND);
                return response;
            }
            request.setSubordinates(oldPermission.getSubordinates());

            error = isConflict(request);
            if (!error.isEmpty()) {
                response = new BaseResponse<>(ReturnCodeEnum.PARAM_DATA_INVALID);
                response.setReturnMessage(error);
                return response;
            }

            if (!validateManagerRole(request, actor)) {
                response = new BaseResponse<>(ReturnCodeEnum.PERMISSION_DENIED);
                response.setReturnMessage("Không thể gán quyền cho nhân viên cao hơn quyền của bản thân");
                return response;
            }

            if (!validateOffice(request, actor)) {
                response = new BaseResponse<>(ReturnCodeEnum.PERMISSION_DENIED);
                response.setReturnMessage("Không thể gán quyền quản lý chi nhánh không thuộc quản lý của bản thân");
                return response;
            }

            if (!validateDepartment(request, actor)) {
                response = new BaseResponse<>(ReturnCodeEnum.PERMISSION_DENIED);
                response.setReturnMessage("Không thể gán quyền quản lý phòng ban không thuộc quản lý của bản thân");
                return response;
            }

            List<EmployeePermission> needUpdate = new ArrayList<>();
            needUpdate.add(request);

            compareLineManager(request, oldPermission, needUpdate);

            compareSubManagers(request, oldPermission, needUpdate);

            employeePermissionService.updateAll(needUpdate);

        } catch (Exception e) {
            log.error("[updateEmployeePermission] {} ex", StringUtil.toJsonString(request), e);
            response = new BaseResponse<>(e);
        }
        return response;
    }

    private String isConflict(EmployeePermission request) {
        boolean res1 = isConflict(request.getLineManager(), request.getSubManagers());
        if (res1) {
            return "Cấp trên trực tiếp không được nằm trong danh sách quản lý phụ";
        }
        boolean res2 = isConflict(request.getSubordinates(), request.getSubManagers());
        if (res2) {
            return "Quản lý phụ không được nằm trong danh sách nhân viên dưới quyền";
        }
        boolean res3 = isConflict(request.getLineManager(), request.getSubordinates());
        if (res3) {
            return "Cấp trên trực tiếp không được nằm trong danh sách nhân viên dưới quyền";
        }
        boolean res4 = isConflict(request.getUsername(), request.getSubManagers());
        if (res4) {
            return "Nhân viên hiện tại không được nằm trong danh sách quản lý phụ";
        }
        boolean res5 = isConflict(request.getUsername(), request.getSubordinates());
        if (res5) {
            return "Nhân viên hiện tại không được nằm trong danh sách nhân viên dưới quyền";
        }
        boolean res6 = request.getUsername().equals(StringUtil.valueOf(request.getLineManager()));
        if (res6) {
            return "Cấp trên trực tiếp không được trùng với nhân viên hiện tại";
        }
        return StringUtil.EMPTY;
    }

    private boolean isConflict(String username, List<String> userList) {
        if (StringUtil.isEmpty(username)) {
            return false;
        }
        return userList.contains(username);
    }

    private boolean isConflict(List<String> sourceList, List<String> targetList) {
        for (String source : sourceList) {
            if (targetList.contains(source)) {
                return true;
            }
        }
        return false;
    }

    private void compareSubManagers(EmployeePermission newPermission, EmployeePermission oldPermission, List<EmployeePermission> needUpdate) throws Exception {
        if (isEqual(newPermission.getSubManagers(), oldPermission.getSubManagers())) {
            return;
        }
        String companyId = newPermission.getCompanyId();
        Map<String, EmployeePermission> needUpdateMap = new HashMap<>();

        // remove all in old manager list
        for (String manager : oldPermission.getSubManagers()) {
            if (!needUpdateMap.containsKey(manager)) {
                EmployeePermission permission = employeePermissionService.findById(new EmployeePermission.Key(companyId, manager));
                if (permission == null) {
                    throw new Exception(String.format("employeePermissionService.findById [%s-%s] return null", companyId, manager));
                }
                needUpdateMap.put(manager, permission);
            }
            EmployeePermission employeePermission = needUpdateMap.get(manager);
            List<String> subordinates = employeePermission.getSubordinates();
            subordinates.remove(newPermission.getUsername());
            employeePermission.setSubordinates(subordinates);
            needUpdateMap.replace(manager, employeePermission);
        }

        // add to new manager list
        for (String manager : newPermission.getSubManagers()) {
            if (!needUpdateMap.containsKey(manager)) {
                EmployeePermission permission = employeePermissionService.findById(new EmployeePermission.Key(companyId, manager));
                if (permission == null) {
                    throw new Exception(String.format("employeePermissionService.findById [%s-%s] return null", companyId, manager));
                }
                needUpdateMap.put(manager, permission);
            }
            EmployeePermission employeePermission = needUpdateMap.get(manager);
            List<String> subordinates = employeePermission.getSubordinates();
            subordinates.add(newPermission.getUsername());
            employeePermission.setSubordinates(subordinates);
            needUpdateMap.replace(manager, employeePermission);
        }

        needUpdate.addAll(needUpdateMap.values());
    }

    private void compareLineManager(EmployeePermission newPermission, EmployeePermission oldPermission, List<EmployeePermission> needUpdate) {
        if (StringUtil.valueOf(newPermission.getLineManager()).equals(oldPermission.getLineManager())) {
            return;
        }

        if (!oldPermission.getLineManager().isEmpty()) {
            EmployeePermission oldLineManager = employeePermissionService.findById(
                    new EmployeePermission.Key(newPermission.getCompanyId(), oldPermission.getLineManager()));
            List<String> subordinates = oldLineManager.getSubordinates();
            subordinates.remove(newPermission.getUsername());
            oldLineManager.setSubordinates(subordinates);
            needUpdate.add(oldLineManager);
        }

        if (!newPermission.getLineManager().isEmpty()) {
            EmployeePermission newLineManager = employeePermissionService.findById(
                    new EmployeePermission.Key(newPermission.getCompanyId(), newPermission.getLineManager()));
            List<String> subordinates = newLineManager.getSubordinates();
            subordinates.add(newPermission.getUsername());
            newLineManager.setSubordinates(subordinates);
            needUpdate.add(newLineManager);
        }
    }

    private boolean isEqual(List<String> newSubManagers, List<String> oldSubManagers) {
        for (String user : newSubManagers) {
            if (!oldSubManagers.contains(user)) {
                return false;
            }
        }
        return true;
    }

    private boolean validateOffice(EmployeePermission request, String actor) {
        List<String> officeList = permissionRepository.getCanManageOffices(request.getCompanyId(), actor);
        for (String office : request.getCanManageOffices()) {
            if (!officeList.contains(office)) {
                return false;
            }
        }
        return true;
    }

    private boolean validateDepartment(EmployeePermission request, String actor) {
        List<String> departments = permissionRepository.getCanManageDepartments(request.getCompanyId(), actor);
        for (String department : request.getCanManageDepartments()) {
            if (!departments.contains(department)) {
                return false;
            }
        }
        return true;
    }

    private boolean validateManagerRole(EmployeePermission request, String actor) throws Exception {
        int newRoleLevel = Integer.MAX_VALUE;
        int actorRoleLevel = Integer.MAX_VALUE;
        if (!request.getManagerRole().isEmpty()) {
            ManagerRole newRole = managerRoleService.findById(new ManagerRole.Key(request.getCompanyId(), request.getManagerRole()));
            if (newRole == null) {
                throw new Exception("managerRoleService.findById return null");
            }
            newRoleLevel = newRole.getRoleLevel();
        }
        EmployeePermission actorPermission = employeePermissionService.findById(new EmployeePermission.Key(request.getCompanyId(), actor));
        if (actorPermission == null) {
            throw new Exception("employeePermissionService.findById return null");
        }
        if (actorPermission.getManagerRole().isEmpty()) {
            return false;
        }
        ManagerRole actorRole = managerRoleService.findById(new ManagerRole.Key(request.getCompanyId(), actorPermission.getManagerRole()));
        if (actorRole == null) {
            throw new Exception("managerRoleService.findById return null");
        }
        actorRoleLevel = actorRole.getRoleLevel();
        return newRoleLevel >= actorRoleLevel;
    }
}
