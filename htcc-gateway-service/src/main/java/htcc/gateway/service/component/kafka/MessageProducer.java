package htcc.gateway.service.component.kafka;


import htcc.common.entity.base.RequestLogEntity;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Log4j2
@Component
public class MessageProducer {

    @Autowired(required=false)
    private KafkaTemplate<String, RequestLogEntity> requestLogEntityKafkaTemplate;

    @Value(value = "${kafka.topic.requestLog.name}")
    private String requestLogTopicName;

    public void sendRequestLogMessage(RequestLogEntity requestLogEntity) {
        ListenableFuture<SendResult<String, RequestLogEntity>> future = requestLogEntityKafkaTemplate.send(requestLogTopicName, requestLogEntity);
        future.addCallback(new ListenableFutureCallback<SendResult<String, RequestLogEntity>>() {

            @Override
            public void onSuccess(SendResult<String, RequestLogEntity> result) {
                log.info("Sent message=[" + requestLogEntity.toString() + "] with offset=[" + result.getRecordMetadata()
                        .offset() + "]");
            }

            @Override
            public void onFailure(Throwable ex) {
                log.error("Unable to send message=[" + requestLogEntity.toString() + "] due to : " + ex.getMessage());
            }
        });
    }
}
