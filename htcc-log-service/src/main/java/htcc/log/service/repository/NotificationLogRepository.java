package htcc.log.service.repository;

import htcc.common.entity.notification.NotificationLogEntity;
import htcc.common.entity.notification.NotificationModel;
import htcc.common.entity.notification.UpdateNotificationReadStatusModel;

import java.util.List;

public interface NotificationLogRepository {

    List<NotificationLogEntity> getListNotification(int clientId, String companyId, String username, int startIndex, int size);

    void updateReadAllNotification(UpdateNotificationReadStatusModel model);

    void updateReadOneNotification(UpdateNotificationReadStatusModel model);

    int countUnreadNotifications(int clientId, String companyId, String username);

    void saveNotification(NotificationModel model);

    void createNewNotification(NotificationModel model);
}
