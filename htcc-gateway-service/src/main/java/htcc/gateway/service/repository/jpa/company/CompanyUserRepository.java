package htcc.gateway.service.repository.jpa.company;

import htcc.gateway.service.entity.jpa.company.CompanyUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompanyUserRepository extends JpaRepository<CompanyUser, CompanyUser.Key> {

    List<CompanyUser> findByCompanyId(String companyId);
}
