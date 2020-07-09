package htcc.employee.service.service;

import htcc.common.constant.Constant;
import htcc.common.entity.base.BaseResponse;
import htcc.common.entity.checkin.UpdateCheckInStatusModel;
import htcc.common.entity.companyuser.CompanyUserModel;
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
public class GatewayService {

    @Autowired
    private RestTemplate restTemplate;

    private static final String baseURL = String.format("http://%s/internal", Constant.HTCC_GATEWAY_SERVICE);

    /*
    ##################### Company User Section #####################
     */

    public BaseResponse createCompanyUser(CompanyUserModel model) {
        HttpEntity<CompanyUserModel> request = new HttpEntity<>(model);
        String method = "/companyusers";
        return restTemplate.postForObject(baseURL + method, request, BaseResponse.class);
    }

    public BaseResponse updateCompanyUserStatus(CompanyUserModel model) {
        HttpEntity<CompanyUserModel> request = new HttpEntity<>(model);
        String method = "/companyusers/status";
        return restTemplate.postForObject(baseURL + method, request, BaseResponse.class);
    }

    public BaseResponse getCompanyUsers(String companyId) {
        return callGet("/companyusers/" + companyId);
    }

    /*
    ##################### Common #####################
     */
    private BaseResponse callGet(String method){
        return restTemplate.getForObject(baseURL + method, BaseResponse.class);
    }
}
