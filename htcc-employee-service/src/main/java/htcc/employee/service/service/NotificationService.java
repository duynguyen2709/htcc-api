package htcc.employee.service.service;

import com.google.gson.reflect.TypeToken;
import htcc.common.constant.ReturnCodeEnum;
import htcc.common.entity.base.BaseResponse;
import htcc.common.entity.notification.NotificationModel;
import htcc.common.util.StringUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
public class NotificationService {

    @Autowired
    private LogService logService;

    public List<NotificationModel> getListNotification(String companyId, String username, int startIndex, int size) {
        return parseResponse(logService.getNotificationLog(companyId, username, startIndex, size));
    }

    private List<NotificationModel> parseResponse(BaseResponse res) {
        try {
            if (res == null || res.getReturnCode() != ReturnCodeEnum.SUCCESS.getValue() ||
                    res.getData() == null) {
                throw new Exception("Call API Failed");
            }

            String data = StringUtil.toJsonString(res.data);

            return StringUtil.json2Collection(data, new TypeToken<List<NotificationModel>>(){}.getType());
        } catch (Exception e){
            log.error("parseResponse {} return null, ex {}", StringUtil.toJsonString(res), e.getMessage());
            return new ArrayList<>();
        }
    }
}
