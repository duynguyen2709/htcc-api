package htcc.admin.service.service.rest;

import htcc.common.constant.Constant;
import htcc.common.entity.base.BaseResponse;
import htcc.common.entity.companyuser.CompanyUserModel;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Log4j2
public class GatewayCompanyUserService {

    @Autowired
    private RestTemplate restTemplate;

    private static final String baseURL = String.format("http://%s/internal", Constant.HTCC_GATEWAY_SERVICE);

    public BaseResponse getListCompanyUser(String companyId) {
        return callGet("/companyusers/" + companyId);
    }

    public BaseResponse createCompanyUser(CompanyUserModel model) {
        try {
            HttpEntity<CompanyUserModel> request = new HttpEntity<>(model);
            String method = "/companyusers";
            return restTemplate.postForObject(baseURL + method, request, BaseResponse.class);
        } catch (Exception e) {
            log.error(e);
            return new BaseResponse(e);
        }
    }

    public BaseResponse deleteCompanyUser(CompanyUserModel model) {
        try {
            HttpEntity<CompanyUserModel> request = new HttpEntity<>(model);
            String method = "/companyusers/delete";
            return restTemplate.postForObject(baseURL + method, request, BaseResponse.class);
        } catch (Exception e) {
            log.error(e);
            return new BaseResponse(e);
        }
    }

    public BaseResponse updateCompanyUserInfo(CompanyUserModel model) {
        try{
            HttpEntity<CompanyUserModel> request = new HttpEntity<>(model);
            String method = "/companyusers/update";
            return restTemplate.postForObject(baseURL + method, request, BaseResponse.class);
        } catch (Exception e) {
            log.error(e);
            return new BaseResponse(e);
        }
    }


    public BaseResponse updateCompanyUserStatus(CompanyUserModel model) {
        try {
            HttpEntity<CompanyUserModel> request = new HttpEntity<>(model);
            String method = "/companyusers/status";
            return restTemplate.postForObject(baseURL + method, request, BaseResponse.class);
        } catch (Exception e) {
            log.error(e);
            return new BaseResponse(e);
        }
    }

    public BaseResponse blockAllCompanyUser(String companyId, int newStatus) {
        try {
            HttpEntity<Object> request = new HttpEntity<>(null);
            String method = String.format("/companyusers/status/%s/%s", companyId, newStatus);
            return restTemplate.postForObject(baseURL + method, request, BaseResponse.class);
        } catch (Exception e) {
            log.error(e);
            return new BaseResponse(e);
        }
    }


    private BaseResponse callGet(String method){
        return restTemplate.getForObject(baseURL + method, BaseResponse.class);
    }
}
