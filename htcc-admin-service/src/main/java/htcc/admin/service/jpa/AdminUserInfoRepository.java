package htcc.admin.service.jpa;

import htcc.common.entity.jpa.AdminUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminUserInfoRepository extends JpaRepository<AdminUser, String> {
}
