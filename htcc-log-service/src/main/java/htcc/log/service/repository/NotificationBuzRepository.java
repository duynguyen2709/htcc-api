package htcc.log.service.repository;

import htcc.common.entity.notification.NotificationBuz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationBuzRepository extends JpaRepository<NotificationBuz, NotificationBuz.Key> {

    List<NotificationBuz> findByClientIdAndCompanyId(int clientId, String companyId);
}
