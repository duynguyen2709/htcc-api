package htcc.log.service.component.kafka;

import htcc.common.component.kafka.KafkaProducerService;
import htcc.common.constant.ClientSystemEnum;
import htcc.common.constant.Constant;
import htcc.common.constant.NotificationStatusEnum;
import htcc.common.entity.notification.NotificationBuz;
import htcc.common.entity.notification.NotificationModel;
import htcc.common.service.kafka.BaseKafkaConsumer;
import htcc.common.util.StringUtil;
import htcc.log.service.config.NotificationConfig;
import htcc.log.service.repository.NotificationBuzRepository;
import htcc.log.service.repository.NotificationLogRepository;
import htcc.log.service.service.notification.NotificationRetryTask;
import htcc.log.service.service.notification.NotificationService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Log4j2
@ConditionalOnProperty(value = "kafka.enableConsumer", havingValue = "true")
@KafkaListener(topics = "#{kafkaFileConfig.buz.eventPushNotification.topicName}",
               groupId = "#{kafkaFileConfig.buz.eventPushNotification.groupId}")
public class EventPushNotificationListener extends BaseKafkaConsumer<NotificationModel> {

    //<editor-fold defaultstate="collapsed" desc="Autowired">
    @Autowired
    private NotificationBuzRepository notificationBuzRepository;

    @Autowired
    private NotificationConfig notificationConfig;

    @Autowired
    private NotificationLogRepository repository;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private ThreadPoolTaskScheduler taskScheduler;

    @Autowired
    private KafkaProducerService kafka;
    //</editor-fold>

    @Override
    public void process(NotificationModel model) {
        // TODO : Check other clientId
        if (model.getClientId() != ClientSystemEnum.MOBILE.getValue()){
            return;
        }

        try {
            if (System.currentTimeMillis() - model.getSendTime() > Constant.ONE_DAY_MILLISECONDS){
                log.warn("Discard Notification {} after 24 hours", StringUtil.toJsonString(model));
                model.setStatus(NotificationStatusEnum.FAILED.getValue());
                repository.saveNotification(model);
                return;
            }

            NotificationBuz.Key key = new NotificationBuz.Key(model.getClientId(), model.getCompanyId(), model.getUsername());
            NotificationBuz buzEntity = notificationBuzRepository.findById(key).orElse(null);
            if (buzEntity == null) {
                throw new Exception("Can not find NotificationBuz: " + StringUtil.toJsonString(key));
            }

            if (buzEntity.getIsLoggedIn() == 0) {
                // save noti to redis for 24 hours
                model.setStatus(NotificationStatusEnum.PENDING.getValue());
                notificationService.savePendingNotification(model);
                repository.saveNotification(model);
            } else {
                // send noti
                model.setTokenPush(StringUtil.json2Collection(buzEntity.getTokens(),
                        StringUtil.LIST_STRING_TYPE));

                boolean sendNotiResponse = notificationService.sendNotify(model);
                if (sendNotiResponse){
                    model.setStatus(NotificationStatusEnum.SUCCESS.getValue());
                    repository.saveNotification(model);
                } else {
                    // if fail, save to retry
                    handleSendNotiFailed(model);
                }
            }
        } catch (Exception e) {
            log.error("process {} ex", StringUtil.toJsonString(model), e);
        }
    }

    private void handleSendNotiFailed(NotificationModel model) {

        if (notificationConfig.isAllowRetryOnFail()) {
            model.setNumRetries(model.getNumRetries() + 1);
            if (model.getNumRetries() > notificationConfig.getMaxRetries()) {
                log.warn("Send Noti Failed & Max Retried, NotiId = [{}]", model.getNotiId());
                model.setStatus(NotificationStatusEnum.FAILED.getValue());
                repository.saveNotification(model);
            }
            else {
                // retry here
                long retryTime = System.currentTimeMillis() + notificationConfig.getRetryDelaySecond() * 1000;
                model.setRetryTime(retryTime);
                model.setStatus(NotificationStatusEnum.FAILED_RETRYING.getValue());

                log.info("...Send Notify Failed...Waiting For Retry...NotiId = [{}]", model.getNotiId());
                taskScheduler.schedule(
                        new NotificationRetryTask(model, kafka),
                        new Date(retryTime)
                );
                repository.saveNotification(model);
            }
        } else {
            model.setStatus(NotificationStatusEnum.FAILED.getValue());
            repository.saveNotification(model);
        }
    }
}
