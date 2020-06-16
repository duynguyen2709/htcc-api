package htcc.employee.service.service.checkin;

import com.google.gson.reflect.TypeToken;
import htcc.common.component.kafka.KafkaProducerService;
import htcc.common.constant.ReturnCodeEnum;
import htcc.common.entity.base.BaseResponse;
import htcc.common.entity.checkin.CheckinModel;
import htcc.common.util.DateTimeUtil;
import htcc.common.util.StringUtil;
import htcc.employee.service.service.LogService;
import htcc.employee.service.service.redis.RedisCheckinService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public CompletableFuture<List<CheckinModel>> getCheckInLog(String companyId, String username, String yyyyMMdd) {
        String today = DateTimeUtil.parseTimestampToString(System.currentTimeMillis(), "yyyyMMdd");

        List<CheckinModel> result = null;
        if (today.equals(yyyyMMdd)) {
            result = redisService.getCheckInLog(companyId, username, yyyyMMdd);
        } else {
            result = parseResponse(logService.getCheckInLog(companyId, username, yyyyMMdd));
        }

        return CompletableFuture.completedFuture(result);
    }

    @Async("asyncExecutor")
    public CompletableFuture<List<CheckinModel>> getCheckOutLog(String companyId, String username, String yyyyMMdd) {
        String today = DateTimeUtil.parseTimestampToString(System.currentTimeMillis(), "yyyyMMdd");

        List<CheckinModel> result = null;
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

        redisService.setLastCheckInTime(model);

        kafka.sendMessage(kafka.getBuzConfig().checkInLog.topicName, model);
    }

    @Async("asyncExecutor")
    public void setCheckOutLog(CheckinModel model){
        redisService.setCheckOutLog(model);

        redisService.updateLastCheckInTimeOppositeId(model);

        kafka.sendMessage(kafka.getBuzConfig().checkOutLog.topicName, model);
    }

    private List<CheckinModel> parseResponse(BaseResponse res) {
        try {
            if (res == null ||
                    res.getReturnCode() != ReturnCodeEnum.SUCCESS.getValue() ||
                    res.getData() == null) {
                throw new Exception();
            }

            String data = StringUtil.toJsonString(res.data);

            return StringUtil.json2Collection(data, new TypeToken<List<CheckinModel>>() {}.getType());
        } catch (Exception e){
            log.warn("parseResponse {} return null", StringUtil.toJsonString(res));
            return null;
        }
    }

    public void setSucceedQrCheckin(String qrCodeId) {
        redisService.setUsedQrCheckInCode(qrCodeId);
    }

    public boolean isQrCodeUsed(String qrCodeId) {
        return !redisService.getUsedQrCheckInCode(qrCodeId).isEmpty();
    }

    public CheckinModel getLastCheckInTime(String companyId, String username, String date) {
        return redisService.getLastCheckInTime(companyId, username, date);
    }
}
