package htcc.gateway.service.repository.jpa;

import htcc.gateway.service.entity.jpa.AdminUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminUserRepository extends JpaRepository<AdminUser, String> {
}
