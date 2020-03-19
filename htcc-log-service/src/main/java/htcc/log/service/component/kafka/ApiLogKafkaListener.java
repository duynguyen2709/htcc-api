package htcc.log.service.component.kafka;

import htcc.common.entity.base.RequestLogEntity;
import htcc.common.service.kafka.BaseKafkaConsumer;
import htcc.common.util.StringUtil;
import htcc.log.service.repository.BaseLogDAO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Log4j2
@KafkaListener(topics = "#{kafkaFileConfig.buz.apiLog.topicName}",
               groupId = "#{kafkaFileConfig.buz.apiLog.groupId}")
public class ApiLogKafkaListener extends BaseKafkaConsumer<RequestLogEntity> {

    @Autowired
    private BaseLogDAO repo;

    @Override
    public void process(RequestLogEntity baseMessage) {
        repo.insertLog(baseMessage);
    }
}
