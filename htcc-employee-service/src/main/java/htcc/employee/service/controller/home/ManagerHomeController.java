package htcc.employee.service.controller.home;

import htcc.common.constant.ReturnCodeEnum;
import htcc.common.entity.base.BaseResponse;
import htcc.common.entity.home.ManagerHomeResponse;
import htcc.common.entity.jpa.EmployeeInfo;
import htcc.common.util.DateTimeUtil;
import htcc.employee.service.repository.EmployeePermissionRepository;
import htcc.employee.service.service.complaint.ComplaintService;
import htcc.employee.service.service.leavingrequest.LeavingRequestService;
import htcc.employee.service.service.icon.IconService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "API của quản lý",
     description = "API ở màn hình chính")
@RestController
@Log4j2
public class ManagerHomeController {

    @Autowired
    private ComplaintService complaintService;

    @Autowired
    private LeavingRequestService leavingRequestService;

    @Autowired
    private IconService iconService;

    @Autowired
    private EmployeePermissionRepository permissionRepo;

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
            setCanManageOffices(data, companyId, username);
            setCanManageEmployees(data, companyId, username);
            setIsSuperAdmin(data, companyId, username);
            setIconList(data);
            response.setData(data);

        } catch (Exception e) {
            log.error(String.format("home [%s] ex", companyId), e);
            response = new BaseResponse<>(e);
        }
        return response;
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

    private void setCanManageEmployees(ManagerHomeResponse data, String companyId, String username) {
        try {
            List<EmployeeInfo> employeeInfoList = permissionRepo.getCanManageEmployees(companyId, username);
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
}
