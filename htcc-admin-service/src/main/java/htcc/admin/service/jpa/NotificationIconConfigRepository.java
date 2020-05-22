package htcc.admin.service.jpa;

import htcc.common.entity.icon.NotificationIconConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationIconConfigRepository extends JpaRepository<NotificationIconConfig, String> {
}
