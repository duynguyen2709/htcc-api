package htcc.admin.service.jpa;

import htcc.admin.service.entity.jpa.AdminUserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminUserInfoRepository extends JpaRepository<AdminUserInfo, String> {
}
