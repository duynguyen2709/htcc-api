package htcc.log.service.service.notification.admin;

import htcc.common.component.kafka.KafkaProducerService;
import htcc.common.constant.ClientSystemEnum;
import htcc.common.entity.notification.AdminSendNotificationRequest;
import htcc.common.entity.notification.NotificationBuz;
import htcc.common.entity.notification.NotificationModel;
import htcc.common.util.DateTimeUtil;
import htcc.common.util.StringUtil;
import htcc.log.service.service.jpa.NotificationBuzService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class AdminSendToUserNotificationService implements AdminSendNotificationService{

    @Autowired
    private NotificationBuzService notificationBuzService;

    @Autowired
    private KafkaProducerService kafka;

    @Override
    public void send(AdminSendNotificationRequest request) {
        try {
            NotificationBuz notificationBuz = notificationBuzService.findById(new NotificationBuz.Key(
                    request.getTargetClientId(), StringUtil.valueOf(request.getCompanyId()), request.getUsername()));

            if (notificationBuz == null) {
                // in case new user has not logged in,
                // then there is no NotificationBuz
                return;
            }

            long   now      = System.currentTimeMillis();
            String yyyyMMdd = DateTimeUtil.parseTimestampToString(now, "yyyyMMdd");

            NotificationModel model = new NotificationModel(request);
            model.setCompanyId(notificationBuz.getCompanyId());
            model.setOfficeId(StringUtil.EMPTY);
            model.setUsername(notificationBuz.getUsername());
            model.setTargetClientId(notificationBuz.getClientId());
            model.setSendTime(now);
            model.setNotiId(String.format("%s-%s-%s-%s-%s%s", yyyyMMdd, ClientSystemEnum.ADMIN_WEB.getValue(),
                    notificationBuz.getClientId(), notificationBuz.getCompanyId(), notificationBuz.getUsername(), now));

            kafka.sendMessage(kafka.getBuzConfig().getEventPushNotification().getTopicName(), model);

        } catch (Exception e) {
            log.error("[send] {} ex", StringUtil.toJsonString(request));
        }
    }
}
