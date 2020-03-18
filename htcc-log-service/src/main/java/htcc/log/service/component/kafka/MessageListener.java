package htcc.log.service.component.kafka;


import htcc.common.entity.base.RequestLogEntity;
import htcc.log.service.repository.RequestLogDAO;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.CountDownLatch;

@Log4j2
@Component
public class MessageListener {
    private CountDownLatch requestLatch = new CountDownLatch(1);

    @Autowired
    private RequestLogDAO requestLogDAO;

    @KafkaListener(
            topics = "${kafka.topic.requestLog.name}",
            containerFactory = "requestLogEntityConcurrentKafkaListenerContainerFactory"
    )
    public void requestLogListener(ConsumerRecord consumerRecord){
        Map<String, Object> map = (Map<String, Object>) consumerRecord.value();
        RequestLogEntity requestLog = new RequestLogEntity(map);

        requestLogDAO.setTableName("ApiLog");
        requestLogDAO.addRequestLog(requestLog);

        log.info("Receive request message: " + requestLog.toString());
        requestLatch.countDown();
    }
}
