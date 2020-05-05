package htcc.log.service.component.kafka;

import htcc.common.entity.leavingrequest.LeavingRequestLogEntity;
import htcc.common.entity.leavingrequest.LeavingRequestModel;
import htcc.common.entity.leavingrequest.UpdateLeavingRequestStatusModel;
import htcc.common.entity.notification.UpdateNotificationReadStatusModel;
import htcc.common.service.kafka.BaseKafkaConsumer;
import htcc.common.util.DateTimeUtil;
import htcc.common.util.StringUtil;
import htcc.log.service.repository.BaseLogDAO;
import htcc.log.service.repository.LeavingRequestLogRepository;
import htcc.log.service.repository.NotificationLogRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Log4j2
@ConditionalOnProperty(value = "kafka.enableConsumer", havingValue = "true")
@KafkaListener(topics = "#{kafkaFileConfig.buz.eventReadNotification.topicName}",
               groupId = "#{kafkaFileConfig.buz.eventReadNotification.groupId}")
public class EventReadNotificationKafkaListener extends BaseKafkaConsumer<UpdateNotificationReadStatusModel> {

    @Autowired
    private NotificationLogRepository notificationLogRepository;

    @Override
    public void process(UpdateNotificationReadStatusModel model) {
        try {
            if (model.getType() == 0){
                notificationLogRepository.updateReadAllNotification(model);
            } else {
                notificationLogRepository.updateReadOneNotification(model);
            }
        } catch (Exception e) {
            log.error("process {} ex", StringUtil.toJsonString(model), e);
        }
    }
}
