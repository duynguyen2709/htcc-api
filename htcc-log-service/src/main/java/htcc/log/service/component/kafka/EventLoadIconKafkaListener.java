package htcc.log.service.component.kafka;

import com.google.gson.reflect.TypeToken;
import htcc.common.entity.icon.NotificationIconConfig;
import htcc.common.service.kafka.BaseKafkaConsumer;
import htcc.common.util.StringUtil;
import htcc.log.service.service.icon.IconService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Log4j2
@ConditionalOnProperty(value = "kafka.enableConsumer", havingValue = "true")
@KafkaListener(topics = "#{kafkaFileConfig.buz.eventLoadIcon.topicName}",
               groupId = "#{kafkaFileConfig.buz.eventLoadIcon.groupId}")
public class EventLoadIconKafkaListener extends BaseKafkaConsumer<String> {

    @Autowired
    private IconService iconService;

    @Override
    public void process(String jsonMap) {
        try {
            Map<String, NotificationIconConfig> map = StringUtil.json2Collection(jsonMap,
                    new TypeToken<Map<String, NotificationIconConfig>>(){}.getType());
            iconService.setNotiIconMap(map);
        } catch (Exception e) {
            log.error("[process] {} ex", jsonMap, e);
        }
    }
}
