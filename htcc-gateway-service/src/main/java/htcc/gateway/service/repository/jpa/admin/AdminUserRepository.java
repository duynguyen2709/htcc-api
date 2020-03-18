package htcc.gateway.service.repository.jpa.admin;

import htcc.gateway.service.entity.jpa.admin.AdminUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminUserRepository extends JpaRepository<AdminUser, String> {
}
