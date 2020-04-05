package htcc.gateway.service.service.redis;

import htcc.common.component.redis.RedisService;
import htcc.common.util.StringUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class RedisUserInfoService {

    @Autowired
    private RedisService redis;

    public Object getUserInfo(String clientId, String companyId, String username) {
        Object result = redis.get(redis.buzConfig.userInfoFormat, clientId, companyId, username);
        log.info("[getUserInfo] [{}-{}-{}] from cache return [{}]", clientId, companyId, username, StringUtil.valueOf(result));
        return result;
    }
}
