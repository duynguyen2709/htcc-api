package htcc.admin.service.service.redis;

import htcc.common.component.redis.RedisService;
import htcc.common.constant.ClientSystemEnum;
import htcc.common.util.DateTimeUtil;
import htcc.common.util.StringUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RedisTokenService {

    @Autowired
    private RedisService redis;

    public void setBlacklistToken(String username){
        Object token = redis.get(redis.buzConfig.tokenFormat,
                ClientSystemEnum.ADMIN_WEB.getValue(), "", username);

        if (token != null) {
            redis.set(StringUtil.valueOf(token), redis.buzConfig.jwtTTL,
                    redis.buzConfig.blacklistTokenFormat,
                    ClientSystemEnum.ADMIN_WEB.getValue(), "", username);
        }
    }

    public void deleteBlacklistToken(String username){
        redis.delete(redis.buzConfig.blacklistTokenFormat,
                ClientSystemEnum.ADMIN_WEB.getValue(), "", username);
    }

    public void deleteToken(String username) {
        redis.delete(redis.buzConfig.tokenFormat, ClientSystemEnum.ADMIN_WEB.getValue(), "", username);
    }
}
