package htcc.log.service.repository;

import htcc.common.entity.notification.NotificationBuz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationBuzRepository extends JpaRepository<NotificationBuz, NotificationBuz.Key> {
}
