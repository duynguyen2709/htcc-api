package htcc.employee.service.service.redis;

import com.google.gson.reflect.TypeToken;
import htcc.common.component.redis.RedisService;
import htcc.common.entity.checkin.CheckinModel;
import htcc.common.util.DateTimeUtil;
import htcc.common.util.StringUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
public class RedisCheckinService {

    @Autowired
    private RedisService redis;

    public void setUsedQrCheckInCode(String qrCodeId) {
        redis.set(qrCodeId, DateTimeUtil.getSecondUntilEndOfDay(),
                redis.buzConfig.qrCodeCheckInFormat, qrCodeId);
    }

    public String getUsedQrCheckInCode(String qrCodeId) {
        return StringUtil.valueOf(redis.get(redis.buzConfig.qrCodeCheckInFormat, qrCodeId));
    }

    public void deleteLastCheckInTime(CheckinModel model) {
        redis.delete(redis.buzConfig.getLastCheckInFormat(),
                model.getCompanyId(), model.getUsername(), model.getDate());
    }

    public void setLastCheckInTime(CheckinModel model) {
        long cacheTime = 31 * 86_400L;
        redis.set(StringUtil.toJsonString(model), cacheTime, redis.buzConfig.getLastCheckInFormat(),
                model.getCompanyId(), model.getUsername(), model.getDate());
    }

    public CheckinModel getLastCheckInTime(String companyId, String username, String date) {
        String raw = StringUtil.valueOf(redis.get(redis.buzConfig.getLastCheckInFormat(), companyId, username, date));
        if (raw.isEmpty()) {
            return null;
        }

        return StringUtil.fromJsonString(raw, CheckinModel.class);
    }

    public void updateLastCheckInTimeOppositeId(CheckinModel model) {

        CheckinModel lastCheckInTime = getLastCheckInTime(model.getCompanyId(), model.getUsername(), model.getDate());
        List<CheckinModel> listCheckInTime = getCheckInLog(model.getCompanyId(), model.getUsername(), model.getDate());

        if (listCheckInTime.isEmpty()) {
            return;
        }

        for (CheckinModel checkinModel : listCheckInTime) {
            if (checkinModel.getCheckInId().equals(lastCheckInTime.getCheckInId())) {
                checkinModel.setOppositeId(model.getCheckInId());
            }
        }

        redis.set(StringUtil.toJsonString(listCheckInTime), DateTimeUtil.getSecondUntilEndOfDay(),
                redis.buzConfig.getCheckinFormat(), model.getCompanyId(), model.getUsername(), model.getDate());

        deleteLastCheckInTime(lastCheckInTime);
    }

    public void setCheckInLog(CheckinModel data) {
        List<CheckinModel> list = getCheckInLog(data.getCompanyId(), data.getUsername(), data.getDate());
        if (list == null){
            list = new ArrayList<>();
        }
        list.add(data);

        redis.set(StringUtil.toJsonString(list), DateTimeUtil.getSecondUntilEndOfDay(),
                redis.buzConfig.checkinFormat,
                data.companyId, data.username, data.date);
    }

    public List<CheckinModel> getCheckInLog(String companyId, String username, String date) {
        String rawList = StringUtil.valueOf(redis.get(redis.buzConfig.checkinFormat,
                companyId, username, date));

        if (rawList.isEmpty()){
            return new ArrayList<>();
        }
        return StringUtil.json2Collection(rawList, new TypeToken<List<CheckinModel>>() {}.getType());
    }

    public void setCheckOutLog(CheckinModel data) {
        List<CheckinModel> list = getCheckOutLog(data.getCompanyId(), data.getUsername(), data.getDate());
        if (list == null){
            list = new ArrayList<>();
        }
        list.add(data);

        redis.set(StringUtil.toJsonString(list), DateTimeUtil.getSecondUntilEndOfDay(),
                redis.buzConfig.checkoutFormat,
                data.companyId, data.username, data.date);
    }

    public List<CheckinModel> getCheckOutLog(String companyId, String username, String date) {
        String rawList = StringUtil.valueOf(redis.get(redis.buzConfig.checkoutFormat,
                companyId, username, date));

        if (rawList.isEmpty()){
            return new ArrayList<>();
        }
        return StringUtil.json2Collection(rawList, new TypeToken<List<CheckinModel>>() {}.getType());
    }
}
