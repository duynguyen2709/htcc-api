package htcc.employee.service.service;

import htcc.common.component.kafka.KafkaProducerService;
import htcc.common.entity.base.BaseResponse;
import htcc.common.util.DateTimeUtil;
import htcc.common.util.StringUtil;
import htcc.common.entity.checkin.CheckinModel;
import htcc.employee.service.repository.feign.LogServiceClient;
import htcc.employee.service.service.redis.RedisCheckinService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class CheckInService {

    @Autowired
    private LogServiceClient logService;

    @Autowired
    private RedisCheckinService redisService;

    @Autowired
    private KafkaProducerService kafka;

    public CheckinModel getCheckInLog(String companyId, String username, String yyyyMMdd) {
        String today = DateTimeUtil.parseTimestampToString(System.currentTimeMillis(), "yyyyMMdd");

        if (today.equals(yyyyMMdd)) {
            return redisService.getCheckInLog(companyId, username, yyyyMMdd);
        } else {
            return parseResponse(logService.getCheckInLog(companyId, username, yyyyMMdd));
        }
    }

    public CheckinModel getCheckOutLog(String companyId, String username, String yyyyMMdd) {
        String today = DateTimeUtil.parseTimestampToString(System.currentTimeMillis(), "yyyyMMdd");

        if (today.equals(yyyyMMdd)) {
            return redisService.getCheckOutLog(companyId, username, yyyyMMdd);
        } else {
            return parseResponse(logService.getCheckOutLog(companyId, username, yyyyMMdd));
        }
    }

    public CheckinModel getCheckInLog(CheckinModel model){
        return getCheckInLog(model.companyId, model.username, model.date);
    }

    public CheckinModel getCheckOutLog(CheckinModel model){
        return getCheckOutLog(model.companyId, model.username, model.date);
    }

    @Async("asyncExecutor")
    public void setCheckInLog(CheckinModel model){
        redisService.setCheckInLog(model);

        // TODO: Send Kafka Log here
        log.info("Send Kafka Checkin Log: " + StringUtil.toJsonString(model));
        kafka.sendMessage(kafka.getBuzConfig().checkInLog.topicName, model);
    }

    @Async("asyncExecutor")
    public void setCheckOutLog(CheckinModel model){
        redisService.setCheckOutLog(model);

        // TODO : Send Kafka Log Here
        log.info("Send Kafka Checkout Log: " + StringUtil.toJsonString(model));
        kafka.sendMessage(kafka.getBuzConfig().checkOutLog.topicName, model);
    }

    public void deleteCheckInLog(String companyId, String username, String date) {
        redisService.deleteCheckInLog(companyId, username, date);
    }

    private CheckinModel parseResponse(BaseResponse res) {
        try {
            if (res == null) {
                return null;
            }

            String data = StringUtil.toJsonString(res.data);

            return StringUtil.fromJsonString(data, CheckinModel.class);
        } catch (Exception e){
            log.warn("parseResponse {} return null", StringUtil.toJsonString(res));
            return null;
        }
    }
}
