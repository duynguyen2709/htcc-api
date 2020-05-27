package htcc.log.service.component.kafka;

import htcc.common.entity.notification.AdminSendNotificationRequest;
import htcc.common.service.kafka.BaseKafkaConsumer;
import htcc.common.util.StringUtil;
import htcc.log.service.service.notification.admin.AdminSendNotificationFactory;
import htcc.log.service.service.notification.admin.AdminSendNotificationService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Log4j2
@ConditionalOnProperty(value = "kafka.enableConsumer", havingValue = "true")
@KafkaListener(topics = "#{kafkaFileConfig.buz.eventAdminSendNotification.topicName}",
               groupId = "#{kafkaFileConfig.buz.eventAdminSendNotification.groupId}")
public class EventAdminSendNotificationKafkaListener extends BaseKafkaConsumer<AdminSendNotificationRequest> {

    @Autowired
    private AdminSendNotificationFactory notificationFactory;


    @Override
    public void process(AdminSendNotificationRequest request) {
        try {
            AdminSendNotificationService sendNotiService = notificationFactory.getSubService(request.getReceiverType());
            if (sendNotiService == null) {
                throw new Exception("notificationFactory.getSubService return null");
            }

            sendNotiService.send(request);
        } catch (Exception e) {
            log.error("[process] {} ex", StringUtil.toJsonString(request), e);
        }
    }
}
