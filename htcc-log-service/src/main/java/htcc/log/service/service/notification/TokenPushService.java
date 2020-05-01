package htcc.log.service.service.notification;

import htcc.common.entity.notification.NotificationBuz;
import htcc.common.util.StringUtil;
import htcc.log.service.repository.NotificationBuzRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class TokenPushService {

    @Autowired
    private NotificationBuzRepository buzRepository;

    @Async("asyncExecutor")
    public void removeOldTokens(NotificationBuz buz){
        try {
            NotificationBuz oldBuz = buzRepository.findById(buz.getKey()).orElse(null);
            if (oldBuz == null){
                throw new Exception("NotificationBuzRepository findById return null");
            }

            oldBuz.setTokens(buz.getTokens());
            buzRepository.save(oldBuz);
        } catch (Exception e){
            log.error("[removeOldTokens] [{}] ex", StringUtil.toJsonString(buz));
        }
    }
}
