package htcc.admin.service.service.rest;

import htcc.common.constant.Constant;
import htcc.common.entity.base.BaseResponse;
import htcc.common.entity.complaint.UpdateComplaintStatusModel;
import htcc.common.entity.jpa.Company;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Log4j2
@Service
public class EmployeeCompanyService {

    @Autowired
    private RestTemplate restTemplate;

    private static final String baseURL = String.format("http://%s/internal", Constant.HTCC_EMPLOYEE_SERVICE);

    public BaseResponse getListCompany() {
        return callGet("/companies");
    }

    public BaseResponse createCompany(Company model) {
        HttpEntity<Company> request = new HttpEntity<>(model);
        String method = "/companies";
        return restTemplate.postForObject(baseURL + method, request, BaseResponse.class);
    }

    public BaseResponse updateCompanyInfo(Company model) {
        HttpEntity<Company> request = new HttpEntity<>(model);
        String method = "/companies/update";
        return restTemplate.postForObject(baseURL + method, request, BaseResponse.class);
    }


    public BaseResponse updateCompanyStatus(Company model) {
        HttpEntity<Company> request = new HttpEntity<>(model);
        String method = "/companies/status";
        return restTemplate.postForObject(baseURL + method, request, BaseResponse.class);
    }


    private BaseResponse callGet(String method){
        return restTemplate.getForObject(baseURL + method, BaseResponse.class);
    }
}
