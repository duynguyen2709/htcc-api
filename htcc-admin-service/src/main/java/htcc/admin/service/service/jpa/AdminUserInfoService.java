package htcc.admin.service.service.jpa;

import htcc.admin.service.entity.jpa.AdminUserInfo;
import htcc.admin.service.jpa.AdminUserInfoRepository;
import htcc.common.constant.Constant;
import htcc.common.service.BaseJPAService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class AdminUserInfoService extends BaseJPAService<AdminUserInfo, String> {

    @Autowired
    private AdminUserInfoRepository repo;

    @Override
    public List<AdminUserInfo> findAll() {
        return repo.findAll();
    }

    @Override
    public AdminUserInfo findById(String key) {
        Optional<AdminUserInfo> user = repo.findById(key);
        return user.orElse(null);
    }

    @Override
    public AdminUserInfo create(AdminUserInfo adminUser) {
        return repo.save(adminUser);
    }

    @Override
    public AdminUserInfo update(AdminUserInfo adminUser) {
        adminUser.avatar = Constant.USER_DEFAULT_AVATAR;
        return repo.save(adminUser);
    }

    @Override
    public void delete(String key) {
        repo.deleteById(key);
    }
}
