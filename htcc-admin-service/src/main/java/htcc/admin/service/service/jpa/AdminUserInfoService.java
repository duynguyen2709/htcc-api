package htcc.admin.service.service.jpa;

import htcc.common.entity.jpa.AdminUser;
import htcc.admin.service.jpa.AdminUserInfoRepository;
import htcc.common.constant.Constant;
import htcc.common.service.BaseJPAService;
import htcc.common.util.StringUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class AdminUserInfoService extends BaseJPAService<AdminUser, String> {

    @Autowired
    private AdminUserInfoRepository repo;

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
        if (StringUtil.valueOf(adminUser.avatar).isEmpty()) {
            adminUser.avatar = Constant.USER_DEFAULT_AVATAR;
        }
        return repo.save(adminUser);
    }

    @Override
    public void delete(String key) {
        repo.deleteById(key);
    }
}
