package htcc.employee.service.controller.notification;

import htcc.common.component.LoggingConfiguration;
import htcc.common.constant.ClientSystemEnum;
import htcc.common.constant.NotificationStatusEnum;
import htcc.common.constant.ReturnCodeEnum;
import htcc.common.entity.base.BaseResponse;
import htcc.common.entity.notification.CreateNotificationRequest;
import htcc.common.entity.notification.NotificationModel;
import htcc.common.entity.notification.NotificationResponse;
import htcc.common.entity.notification.UpdateNotificationReadStatusModel;
import htcc.common.util.DateTimeUtil;
import htcc.common.util.StringUtil;
import htcc.employee.service.service.NotificationService;
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
public class NotificationController {

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
                    .getListNotification(companyId, username, startIndex, size);

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



    // TODO : DELETE THIS METHOD AFTER TESTING
    @ApiOperation(value = "Tạo noti mới", response = BaseResponse.class)
    @PostMapping("/notifications")
    public BaseResponse createNoti(@ApiParam(name = "request", value = "[Body] Thông tin noti mới", required = true)
                                                        @RequestBody CreateNotificationRequest request) {
        BaseResponse response = new BaseResponse(ReturnCodeEnum.SUCCESS);
        try {
            long now = System.currentTimeMillis();
            NotificationModel model = new NotificationModel();
            model.setTargetClientId(ClientSystemEnum.MOBILE.getValue());
            model.setRequestId(LoggingConfiguration.getTraceId());
            model.setCompanyId(request.getCompanyId());
            model.setUsername(request.getUsername());
            model.setSendTime(now);
            model.setRetryTime(0);
            model.setTitle(request.getTitle());
            model.setContent(request.getContent());
            model.setStatus(NotificationStatusEnum.INIT.getValue());
            model.setHasRead(false);
            model.setScreenId(request.getScreenId());
            model.setNotiId(String.format("%s-%s-%s-%s-%s",
                    DateTimeUtil.parseTimestampToString(now, "yyyyMMdd"),
                    model.getTargetClientId(), model.getCompanyId(), model.getUsername(), now));
            switch (model.getScreenId()){
                case 1:
                    model.setIconId("checkin");
//                    model.setIconUrl("https://drive.google.com/uc?export=view&id=13VkeHpPGGQPIqSPylQHX1FBGaLpJ6kM3");
                    break;
                case 7:
                    model.setIconId("complaint");
//                    model.setIconUrl("https://drive.google.com/uc?export=view&id=1bAZ6NAfVxFb1jPWqT8wIkPGaZ9ZObi3a");
                    break;
                case 2:
                    model.setIconId("leaving");
//                    model.setIconUrl("https://drive.google.com/uc?export=view&id=181Xuew9SGy17KJ2x6wHeLJaRTBFkNHZg");
                    break;
                case 6:
                    model.setIconId("payroll");
//                    model.setIconUrl("https://drive.google.com/uc?export=view&id=14zSFk6qYhBHLINARDzWbv8IWG8_q8KAU");
                    break;
                case 4:
                    model.setIconId("personal");
//                    model.setIconUrl("https://drive.google.com/uc?export=view&id=15q22GGIOOPY6wFgKumH-M88VDQ7sKUru");
                    break;
                case 3:
                    model.setIconId("statistics");
//                    model.setIconUrl("https://drive.google.com/uc?export=view&id=1ASCBAWzpW2Gxr74Y2dNvkocaxKkxJ_iv");
                    break;
                default:
                    model.setIconId("noti");
//                    model.setIconUrl("https://drive.google.com/uc?export=view&id=1lwV3OFqdTDH3cFHt-cAruMVGN4SV6yTi");
                    break;
            }

            notiService.sendNotification(model);
        } catch (Exception e){
            log.error("[createNoti] request = {}, ex", StringUtil.toJsonString(request), e);
            response = new BaseResponse(e);
        }
        return response;
    }
}
