package htcc.employee.service.controller.home;

import htcc.common.constant.ClientSystemEnum;
import htcc.common.constant.ReturnCodeEnum;
import htcc.common.entity.base.BaseResponse;
import htcc.common.entity.dayoff.CompanyDayOffInfo;
import htcc.common.entity.home.ManagerHomeResponse;
import htcc.common.entity.jpa.EmployeeInfo;
import htcc.common.entity.role.EmployeePermission;
import htcc.common.entity.role.ManagerRole;
import htcc.common.util.DateTimeUtil;
import htcc.common.util.StringUtil;
import htcc.employee.service.config.DbStaticConfigMap;
import htcc.employee.service.repository.PermissionRepository;
import htcc.employee.service.service.LogService;
import htcc.employee.service.service.checkin.CheckInService;
import htcc.employee.service.service.complaint.ComplaintService;
import htcc.employee.service.service.icon.IconService;
import htcc.employee.service.service.jpa.EmployeeInfoService;
import htcc.employee.service.service.jpa.EmployeePermissionService;
import htcc.employee.service.service.jpa.ManagerRoleService;
import htcc.employee.service.service.leavingrequest.LeavingRequestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Api(tags = "API của quản lý",
     description = "API ở màn hình chính")
@RestController
@Log4j2
public class ManagerHomeController {

    @Autowired
    private LogService logService;

    @Autowired
    private ComplaintService complaintService;

    @Autowired
    private LeavingRequestService leavingRequestService;

    @Autowired
    private IconService iconService;

    @Autowired
    private CheckInService checkInService;

    @Autowired
    private PermissionRepository permissionRepo;

    @Autowired
    private EmployeePermissionService employeePermissionService;

    @Autowired
    private ManagerRoleService managerRoleService;

    @Autowired
    private EmployeeInfoService employeeInfoService;

    @ApiOperation(value = "API Home", response = ManagerHomeResponse.class)
    @GetMapping("/home/manager/{companyId}/{username}")
    public BaseResponse home(@ApiParam(value = "[Path] Mã công ty", required = true)
                             @PathVariable String companyId,
                             @ApiParam(value = "[Path] Tên đăng nhập", defaultValue = "admin", required = true)
                             @PathVariable String username) {
        BaseResponse<ManagerHomeResponse> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        try {
            String yyyyMM = DateTimeUtil.parseTimestampToString(System.currentTimeMillis(), "yyyyMM");

            ManagerHomeResponse data = new ManagerHomeResponse();
            countPendingComplaint(data, companyId);
            countPendingLeavingRequest(data, companyId);
            countPendingCheckIn(data, companyId);
            countUnreadNotifications(data, companyId, username);
            setCanManageOffices(data, companyId, username);
            setCanManageDepartments(data, companyId, username);
            setCanManageEmployees(data, companyId, username);
            setIsSuperAdmin(data, companyId, username);
            setIconList(data);
            setRoleDetail(data, companyId, username);
            setCanAssignRoles(data, companyId, username);
            setLeavingRequestCategories(data, companyId);
            response.setData(data);

        } catch (Exception e) {
            log.error(String.format("home [%s] ex", companyId), e);
            response = new BaseResponse<>(e);
        }
        return response;
    }

    private void setCanAssignRoles(ManagerHomeResponse data, String companyId, String username) {
        try {
            EmployeePermission employee = employeePermissionService.findById(
                    new EmployeePermission.Key(companyId, username));
            if (employee == null) {
                throw new Exception("employeeInfoService.findById return null");
            }

            String managerRole = employee.getManagerRole();
            if (StringUtil.isEmpty(managerRole)) {
                data.setCanAssignRoles(new ArrayList<>());
                return;
            }
            ManagerRole role = managerRoleService.findById(new ManagerRole.Key(companyId, managerRole));
            int roleLevel = role.getRoleLevel();
            List<ManagerRole> canAssignRoles = managerRoleService.findByCompanyId(companyId)
                    .stream()
                    .filter(c -> c.getRoleLevel() >= roleLevel)
                    .collect(Collectors.toList());
            data.setCanAssignRoles(canAssignRoles);
        } catch (Exception e) {
            log.error("[setCanAssignRoles] [{} - {}]", companyId, username, e);
            data.setCanAssignRoles(new ArrayList<>());
        }
    }

    private void setRoleDetail(ManagerHomeResponse data, String companyId, String username) {
        try {
            EmployeePermission employee = employeePermissionService.findById(
                    new EmployeePermission.Key(companyId, username));
            if (employee == null) {
                throw new Exception("employeeInfoService.findById return null");
            }

            String managerRole = employee.getManagerRole();
            if (StringUtil.isEmpty(managerRole)) {
                data.setRoleDetail(new HashMap<>());
                return;
            }
            ManagerRole role = managerRoleService.findById(new ManagerRole.Key(companyId, managerRole));
            data.setRoleDetail(role.getRoleDetail());
        } catch (Exception e) {
            log.error("[setRoleDetail] [{} - {}]", companyId, username, e);
            data.setRoleDetail(new HashMap<>());
        }
    }

    private void setIconList(ManagerHomeResponse data) {
        data.setIconList(iconService.getListIcon());
    }

    private void setIsSuperAdmin(ManagerHomeResponse data, String companyId, String username) {
        try {
            boolean isSuperAdmin = permissionRepo.isSuperAdmin(companyId, username);
            data.setIsSuperAdmin(isSuperAdmin);
        } catch (Exception e){
            log.error("[setIsSuperAdmin] [{} - {}]", companyId, username, e);
        }
    }

    private void setCanManageOffices(ManagerHomeResponse data, String companyId, String username) {
        try {
            List<String> officeList = permissionRepo.getCanManageOffices(companyId, username);
            data.setCanManageOffices(officeList);
        } catch (Exception e){
            log.error("[setCanManageOffices] [{} - {}]", companyId, username, e);
        }
    }

    private void setCanManageDepartments(ManagerHomeResponse data, String companyId, String username) {
        try {
            List<String> departmentList = permissionRepo.getCanManageDepartments(companyId, username);
            data.setCanManageDepartments(departmentList);
        } catch (Exception e){
            log.error("[setCanManageDepartments] [{} - {}]", companyId, username, e);
        }
    }

    private void setCanManageEmployees(ManagerHomeResponse data, String companyId, String username) {
        try {
            List<EmployeeInfo> employeeInfoList = permissionRepo.getCanManageEmployees(companyId, username);
            boolean hasActor = false;
            for (EmployeeInfo employeeInfo : employeeInfoList) {
                if (employeeInfo.getUsername().equals(username)) {
                    hasActor = true;
                    break;
                }
            }

            if (!hasActor) {
                EmployeeInfo employeeInfo = employeeInfoService.findById(new EmployeeInfo.Key(companyId, username));
                if (employeeInfo == null) {
                    throw new Exception("employeeInfoService.findById return null");
                }
                employeeInfoList.add(employeeInfo);
            }

            data.setCanManageEmployees(employeeInfoList);
        } catch (Exception e){
            log.error("[setCanManageEmployees] [{} - {}]", companyId, username, e);
        }
    }

    private void countPendingComplaint(ManagerHomeResponse data, String companyId){
        int count = 0;
        try {
            BaseResponse response = complaintService.countPendingComplaint(companyId);
            if (response != null && response.getReturnCode() == ReturnCodeEnum.SUCCESS.getValue()){
                count = (int) response.getData();
            }
        } catch (Exception e) {
            log.error("[countPendingComplaint] {} ex", companyId, e);
        }
        data.setPendingComplaint(count);
    }

    private void countPendingLeavingRequest(ManagerHomeResponse data, String companyId){
        int count = 0;
        try {
            BaseResponse response = leavingRequestService.countPendingLeavingRequest(companyId);
            if (response != null && response.getReturnCode() == ReturnCodeEnum.SUCCESS.getValue()){
                count = (int) response.getData();
            }
        } catch (Exception e) {
            log.error("[countPendingLeavingRequest] {} ex", companyId, e);
        }
        data.setPendingLeavingRequest(count);
    }

    private void countPendingCheckIn(ManagerHomeResponse data, String companyId) {
        int count = 0;
        try {
            BaseResponse response = checkInService.countPendingCheckIn(companyId);
            if (response != null && response.getReturnCode() == ReturnCodeEnum.SUCCESS.getValue()){
                count = (int) response.getData();
            }
        } catch (Exception e) {
            log.error("[countPendingCheckIn] {} ex", companyId, e);
        }
        data.setPendingCheckIn(count);
    }

    private void countUnreadNotifications(ManagerHomeResponse data, String companyId, String username){
        int count = 0;
        try {
            BaseResponse response = logService.countUnreadNotification(ClientSystemEnum.MANAGER_WEB.getValue(), companyId, username);
            if (response != null && response.getReturnCode() == ReturnCodeEnum.SUCCESS.getValue()){
                count = (int) response.getData();
            }
        } catch (Exception e) {
            log.error("[countUnreadNotifications] [{} - {}] ex", companyId, username, e);
        }
        data.setUnreadNotifications(count);
    }

    private void setLeavingRequestCategories(ManagerHomeResponse data, String companyId) {
        List<String> categoryList = DbStaticConfigMap.COMPANY_DAY_OFF_INFO_MAP
                .get(companyId)
                .getCategoryList()
                .stream()
                .map(CompanyDayOffInfo.CategoryEntity::getCategory)
                .collect(Collectors.toList());

        data.setLeavingRequestCategories(categoryList);
    }
}
