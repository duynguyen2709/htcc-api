package htcc.log.service.controller.notification;

import htcc.common.constant.ReturnCodeEnum;
import htcc.common.entity.base.BaseResponse;
import htcc.common.entity.notification.NotificationLogEntity;
import htcc.common.entity.notification.NotificationModel;
import htcc.log.service.repository.ManagerNotificationLogRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Log4j2
@RequestMapping("/internal/logs")
public class ManagerNotificationLogController {

    @Autowired
    private ManagerNotificationLogRepository repo;

    @GetMapping("/notifications/manager")
    public BaseResponse getListNotification(@RequestParam String yyyyMMdd, @RequestParam String sender, @RequestParam String companyId) {
        BaseResponse<List<NotificationModel>> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        try {
            List<NotificationLogEntity> rawData = repo.getListNotificationLog(companyId, sender, yyyyMMdd);

            if (rawData == null){
                throw new Exception("repo.getListNotification return null");
            }

            List<NotificationModel> dataResponse = rawData.stream()
                    .map(NotificationModel::new)
                    .collect(Collectors.toList());

            response.setData(dataResponse);
        } catch (Exception e){
            log.error("[getListNotification] [{} - {}] ex", yyyyMMdd, sender, e);
            response = new BaseResponse<>(e);
        }
        return response;
    }
}
