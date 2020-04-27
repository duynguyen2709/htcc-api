package htcc.log.service.repository;

import htcc.common.entity.notification.NotificationLogEntity;

import java.util.List;

public interface NotificationLogRepository {

    List<NotificationLogEntity> getListNotification(int clientId, String companyId, String username, int startIndex, int size);
}
