package htcc.gateway.service.repository.jpa;

import htcc.gateway.service.entity.jpa.AdminUser;
import htcc.gateway.service.entity.jpa.CompanyUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyUserRepository extends JpaRepository<CompanyUser, CompanyUser.Key> {
}
