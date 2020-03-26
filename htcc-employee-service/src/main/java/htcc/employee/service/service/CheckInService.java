package htcc.employee.service.service;

import htcc.common.component.kafka.KafkaProducerService;
import htcc.common.constant.ReturnCodeEnum;
import htcc.common.entity.base.BaseResponse;
import htcc.common.entity.checkin.CheckinModel;
import htcc.common.util.DateTimeUtil;
import htcc.common.util.StringUtil;
import htcc.employee.service.service.redis.RedisCheckinService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@Log4j2
public class CheckInService {

    @Autowired
    private LogService logService;

    @Autowired
    private RedisCheckinService redisService;

    @Autowired
    private KafkaProducerService kafka;

    @Async("asyncExecutor")
    public CompletableFuture<CheckinModel> getCheckInLog(String companyId, String username, String yyyyMMdd) {
        String today = DateTimeUtil.parseTimestampToString(System.currentTimeMillis(), "yyyyMMdd");

        CheckinModel result = null;
        if (today.equals(yyyyMMdd)) {
            result = redisService.getCheckInLog(companyId, username, yyyyMMdd);
        } else {
            result = parseResponse(logService.getCheckInLog(companyId, username, yyyyMMdd));
        }

        return CompletableFuture.completedFuture(result);
    }

    public CompletableFuture<CheckinModel> getCheckOutLog(String companyId, String username, String yyyyMMdd) {
        String today = DateTimeUtil.parseTimestampToString(System.currentTimeMillis(), "yyyyMMdd");

        CheckinModel result = null;
        if (today.equals(yyyyMMdd)) {
            result = redisService.getCheckOutLog(companyId, username, yyyyMMdd);
        } else {
            result = parseResponse(logService.getCheckOutLog(companyId, username, yyyyMMdd));
        }

        return CompletableFuture.completedFuture(result);
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

    @Async("asyncExecutor")
    public void deleteCheckInLog(String companyId, String username, String date) {
        redisService.deleteCheckInLog(companyId, username, date);
    }

    private CheckinModel parseResponse(BaseResponse res) {
        try {
            if (res == null || res.getReturnCode() != ReturnCodeEnum.SUCCESS.getValue() ||
            res.getData() == null) {
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
