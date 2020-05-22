package htcc.employee.service.controller.notification;

import htcc.common.constant.NotificationReceiverSystemEnum;
import htcc.common.constant.ReturnCodeEnum;
import htcc.common.entity.base.BaseResponse;
import htcc.common.entity.jpa.EmployeeInfo;
import htcc.common.entity.notification.ManagerSendNotificationRequest;
import htcc.common.entity.notification.NotificationModel;
import htcc.common.util.DateTimeUtil;
import htcc.common.util.StringUtil;
import htcc.employee.service.repository.EmployeePermissionRepository;
import htcc.employee.service.service.icon.IconService;
import htcc.employee.service.service.notification.NotificationService;
import htcc.employee.service.service.jpa.EmployeeInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api(tags = "Notification API",
     description = "API gửi thông báo")
@RestController
@Log4j2
public class ManagerSendNotificationController {

    @Autowired
    private EmployeePermissionRepository permissionRepo;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private EmployeeInfoService employeeInfoService;

    @Autowired
    private IconService iconService;

    @ApiOperation(value = "Gửi thông báo", response = BaseResponse.class)
    @PostMapping("/notifications/manager")
    public BaseResponse sendNotification(@RequestBody ManagerSendNotificationRequest request) {
        BaseResponse response = new BaseResponse(ReturnCodeEnum.SUCCESS);
        response.setReturnMessage("Gửi thông báo thành công");
        try {
            String error = request.isValid();
            if (!error.isEmpty()) {
                response = new BaseResponse<>(ReturnCodeEnum.PARAM_DATA_INVALID);
                response.setReturnMessage(error);
                return response;
            }

            if (request.getReceiverType() == NotificationReceiverSystemEnum.COMPANY.getValue()) {
                if (!permissionRepo.isSuperAdmin(request.getCompanyId(), request.getSender())) {
                    response = new BaseResponse<>(ReturnCodeEnum.INSUFFICIENT_PRIVILEGES);
                    return response;
                }
            }

            if (request.getReceiverType() == NotificationReceiverSystemEnum.OFFICE.getValue()) {
                if (!permissionRepo.canManageOffice(request.getCompanyId(), request.getSender(), request.getOfficeId())) {
                    response = new BaseResponse<>(ReturnCodeEnum.INSUFFICIENT_PRIVILEGES);
                    return response;
                }
            }

            if (request.getReceiverType() == NotificationReceiverSystemEnum.USER.getValue()) {
                if (!permissionRepo.canManageEmployee(request.getCompanyId(), request.getSender(), request.getUsername())) {
                    response = new BaseResponse<>(ReturnCodeEnum.INSUFFICIENT_PRIVILEGES);
                    return response;
                }
            }

            if (!iconService.containIcon(request.getIconId())) {
                response = new BaseResponse<>(ReturnCodeEnum.DATA_NOT_FOUND);
                response.setReturnMessage("Không tìm thấy icon có id " + request.getIconId());
                return response;
            }

            notificationService.managerSendNotification(request);

        } catch (Exception e) {
            log.error("[sendNotification] {} ex", StringUtil.toJsonString(request), e);
            response = new BaseResponse<>(e);
        }
        return response;
    }


    @ApiOperation(value = "Lấy danh sách thông báo đã gửi theo ngày", response = NotificationModel.class)
    @GetMapping("/notifications/manager/{companyId}/{username}/{yyyyMMdd}")
    public BaseResponse getListNotification(@PathVariable String companyId,
                                            @PathVariable String username,
                                            @PathVariable String yyyyMMdd) {
        BaseResponse<List<NotificationModel>> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        try {
            if (!DateTimeUtil.isRightFormat(yyyyMMdd, "yyyyMMdd")) {
                response = new BaseResponse<>(ReturnCodeEnum.DATE_WRONG_FORMAT);
                response.setReturnMessage(String.format("Ngày %s không phù hợp định dạng yyyyMMdd", yyyyMMdd));
                return response;
            }

            List<NotificationModel> dataResponse = new ArrayList<>();

            List<String> listSender = new ArrayList<>();

            // TODO : GET ALL NOTI SEND TO SAME OFFICE

            // super admin has permission to view all notifications send by others
            if (permissionRepo.isSuperAdmin(companyId, username)) {
                List<EmployeeInfo> employeeList = employeeInfoService.findByCompanyId(companyId);
                if (employeeList == null) {
                    throw new Exception("employeeInfoService.findByCompanyId return null");
                }

                employeeList.forEach(c -> listSender.add(c.getUsername()));
            }
            else {
                listSender.add(username);
            }

            for (String sender : listSender) {
                List<NotificationModel> listNoti = notificationService.getListNotificationForManager(companyId, username, yyyyMMdd);
                if (listNoti == null) {
                    throw new Exception("notificationService.getListNotification return null for sender " + sender);
                }
                dataResponse.addAll(listNoti);
            }

            response.setData(dataResponse);
            return response;

        } catch (Exception e) {
            log.error("[getListNotification] [{}] ex", yyyyMMdd, e);
            response = new BaseResponse<>(e);
        }
        return response;
    }
}
