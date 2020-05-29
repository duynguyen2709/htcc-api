package htcc.admin.service.component.kafka;

import htcc.common.component.kafka.KafkaProducerService;
import htcc.common.service.kafka.BaseKafkaConsumer;
import htcc.common.util.StringUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static htcc.admin.service.config.DbStaticConfigMap.NOTI_ICON_MAP;

@Component
@Log4j2
@ConditionalOnProperty(value = "kafka.enableConsumer", havingValue = "true")
@KafkaListener(topics = "#{kafkaFileConfig.buz.eventRequireIcon.topicName}",
               groupId = "#{kafkaFileConfig.buz.eventRequireIcon.groupId}")
public class EventRequireIconKafkaListener extends BaseKafkaConsumer<String> {

    @Autowired
    private KafkaProducerService kafka;

    @Override
    public void process(String service) {
        try {
            // send kafka message here for employee & log service to get list icon
            kafka.sendMessage(kafka.getBuzConfig().getEventLoadIcon().getTopicName(), StringUtil.toJsonString(NOTI_ICON_MAP));
        } catch (Exception e) {
            log.error("[process] [{}] ex", service, e);
        }
    }
}
