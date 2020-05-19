package htcc.log.service.service;

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

    private static final String baseURL = String.format("http://%s/internal", Constant.HTCC_ADMIN_SERVICE);

    public BaseResponse getListIcons() {
        return callGet("/icons");
    }

    private BaseResponse callGet(String method){
        return restTemplate.getForObject(baseURL + method, BaseResponse.class);
    }
}
