package htcc.employee.service.service;

import htcc.common.util.DateTimeUtil;
import htcc.employee.service.entity.checkin.CheckinModel;
import htcc.employee.service.repository.ICheckInLogService;
import htcc.employee.service.service.redis.RedisCheckinService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class CheckInService {

    @Autowired
    private ICheckInLogService logService;

    @Autowired
    private RedisCheckinService redisService;


    public CheckinModel getCheckInLog(String companyId, String username, String yyyyMMdd) {
        String today = DateTimeUtil.parseTimestampToString(System.currentTimeMillis(), "yyyyMMdd");

        if (today.equals(yyyyMMdd)) {
            return redisService.getCheckInLog(companyId, username, yyyyMMdd);
        } else {
            return logService.getCheckInLog(companyId, username, yyyyMMdd);
        }
    }

    public CheckinModel getCheckOutLog(String companyId, String username, String yyyyMMdd) {
        String today = DateTimeUtil.parseTimestampToString(System.currentTimeMillis(), "yyyyMMdd");

        if (today.equals(yyyyMMdd)) {
            return redisService.getCheckOutLog(companyId, username, yyyyMMdd);
        } else {
            return logService.getCheckOutLog(companyId, username, yyyyMMdd);
        }
    }

    public CheckinModel getCheckInLog(CheckinModel model){
        return getCheckInLog(model.companyId, model.username, model.date);
    }

    public CheckinModel getCheckOutLog(CheckinModel model){
        return getCheckOutLog(model.companyId, model.username, model.date);
    }

    public void setCheckInLog(CheckinModel model){
        redisService.setCheckInLog(model);
    }

    public void setCheckOutLog(CheckinModel model){
        redisService.setCheckOutLog(model);
    }

    public void deleteCheckInLog(String companyId, String username, String date) {
        redisService.deleteCheckInLog(companyId, username, date);
    }
}
