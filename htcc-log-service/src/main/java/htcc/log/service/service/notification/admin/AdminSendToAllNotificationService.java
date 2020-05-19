package htcc.log.service.service.notification.admin;

import htcc.common.component.kafka.KafkaProducerService;
import htcc.common.entity.notification.AdminSendNotificationRequest;
import htcc.common.entity.notification.NotificationBuz;
import htcc.common.entity.notification.NotificationModel;
import htcc.common.util.DateTimeUtil;
import htcc.common.util.StringUtil;
import htcc.log.service.service.jpa.NotificationBuzService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class AdminSendToAllNotificationService implements AdminSendNotificationService{

    @Autowired
    private NotificationBuzService notificationBuzService;

    @Autowired
    private KafkaProducerService kafka;

    @Override
    public void send(AdminSendNotificationRequest request) {
        try {
            List<NotificationBuz> notificationBuzList = notificationBuzService.findAll();
            long now = System.currentTimeMillis();
            String yyyyMMdd = DateTimeUtil.parseTimestampToString(now, "yyyyMMdd");

            for (NotificationBuz entity : notificationBuzList) {
                NotificationModel model = new NotificationModel(request);
                model.setCompanyId(entity.getCompanyId());
                model.setOfficeId(StringUtil.EMPTY);
                model.setUsername(entity.getUsername());
                model.setTargetClientId(entity.getClientId());
                model.setReceiverType(request.getReceiverType());
                model.setSendTime(now);
                model.setNotiId(String.format("%s_%s", yyyyMMdd, now));

                kafka.sendMessage(kafka.getBuzConfig().getEventPushNotification().getTopicName(), model);
            }

        } catch (Exception e) {
            log.error("[send] {} ex", StringUtil.toJsonString(request));
        }
    }
}
