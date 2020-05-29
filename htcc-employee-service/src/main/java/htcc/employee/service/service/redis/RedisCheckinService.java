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

    // TODO : Delete this method after testing
    public void deleteCheckInLog(String companyId, String username, String date) {
        redis.delete(redis.buzConfig.checkinFormat, companyId, username, date);
        redis.delete(redis.buzConfig.checkoutFormat, companyId, username, date);
    }
}
