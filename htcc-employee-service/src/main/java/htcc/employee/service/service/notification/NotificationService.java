package htcc.employee.service.service.notification;

import com.google.gson.reflect.TypeToken;
import htcc.common.component.kafka.KafkaProducerService;
import htcc.common.constant.NotificationReceiverSystemEnum;
import htcc.common.constant.ReturnCodeEnum;
import htcc.common.entity.base.BaseResponse;
import htcc.common.entity.jpa.EmployeeInfo;
import htcc.common.entity.notification.ManagerSendNotificationRequest;
import htcc.common.entity.notification.NotificationModel;
import htcc.common.entity.notification.UpdateNotificationReadStatusModel;
import htcc.common.util.DateTimeUtil;
import htcc.common.util.StringUtil;
import htcc.employee.service.service.LogService;
import htcc.employee.service.service.jpa.EmployeeInfoService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Log4j2
public class NotificationService {

    @Autowired
    private KafkaProducerService kafka;

    @Autowired
    private LogService logService;

    @Autowired
    private EmployeeInfoService employeeInfoService;

    /*
       For Employee to get their personal list notification
    */
    public List<NotificationModel> getListNotification(String companyId, String username, int startIndex, int size) {
        return parseResponse(logService.getNotificationLog(companyId, username, startIndex, size));
    }

    /*
       For Manager to get their notification sent by date
    */
    public List<NotificationModel> getListNotificationForManager(String companyId, String username, String yyyyMMdd) {
        return parseResponse(logService.getNotificationLogForManager(companyId, username, yyyyMMdd));
    }

    @Async
    public void managerSendNotification(ManagerSendNotificationRequest request) {
        List<EmployeeInfo> employeeInfoList = null;

        if (request.getReceiverType() == NotificationReceiverSystemEnum.COMPANY.getValue()) {
            employeeInfoList = employeeInfoService.findByCompanyId(request.getCompanyId());
        }

        if (request.getReceiverType() == NotificationReceiverSystemEnum.OFFICE.getValue()) {
            employeeInfoList = employeeInfoService.findByCompanyIdAndOfficeId(request.getCompanyId(), request.getOfficeId());
        }

        if (request.getReceiverType() == NotificationReceiverSystemEnum.USER.getValue()) {
            EmployeeInfo employee = employeeInfoService.findById(new EmployeeInfo.Key(request.getCompanyId(), request.getUsername()));
            if (employee == null) {
                log.error("employeeInfoService.findById return null, companyId = {}, username = {}",
                        request.getCompanyId(), request.getUsername());
                return;
            }
            employeeInfoList = Collections.singletonList(employee);
        }

        if (employeeInfoList == null || employeeInfoList.isEmpty()) {
            log.warn("!!! employeeList to send notification is empty !!!");
            return;
        }

        long now = System.currentTimeMillis();
        String yyyyMMdd = DateTimeUtil.parseTimestampToString(now, "yyyyMMdd");

        for (EmployeeInfo employee : employeeInfoList) {
            NotificationModel model = new NotificationModel(request);
            model.setOfficeId(employee.getOfficeId());
            model.setUsername(employee.getUsername());
            model.setSendTime(now);
            model.setNotiId(String.format("%s_%s", yyyyMMdd, now));

            sendNotification(model);
        }
    }

    /*
       Common Section
    */
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
            return null;
        }
    }

    @Async
    public void updateNotificationHasReadStatus(UpdateNotificationReadStatusModel model){
        kafka.sendMessage(kafka.getBuzConfig().getEventReadNotification().getTopicName(), model);
    }

    public void sendNotification(NotificationModel model){
        kafka.sendMessage(kafka.getBuzConfig().getEventPushNotification().getTopicName(), model);
    }
}
