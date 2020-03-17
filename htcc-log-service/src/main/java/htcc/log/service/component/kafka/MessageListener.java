package htcc.log.service.component.kafka;


import htcc.common.entity.base.RequestLogEntity;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.CountDownLatch;

@Log4j2
@Component
public class MessageListener {
    private CountDownLatch requestLatch = new CountDownLatch(1);

    @KafkaListener(
            topics = "${kafka.topic.requestLog.name}",
            containerFactory = "requestLogEntityConcurrentKafkaListenerContainerFactory"
    )
    public void requestLogListener(ConsumerRecord consumerRecord){
        Map<String, Object> map = (Map<String, Object>) consumerRecord.value();
        RequestLogEntity request = new RequestLogEntity(map);

        log.info("Receive request message: " + request.toString());
        requestLatch.countDown();
    }
}
