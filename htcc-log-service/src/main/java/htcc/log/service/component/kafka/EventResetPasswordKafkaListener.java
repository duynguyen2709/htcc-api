package htcc.log.service.component.kafka;

import htcc.common.entity.base.BaseUser;
import htcc.common.service.kafka.BaseKafkaConsumer;
import htcc.common.util.StringUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Log4j2
@ConditionalOnProperty(value = "kafka.enableConsumer", havingValue = "true")
@KafkaListener(topics = "#{kafkaFileConfig.buz.eventResetPassword.topicName}",
               groupId = "#{kafkaFileConfig.buz.eventResetPassword.groupId}")
public class EventResetPasswordKafkaListener extends BaseKafkaConsumer<BaseUser> {

    @Override
    public void process(BaseUser baseUser) {
        try {
            // TODO : Implement this method
            log.info(StringUtil.toJsonString(baseUser));
        } catch (Exception e) {
            log.error("process {} ex", StringUtil.toJsonString(baseUser), e);
        }
    }
}
