package htcc.log.service.component.kafka;


import htcc.common.entity.base.RequestLogEntity;
import htcc.common.util.StringUtil;
import htcc.log.service.repository.LogDAO;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Log4j2
@Component
public class MessageListener {

    @Autowired
    private LogDAO logDAO;

    @KafkaListener(
            topics = "${kafka.topic.requestLog.name}",
            containerFactory = "requestLogEntityConcurrentKafkaListenerContainerFactory"
    )
    public void requestLogListener(ConsumerRecord consumerRecord){
        Map<String, Object> rawValue = (Map<String, Object>) consumerRecord.value();
        try {
            RequestLogEntity requestLog = StringUtil.fromMapToObject(rawValue, RequestLogEntity.class);
            if (logDAO.insertLog(requestLog) != 0) {
                log.info("Inserted ApiLog {}", StringUtil.toJsonString(requestLog));
            }
        } catch (Exception e) {
            log.error(e);
        }
    }
}
