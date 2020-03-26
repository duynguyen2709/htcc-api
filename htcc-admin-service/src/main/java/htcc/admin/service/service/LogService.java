package htcc.admin.service.service;

import htcc.common.constant.Constant;
import htcc.common.entity.base.BaseResponse;
import htcc.common.entity.complaint.UpdateComplaintStatusModel;
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


    public BaseResponse getListComplaintLogByMonth(String yyyyMM) {
        int receiverType = 1;
        String method = String.format("/complaint/%s/%s",receiverType, yyyyMM);
        return callGet(method);
    }


    public BaseResponse updateComplaintStatus(UpdateComplaintStatusModel model) {
        HttpEntity<UpdateComplaintStatusModel> request = new HttpEntity<>(model);
        String method = "/complaint/status";
        return restTemplate.postForObject(baseURL + method, request, BaseResponse.class);
    }


    private BaseResponse callGet(String method){
        return restTemplate.getForObject(baseURL + method, BaseResponse.class);
    }
}
