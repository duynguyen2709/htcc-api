package htcc.gateway.service.service.jpa;

import htcc.common.service.BaseJPAService;
import htcc.gateway.service.entity.jpa.company.CompanyUser;
import htcc.gateway.service.repository.jpa.company.CompanyUserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class CompanyUserService extends BaseJPAService<CompanyUser, CompanyUser.Key> {

    @Autowired
    private CompanyUserRepository repo;

    @Override
    public List<CompanyUser> findAll() {
        return repo.findAll();
    }

    public List<CompanyUser> findByCompanyId(String companyId) {
        return repo.findByCompanyId(companyId);
    }

    @Override
    public CompanyUser findById(CompanyUser.Key key) {
        Optional<CompanyUser> companyUser = repo.findById(key);
        return companyUser.orElse(null);
    }

    @Override
    public CompanyUser create(CompanyUser companyUser) {
        return repo.save(companyUser);
    }

    @Override
    public CompanyUser update(CompanyUser companyUser) {
        return repo.save(companyUser);
    }

    @Override
    public void delete(CompanyUser.Key key) {
        repo.deleteById(key);
    }

}
