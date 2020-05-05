package htcc.log.service.service.notification;

import htcc.common.component.kafka.KafkaProducerService;
import htcc.common.entity.notification.NotificationModel;
import htcc.common.util.StringUtil;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class NotificationRetryTask implements Runnable {

    private NotificationModel model;
    private KafkaProducerService kafka;

    public NotificationRetryTask(NotificationModel model, KafkaProducerService kafka) {
        this.model = model;
        this.kafka = kafka;
    }

    @Override
    public void run() {
        log.info("...Retrying send failed notification {}...", StringUtil.toJsonString(model));
        kafka.sendMessage(kafka.getBuzConfig().getEventPushNotification().getTopicName(), model);
    }
}
