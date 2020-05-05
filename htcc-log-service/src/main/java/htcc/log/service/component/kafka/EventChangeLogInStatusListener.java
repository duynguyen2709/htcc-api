package htcc.log.service.component.kafka;

import htcc.common.component.kafka.KafkaProducerService;
import htcc.common.entity.notification.NotificationBuz;
import htcc.common.entity.notification.NotificationModel;
import htcc.common.service.kafka.BaseKafkaConsumer;
import htcc.common.util.StringUtil;
import htcc.log.service.repository.NotificationBuzRepository;
import htcc.log.service.service.notification.NotificationService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
@Log4j2
@ConditionalOnProperty(value = "kafka.enableConsumer", havingValue = "true")
@KafkaListener(topics = "#{kafkaFileConfig.buz.eventChangeLogInStatus.topicName}",
               groupId = "#{kafkaFileConfig.buz.eventChangeLogInStatus.groupId}")
public class EventChangeLogInStatusListener extends BaseKafkaConsumer<NotificationBuz> {

    @Autowired
    private NotificationBuzRepository notificationBuzRepository;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private KafkaProducerService kafka;

    @Override
    public void process(NotificationBuz model) {
        try {
            // LogOut event
            if (model.getIsLoggedIn() == 0){
                handleLogOutEvent(model);
            } else if (model.getIsLoggedIn() == 1){
                handleLogInEvent(model);
            }
        } catch (Exception e) {
            log.error("process {} ex", StringUtil.toJsonString(model), e);
        }
    }

    private void handleLogInEvent(NotificationBuz model) throws Exception {
        //
        NotificationBuz oldEntity = notificationBuzRepository.findById(model.getKey())
                .orElse(null);

        if (oldEntity == null){
            // new user logged in
            // create data
            createNewNotificationBuz(model);
            return;
        }

        List<String> oldTokens = StringUtil.json2Collection(oldEntity.getTokens(), StringUtil.LIST_STRING_TYPE);

        boolean isNewToken = true;
        for (String token : oldTokens){
            if (token.equals(model.getTokens())) {
                isNewToken = false;
                break;
            }
        }

        if (isNewToken){
            oldTokens.add(model.getTokens());
        }

        model.setTokens(StringUtil.toJsonString(oldTokens));
        model = notificationBuzRepository.save(model);

        resendPendingNotification(model);
    }

    private void resendPendingNotification(NotificationBuz buz) {
        try {
            List<NotificationModel> pendingNotis = notificationService.getPendingNotification(
                    buz.getClientId(), buz.getCompanyId(), buz.getUsername());

            if (pendingNotis == null || pendingNotis.isEmpty()) {
                return;
            }

            log.info("...Started resendPendingNotification for NotificationBuz: " + StringUtil.toJsonString(buz));
            for (NotificationModel model : pendingNotis) {
                log.info("### Send pending notification: " + StringUtil.toJsonString(model));
                kafka.sendMessage(kafka.getBuzConfig().getEventPushNotification().getTopicName(), model);
                Thread.sleep(1000);
            }
            log.info("End resendPendingNotification for NotificationBuz: " + StringUtil.toJsonString(buz));

        } catch (Exception e) {
            log.error("[resendPendingNotification] {} ex", StringUtil.toJsonString(buz), e);
        } finally {
            notificationService.deletePendingNotification(buz.getClientId(), buz.getCompanyId(), buz.getUsername());
        }
    }

    private void createNewNotificationBuz(NotificationBuz model) throws Exception {
        if (model.getTokens().isEmpty()){
            throw new Exception(String.format("Token Push for user [%s - %s] is empty", model.getCompanyId(), model.getUsername()));
        }
        String token = StringUtil.toJsonString(Collections.singletonList(model.getTokens()));
        model.setTokens(token);
        notificationBuzRepository.save(model);
    }

    private void handleLogOutEvent(NotificationBuz model) throws Exception {
        NotificationBuz oldEntity = notificationBuzRepository.findById(model.getKey())
                .orElse(null);

        if (oldEntity == null){
            throw new Exception("Can not find NotificationBuz: " + StringUtil.toJsonString(model));
        }

        if (oldEntity.getIsLoggedIn() == 0) {
            return;
        }

        List<String> oldTokens = StringUtil.json2Collection(oldEntity.getTokens(), StringUtil.LIST_STRING_TYPE);
        String needToRemove = null;
        for (String token : oldTokens){
            if (token.equals(model.getTokens())) {
                needToRemove = token;
                break;
            }
        }

        if (needToRemove != null) {
            oldTokens.remove(needToRemove);
        }

        model.setTokens(StringUtil.toJsonString(oldTokens));
        notificationBuzRepository.save(model);
    }
}
