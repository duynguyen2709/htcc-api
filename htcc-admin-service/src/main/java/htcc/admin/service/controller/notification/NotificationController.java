package htcc.admin.service.controller.notification;

import htcc.admin.service.service.NotificationService;
import htcc.admin.service.service.jpa.AdminUserInfoService;
import htcc.common.component.kafka.KafkaProducerService;
import htcc.common.constant.Constant;
import htcc.common.constant.ReturnCodeEnum;
import htcc.common.entity.base.BaseResponse;
import htcc.common.entity.jpa.AdminUser;
import htcc.common.entity.notification.AdminSendNotificationRequest;
import htcc.common.entity.notification.NotificationModel;
import htcc.common.util.DateTimeUtil;
import htcc.common.util.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api(tags = "Notification API",
     description = "API gửi thông báo")
@RestController
@Log4j2
public class NotificationController {

    @Autowired
    private KafkaProducerService kafka;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private AdminUserInfoService adminUserInfoService;

    @ApiOperation(value = "Gửi thông báo", response = BaseResponse.class)
    @PostMapping("/notifications")
    public BaseResponse sendNotification(@RequestBody AdminSendNotificationRequest request) {
        BaseResponse response = new BaseResponse(ReturnCodeEnum.SUCCESS);
        response.setReturnMessage("Gửi thông báo thành công");
        try {
            String error = request.isValid();
            if (!error.isEmpty()) {
                response = new BaseResponse<>(ReturnCodeEnum.PARAM_DATA_INVALID);
                response.setReturnMessage(error);
                return response;
            }

            kafka.sendMessage(kafka.getBuzConfig().getEventAdminSendNotification().getTopicName(), request);

        } catch (Exception e) {
            log.error("[sendNotification] {} ex", StringUtil.toJsonString(request), e);
            response = new BaseResponse<>(e);
        }
        return response;
    }


    @ApiOperation(value = "Lấy danh sách thông báo đã gửi theo ngày", response = NotificationModel.class)
    @GetMapping("/notifications/{yyyyMMdd}")
    public BaseResponse getListNotification(@PathVariable String yyyyMMdd,
                                            @ApiParam(hidden = true) @RequestHeader(Constant.USERNAME) String username) {
        BaseResponse<List<NotificationModel>> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        try {
            if (!DateTimeUtil.isRightFormat(yyyyMMdd, "yyyyMMdd")) {
                response = new BaseResponse<>(ReturnCodeEnum.DATE_WRONG_FORMAT,
                        String.format("Ngày %s không phù hợp định dạng yyyyMMdd", yyyyMMdd));
                return response;
            }

            List<NotificationModel> dataResponse = new ArrayList<>();

            List<String> listSender = new ArrayList<>();

            // super admin has permission to view all notifications send by other admins
            if (isSuperAdmin(username)) {
                List<AdminUser> listAdmin = adminUserInfoService.findAll();
                if (listAdmin == null) {
                    throw new Exception("adminUserInfoService.findAll return null");
                }

                listAdmin.forEach(c -> listSender.add(c.getUsername()));
            } else {
                listSender.add(username);
            }

            for (String sender : listSender) {
                List<NotificationModel> listNoti = notificationService.getListNotification(yyyyMMdd, sender);
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

    private boolean isSuperAdmin(String username) {
        return adminUserInfoService.findById(username).getRole() == 0;
    }
}
