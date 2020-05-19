package htcc.log.service.service.notification.admin;

import htcc.common.entity.notification.AdminSendNotificationRequest;

public interface AdminSendNotificationService {

    void send(AdminSendNotificationRequest request);
}
