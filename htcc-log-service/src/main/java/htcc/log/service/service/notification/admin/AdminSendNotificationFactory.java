package htcc.log.service.service.notification.admin;

import htcc.common.constant.NotificationReceiverSystemEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AdminSendNotificationFactory {

    @Autowired
    private AdminSendToAllNotificationService allNotificationService;

    @Autowired
    private AdminSendToCompanyNotificationService companyNotificationService;

    @Autowired
    private AdminSendToUserNotificationService userNotificationService;

    public AdminSendNotificationService getSubService(int receiverType) {
        NotificationReceiverSystemEnum system = NotificationReceiverSystemEnum.fromInt(receiverType);
        switch (system) {
            case ALL:
                return allNotificationService;
            case COMPANY:
                return companyNotificationService;
            case USER:
                return userNotificationService;
            default:
                return null;
        }
    }
}
