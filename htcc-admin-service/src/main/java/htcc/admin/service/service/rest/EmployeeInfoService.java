package htcc.admin.service.service.rest;

import htcc.common.constant.Constant;
import htcc.common.entity.base.BaseResponse;
import htcc.common.entity.companyuser.CompanyUserModel;
import htcc.common.entity.jpa.Company;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Log4j2
@Service
public class EmployeeInfoService {

    @Autowired
    private RestTemplate restTemplate;

    private static final String baseURL = String.format("http://%s/internal", Constant.HTCC_EMPLOYEE_SERVICE);

    public BaseResponse createDefaultEmployeeInfo(CompanyUserModel model) {
        try {
            HttpEntity<CompanyUserModel> request = new HttpEntity<>(model);
            String method = "/employeeinfo";
            return restTemplate.postForObject(baseURL + method, request, BaseResponse.class);
        } catch (Exception e) {
            log.error(e);
            return new BaseResponse(e);
        }
    }
}
