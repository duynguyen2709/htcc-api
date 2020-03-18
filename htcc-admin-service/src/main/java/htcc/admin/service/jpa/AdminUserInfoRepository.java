package htcc.admin.service.jpa;

import htcc.admin.service.entity.jpa.AdminUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminUserInfoRepository extends JpaRepository<AdminUser, String> {
}
