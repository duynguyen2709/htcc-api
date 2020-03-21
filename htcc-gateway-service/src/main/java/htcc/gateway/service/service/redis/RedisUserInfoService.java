package htcc.gateway.service.service.redis;

import htcc.common.component.redis.RedisService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class RedisUserInfoService {

    @Autowired
    private RedisService redis;

    public Object getUserInfo(String clientId, String companyId, String username) {
        log.info("[getUserInfo] from cache [{}-{}-{}]", clientId, companyId, username);
        return redis.get(redis.buzConfig.userInfoFormat, clientId, companyId, username);
    }
}
