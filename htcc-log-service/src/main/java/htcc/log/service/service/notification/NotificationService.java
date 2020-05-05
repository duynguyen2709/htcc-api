package htcc.log.service.service.notification;

import com.google.firebase.messaging.*;
import com.google.gson.reflect.TypeToken;
import htcc.common.component.redis.RedisService;
import htcc.common.constant.Constant;
import htcc.common.entity.notification.NotificationBuz;
import htcc.common.entity.notification.NotificationModel;
import htcc.common.entity.notification.NotificationResponse;
import htcc.common.util.StringUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class NotificationService {

    @Autowired
    private RedisService redis;

    @Autowired
    private TokenPushService tokenPushService;

    public boolean sendNotify(NotificationModel model){
        try {
            List<String> tokens = model.getTokenPush();

            Notification noti = Notification.builder()
                    .setTitle(model.getTitle())
                    .setBody(model.getContent())
                    .build();

            NotificationResponse modelResponse = new NotificationResponse(model);

            MulticastMessage message = MulticastMessage.builder()
                    .setNotification(noti)
                    .putAllData(modelResponse.toMap())
                    .addAllTokens(model.getTokenPush())
                    .build();

            BatchResponse allResponse = FirebaseMessaging.getInstance().sendMulticast(message);

            List<SendResponse> responseList = allResponse.getResponses();
            List<String> oldTokens = new ArrayList<>();
            for (int i = 0; i < responseList.size(); i++) {
                if (responseList.get(i).isSuccessful()) {
                    log.info("Send Noti [{}] Succeed, MessageId = [{}]", model.getNotiId(),
                            responseList.get(i).getMessageId());
                } else {
                    log.error("Send Noti [{}] Failed, Ex = [{}], Error = [{}]",
                            model.getNotiId(), responseList.get(i).getException(),
                            responseList.get(i).getException().getErrorCode());

                    if (responseList.get(i).getException().getErrorCode()
                            .equals(Constant.ERROR_FCM_TOKEN_NOT_REGISTERED)){
                        oldTokens.add(tokens.get(i));
                    }
                }
            }

            if (!oldTokens.isEmpty()) {
                log.info("...Removing Old Tokens For User [{} - {}], Values = {}",
                        model.getCompanyId(), model.getUsername(), StringUtil.toJsonString(oldTokens));

                tokens.removeAll(oldTokens);

                NotificationBuz buz = new NotificationBuz();
                buz.setClientId(model.getClientId());
                buz.setCompanyId(model.getCompanyId());
                buz.setUsername(model.getUsername());
                buz.setTokens(StringUtil.toJsonString(tokens));

                tokenPushService.removeOldTokens(buz);
            }

            return (allResponse.getSuccessCount() > 0);
        } catch (Exception e){
            log.error("[sendNotify] {} ex", StringUtil.toJsonString(model), e);
            return false;
        }
    }

    public void savePendingNotification(NotificationModel model){
        try {
            log.info("savePendingNotification for user: [{} - {}], noti = [{}]",
                    model.getCompanyId(), model.getUsername(), StringUtil.toJsonString(model));

            List<NotificationModel> currentList = getPendingNotification(
                    model.getClientId(), model.getCompanyId(), model.getUsername());

            if (currentList == null){
                currentList = new ArrayList<>();
            }

            currentList.add(model);

            // save to redis for 1 day
            redis.set(StringUtil.toJsonString(currentList),24 * 60 * 60,
                    redis.buzConfig.notificationFormat,
                    model.getClientId(), model.getCompanyId(), model.getUsername());

        } catch (Exception e){
            log.error("[savePendingNotification] {}", StringUtil.toJsonString(model), e);
        }
    }

    public List<NotificationModel> getPendingNotification(int clientId, String companyId, String username){
        try {
            Object objectList = redis.get(redis.buzConfig.notificationFormat,
                    clientId, companyId, username);

            String rawList = StringUtil.valueOf(objectList);
            if (!rawList.isEmpty()) {
                return StringUtil.json2Collection(rawList,
                        new TypeToken<List<NotificationModel>>(){}.getType());
            }
        } catch (Exception e){
            log.error("[getPendingNotification] [{} - {} - {}]", clientId, companyId, username, e);
        }
        return null;
    }

    public void deletePendingNotification(int clientId, String companyId, String username) {
        try {
            redis.delete(redis.buzConfig.notificationFormat, clientId, companyId, username);
        } catch (Exception e){
            log.error("[deletePendingNotification] [{} - {} - {}]", clientId, companyId, username, e);
        }
    }
}
