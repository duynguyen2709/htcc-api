package htcc.gateway.service.repository.jpa.company;

import htcc.gateway.service.entity.jpa.company.CompanyUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyUserRepository extends JpaRepository<CompanyUser, CompanyUser.Key> {
}
