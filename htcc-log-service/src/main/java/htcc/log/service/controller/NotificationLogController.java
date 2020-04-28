package htcc.log.service.controller;

import htcc.common.constant.ReturnCodeEnum;
import htcc.common.entity.base.BaseResponse;
import htcc.common.entity.notification.NotificationLogEntity;
import htcc.common.entity.notification.NotificationModel;
import htcc.common.util.StringUtil;
import htcc.log.service.repository.NotificationLogRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Log4j2
@RequestMapping("/internal/logs")
public class NotificationLogController {

    @Autowired
    private NotificationLogRepository repo;

    @GetMapping("/notifications")
    public BaseResponse getListNotification(@RequestParam int clientId, @RequestParam String companyId,
                                                       @RequestParam String username, @RequestParam Integer startIndex,
                                                       @RequestParam Integer size) {
        BaseResponse<List<NotificationModel>> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        try {
            List<NotificationLogEntity> rawData = repo.getListNotification(clientId,
                    StringUtil.valueOf(companyId), username, startIndex, size);

            if (rawData == null){
                throw new Exception("repo.getListNotification return null");
            }

            List<NotificationModel> dataResponse =
                    rawData.stream().map(NotificationModel::new)
                            .sorted(new Comparator<NotificationModel>() {
                                @Override
                                public int compare(NotificationModel o1, NotificationModel o2) {
                                    return Long.compare(o2.getSendTime(), o1.getSendTime());
                                }
                            }).collect(Collectors.toList());

            response.setData(dataResponse);
        } catch (Exception e){
            log.error("[getListNotification] [{} - {} - {} - {} - {}] ex",
                    clientId, companyId, username, startIndex, size, e);
            response = new BaseResponse<>(e);
        }
        return response;
    }
}
