package htcc.log.service.repository;

import htcc.common.entity.notification.NotificationLogEntity;

import java.util.List;

public interface AdminNotificationLogRepository {

    List<NotificationLogEntity> getListNotificationLog(String yyyyMMdd, String sender);
}
