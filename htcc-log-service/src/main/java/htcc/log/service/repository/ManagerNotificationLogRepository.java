package htcc.log.service.repository;

import htcc.common.entity.notification.NotificationLogEntity;

import java.util.List;

public interface ManagerNotificationLogRepository {

    List<NotificationLogEntity> getListNotificationLog(String companyId, String sender, String yyyyMMdd);
}
