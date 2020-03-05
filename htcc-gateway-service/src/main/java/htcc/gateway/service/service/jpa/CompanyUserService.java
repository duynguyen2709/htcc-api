package htcc.gateway.service.service.jpa;

import htcc.gateway.service.entity.jpa.AdminUser;
import htcc.gateway.service.entity.jpa.CompanyUser;
import htcc.gateway.service.repository.jpa.AdminUserRepository;
import htcc.gateway.service.repository.jpa.CompanyUserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.BaseJPAService;

import java.util.List;

@Service
@Log4j2
public class CompanyUserService extends BaseJPAService<CompanyUser, CompanyUser.Key> {

    @Autowired
    private CompanyUserRepository repo;

    @Override
    public List<CompanyUser> findAll() {
        return repo.findAll();
    }

    @Override
    public CompanyUser findById(CompanyUser.Key key) {
        return repo.findById(key).get();
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
