package htcc.employee.service.service;

import htcc.common.constant.ClientSystemEnum;
import htcc.common.constant.Constant;
import htcc.common.entity.base.BaseResponse;
import htcc.common.entity.complaint.ResubmitComplaintModel;
import htcc.common.entity.complaint.UpdateComplaintStatusModel;
import htcc.common.entity.leavingrequest.UpdateLeavingRequestStatusModel;
import htcc.common.entity.shift.ShiftArrangementModel;
import htcc.common.entity.shift.ShiftTime;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Log4j2
@Service
public class LogService {

    @Autowired
    private RestTemplate restTemplate;

    private static final String baseURL = String.format("http://%s/internal/logs", Constant.HTCC_LOG_SERVICE);

    /*
    ##################### Complaint Section #####################
     */

    // for employee
    public BaseResponse getComplaintLog(String companyId, String username, String yyyyMM) {
        String method = String.format("/complaint/%s/%s/%s", companyId, username, yyyyMM);
        return callGet(method);
    }

    //for manager
    public BaseResponse getListComplaintLogByCompany(String companyId, String yyyyMM) {
        int receiverType = 2;
        String method = String.format("/complaint/%s/%s?companyId=%s",receiverType, yyyyMM, companyId);
        return callGet(method);
    }

    public BaseResponse countPendingComplaintLogByCompany(String companyId) {
        int receiverType = 2;
        String method = String.format("/complaint/count/%s?companyId=%s",receiverType, companyId);
        return callGet(method);
    }

    public BaseResponse updateComplaintStatus(UpdateComplaintStatusModel model) {
        HttpEntity<UpdateComplaintStatusModel> request = new HttpEntity<>(model);
        String method = "/complaint/status";
        return restTemplate.postForObject(baseURL + method, request, BaseResponse.class);
    }

    public BaseResponse resubmitComplaint(ResubmitComplaintModel model) {
        HttpEntity<ResubmitComplaintModel> request = new HttpEntity<>(model);
        String method = "/complaint/resubmit";
        return restTemplate.postForObject(baseURL + method, request, BaseResponse.class);
    }

    /*
    ##################### Leaving Request Section #####################
     */

    // for employee
    public BaseResponse getOneLeavingRequestLog(String leavingRequestId, String yyyyMM) {
        String method = String.format("/leaving?leavingRequestId=%s&yyyyMM=%s", leavingRequestId, yyyyMM);
        return callGet(method);
    }

    public BaseResponse getListLeavingRequestLog(String companyId, String username, String year) {
        String method = String.format("/leaving/%s/%s/%s", companyId, username, year);
        return callGet(method);
    }

    //for manager
    public BaseResponse getListLeavingRequestLogByCompany(String companyId, String yyyyMM) {
        String method = String.format("/leaving/%s/%s",companyId, yyyyMM);
        return callGet(method);
    }


    public BaseResponse countPendingLeavingRequest(String companyId) {
        String method = String.format("/leaving/count/%s", companyId);
        return callGet(method);
    }

    public BaseResponse updateLeavingRequestStatus(UpdateLeavingRequestStatusModel model) {
        HttpEntity<UpdateLeavingRequestStatusModel> request = new HttpEntity<>(model);
        String method = "/leaving/status";
        return restTemplate.postForObject(baseURL + method, request, BaseResponse.class);
    }

    /*
    ##################### CheckIn Section #####################
     */
    public BaseResponse getCheckInLog(String companyId, String username, String yyyyMMdd) {
        String method = String.format("/checkin/%s/%s/%s", companyId, username, yyyyMMdd);
        return callGet(method);
    }

    public BaseResponse getCheckOutLog(String companyId, String username, String yyyyMMdd) {
        String method = String.format("/checkout/%s/%s/%s", companyId, username, yyyyMMdd);
        return callGet(method);
    }

    /*
    ##################### Notification Section #####################
     */
    public BaseResponse getNotificationLog(String companyId, String username, int startIndex, int size) {
        String method = String.format("/notifications?clientId=%s&companyId=%s&username=%s&startIndex=%s&size=%s",
                ClientSystemEnum.MOBILE.getValue(), companyId, username, startIndex, size);
        return callGet(method);
    }

    public BaseResponse countUnreadNotificationForMobile(String companyId, String username) {
        String method = String.format("/notifications/count?clientId=%s&companyId=%s&username=%s",
                ClientSystemEnum.MOBILE.getValue(), companyId, username);
        return callGet(method);
    }

    public BaseResponse getNotificationLogForManager(String companyId, String username, String yyyyMMdd) {
        String method = String.format("/notifications/manager?companyId=%s&sender=%s&yyyyMMdd=%s",
                companyId, username, yyyyMMdd);
        return callGet(method);
    }

    /*
    ##################### Shift Arrangement Section #####################
     */
    public BaseResponse getShiftArrangementLog(String companyId, int week) {
        String method = String.format("/shifts/%s/%s", companyId, week);
        return callGet(method);
    }

    public BaseResponse deleteShiftArrangement(String arrangementId) {
        try {
            HttpEntity<Object> request = new HttpEntity<>(null);
            String method = String.format("/shifts/delete/%s", arrangementId);
            return restTemplate.postForObject(baseURL + method, request, BaseResponse.class);
        } catch (Exception e) {
            log.error(e);
            return new BaseResponse(e);
        }
    }

    public BaseResponse deleteShiftArrangement(ShiftTime entity) {
        try {
            HttpEntity<ShiftTime> request = new HttpEntity<>(entity);
            String method = "/shifts/delete";
            return restTemplate.postForObject(baseURL + method, request, BaseResponse.class);
        } catch (Exception e) {
            log.error(e);
            return new BaseResponse(e);
        }
    }

    public BaseResponse insertShiftArrangement(ShiftArrangementModel entity) {
        try {
            HttpEntity<ShiftArrangementModel> request = new HttpEntity<>(entity);
            String method = "/shifts";
            return restTemplate.postForObject(baseURL + method, request, BaseResponse.class);
        } catch (Exception e) {
            log.error(e);
            return new BaseResponse(e);
        }
    }

    /*
    ##################### Common #####################
     */
    private BaseResponse callGet(String method){
        return restTemplate.getForObject(baseURL + method, BaseResponse.class);
    }
}
