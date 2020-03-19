package htcc.employee.service.service.redis;

import htcc.common.component.redis.RedisService;
import htcc.common.util.DateTimeUtil;
import htcc.common.util.StringUtil;
import htcc.employee.service.entity.checkin.CheckinModel;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class RedisCheckinService {

    @Autowired
    private RedisService redis;

    public void setCheckInLog(CheckinModel data) {
        redis.set(data, DateTimeUtil.getSecondUntilEndOfDay(),
                redis.buzConfig.checkinFormat,
                data.companyId, data.username, data.date);
    }

    public CheckinModel getCheckInLog(CheckinModel data) {
        return getCheckInLog(data.companyId, data.username, data.date);
    }

    public CheckinModel getCheckInLog(String companyId, String username, String date) {
        return (CheckinModel) redis.get(redis.buzConfig.checkinFormat,
                companyId, username, date);
    }

    public void setCheckOutLog(CheckinModel data) {
        redis.set(data, DateTimeUtil.getSecondUntilEndOfDay(),
                redis.buzConfig.checkoutFormat,
                data.companyId, data.username, data.date);
    }

    public CheckinModel getCheckOutLog(CheckinModel data) {
        return getCheckOutLog(data.companyId, data.username, data.date);
    }

    public CheckinModel getCheckOutLog(String companyId, String username, String date) {
        return (CheckinModel) redis.get(redis.buzConfig.checkoutFormat,
                companyId, username, date);
    }

    public void deleteCheckInLog(String companyId, String username, String date) {
        redis.delete(redis.buzConfig.checkinFormat, companyId, username, date);
        redis.delete(redis.buzConfig.checkoutFormat, companyId, username, date);
    }
}
