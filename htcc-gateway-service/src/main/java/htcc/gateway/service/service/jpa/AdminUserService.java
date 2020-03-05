package htcc.gateway.service.service.jpa;

import htcc.gateway.service.entity.jpa.AdminUser;
import htcc.gateway.service.repository.jpa.AdminUserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.BaseJPAService;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class AdminUserService extends BaseJPAService<AdminUser, String> {

    @Autowired
    private AdminUserRepository repo;

    @Override
    public List<AdminUser> findAll() {
        return repo.findAll();
    }

    @Override
    public AdminUser findById(String key) {
        Optional<AdminUser> user = repo.findById(key);
        return user.orElse(null);
    }

    @Override
    public AdminUser create(AdminUser adminUser) {
        return repo.save(adminUser);
    }

    @Override
    public AdminUser update(AdminUser adminUser) {
        return repo.save(adminUser);
    }

    @Override
    public void delete(String key) {
        repo.deleteById(key);
    }
}
