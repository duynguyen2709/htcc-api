package htcc.employee.service.controller.notification;

import htcc.common.component.LoggingConfiguration;
import htcc.common.constant.ClientSystemEnum;
import htcc.common.constant.NotificationStatusEnum;
import htcc.common.constant.ReturnCodeEnum;
import htcc.common.entity.base.BaseResponse;
import htcc.common.entity.notification.NotificationModel;
import htcc.common.entity.notification.NotificationResponse;
import htcc.common.entity.notification.UpdateNotificationReadStatusModel;
import htcc.common.util.DateTimeUtil;
import htcc.common.util.StringUtil;
import htcc.employee.service.service.notification.NotificationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Api(tags = "API Get list notification")
@RestController
@Log4j2
public class EmployeeMobileNotificationController {

    @Autowired
    private NotificationService notiService;

    @ApiOperation(value = "Lấy danh sách notification", response = NotificationResponse.class)
    @GetMapping("/notifications/{companyId}/{username}")
    public BaseResponse getListNotification(@ApiParam(name = "companyId", value = "[Path] Mã công ty", defaultValue = "VNG", required = true)
                                       @PathVariable String companyId,
                                       @ApiParam(name = "username", value = "[Path] Tên đăng nhập", defaultValue = "admin", required = true)
                                       @PathVariable String username,
                                       @ApiParam(name = "index", value = "[QueryString] Phân trang (0,1,2...)", defaultValue = "0", required = false)
                                       @RequestParam(required = true, defaultValue = "0") Integer index,
                                       @ApiParam(name = "size", value = "[QueryString] Số record mỗi trang, nếu không gửi sẽ lấy default 20," +
                                               " nếu khác 0 cần gửi thêm index để lấy trang tiếp theo",
                                                 defaultValue = "0", required = false)
                                       @RequestParam(required = false, defaultValue = "0") Integer size) {
        BaseResponse<List<NotificationResponse>> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        try {
            if (size == null || size == 0){
                size = 20;
            }

            int startIndex = size * index;

            List<NotificationModel> models = notiService
                    .getListNotification(ClientSystemEnum.MOBILE.getValue(), companyId, username, startIndex, size);
            if (models == null) {
                throw new Exception("notiService.getListNotification return null");
            }

            models.sort(new Comparator<NotificationModel>() {
                @Override
                public int compare(NotificationModel o1, NotificationModel o2) {
                    return Long.compare(o2.getSendTime(), o1.getSendTime());
                }
            });

            List<NotificationResponse> notificationList = models
                    .stream()
                    .map(NotificationResponse::new)
                    .collect(Collectors.toList());

            response.setData(notificationList);
        } catch (Exception e){
            log.error(String.format("[getListNotification] [%s-%s-%s-%s]",
                    companyId, username, index, size), e);
            response = new BaseResponse<>(e);
        }
        return response;
    }

    @ApiOperation(value = "Lấy danh sách notification (CHO QUẢN LÝ)", response = NotificationResponse.class)
    @GetMapping("/notifications/manager/{companyId}/{username}")
    public BaseResponse getListNotificationForManager(@ApiParam(name = "companyId", value = "[Path] Mã công ty", defaultValue = "VNG", required = true)
                                            @PathVariable String companyId,
                                            @ApiParam(name = "username", value = "[Path] Tên đăng nhập", defaultValue = "admin", required = true)
                                            @PathVariable String username,
                                            @ApiParam(name = "index", value = "[QueryString] Phân trang (0,1,2...)", defaultValue = "0", required = false)
                                            @RequestParam(required = true, defaultValue = "0") Integer index,
                                            @ApiParam(name = "size", value = "[QueryString] Số record mỗi trang, nếu không gửi sẽ lấy default 20," +
                                                    " nếu khác 0 cần gửi thêm index để lấy trang tiếp theo",
                                                      defaultValue = "0", required = false)
                                            @RequestParam(required = false, defaultValue = "0") Integer size) {
        BaseResponse<List<NotificationResponse>> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        try {
            if (size == null || size == 0){
                size = 20;
            }

            int startIndex = size * index;

            List<NotificationModel> models = notiService
                    .getListNotification(ClientSystemEnum.MANAGER_WEB.getValue(), companyId, username, startIndex, size);
            if (models == null) {
                throw new Exception("notiService.getListNotification return null");
            }

            models.sort(new Comparator<NotificationModel>() {
                @Override
                public int compare(NotificationModel o1, NotificationModel o2) {
                    return Long.compare(o2.getSendTime(), o1.getSendTime());
                }
            });

            List<NotificationResponse> notificationList = models
                    .stream()
                    .map(NotificationResponse::new)
                    .collect(Collectors.toList());

            response.setData(notificationList);
        } catch (Exception e){
            log.error(String.format("[getListNotification] [%s-%s-%s-%s]",
                    companyId, username, index, size), e);
            response = new BaseResponse<>(e);
        }
        return response;
    }


    @ApiOperation(value = "Cập nhật trạng thái đã đọc của noti", response = BaseResponse.class)
    @PostMapping("/notifications/status")
    public BaseResponse updateNotificationHasReadStatus(@ApiParam(name = "request", value = "[Body] Thông tin noti cần update", required = true)
                                                @RequestBody UpdateNotificationReadStatusModel request) {
        BaseResponse response = new BaseResponse(ReturnCodeEnum.SUCCESS);
        try {
            if (!request.isValid().isEmpty()){
                throw new Exception("Data invalid");
            }
            request.setClientId(ClientSystemEnum.MOBILE.getValue());

            notiService.updateNotificationHasReadStatus(request);
        } catch (Exception e){
            log.error("[updateNotificationHasReadStatus] request = {}, ex", StringUtil.toJsonString(request), e);
            response = new BaseResponse(e);
        }
        return response;
    }
}
