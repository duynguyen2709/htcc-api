package htcc.employee.service.service.redis;

import htcc.common.component.redis.RedisService;
import htcc.common.constant.ClientSystemEnum;
import htcc.common.entity.jpa.EmployeeInfo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class RedisUserInfoService {

    @Autowired
    private RedisService redis;

    public void setUserInfo(EmployeeInfo data) {
        redis.set(data, 0,
                redis.buzConfig.userInfoFormat,
                ClientSystemEnum.MOBILE.getValue(),data.companyId, data.username);
    }
}
