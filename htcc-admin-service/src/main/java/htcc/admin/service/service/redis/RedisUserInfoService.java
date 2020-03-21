package htcc.admin.service.service.redis;

import htcc.common.entity.jpa.AdminUser;
import htcc.common.component.redis.RedisService;
import htcc.common.constant.ClientSystemEnum;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class RedisUserInfoService {

    @Autowired
    private RedisService redis;

    public void setUserInfo(AdminUser data) {
        redis.set(data, 0,
                redis.buzConfig.userInfoFormat,
                ClientSystemEnum.ADMIN_WEB.getValue(), "", data.username);
    }

    public void deleteUserInfo(String username) {
        redis.delete(redis.buzConfig.userInfoFormat,
                ClientSystemEnum.ADMIN_WEB.getValue(), "", username);
    }
}
