package htcc.employee.service.service;

import htcc.common.constant.Constant;
import htcc.common.entity.base.BaseResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Log4j2
@Service
public class LogService {

    @Autowired
    private RestTemplate restTemplate;

    private static final String baseURL = String.format("http://%s/internal/logs", Constant.HTCC_LOG_SERVICE);

    public BaseResponse getComplaintLog(String companyId, String username, String yyyyMM) {
        String method = String.format("/complaint/%s/%s/%s", companyId, username, yyyyMM);
        return restTemplate.getForObject(baseURL + method, BaseResponse.class);
    }

    public BaseResponse getCheckInLog(String companyId, String username, String yyyyMMdd) {
        String method = String.format("/checkin/%s/%s/%s", companyId, username, yyyyMMdd);
        return restTemplate.getForObject(baseURL + method, BaseResponse.class);
    }

    public BaseResponse getCheckOutLog(String companyId, String username, String yyyyMMdd) {
        String method = String.format("/checkout/%s/%s/%s", companyId, username, yyyyMMdd);
        return restTemplate.getForObject(baseURL + method, BaseResponse.class);
    }
}
