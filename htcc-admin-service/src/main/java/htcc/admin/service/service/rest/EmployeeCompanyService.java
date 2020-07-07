package htcc.admin.service.service.rest;

import com.google.gson.reflect.TypeToken;
import htcc.common.constant.Constant;
import htcc.common.constant.ReturnCodeEnum;
import htcc.common.entity.base.BaseResponse;
import htcc.common.entity.jpa.Company;
import htcc.common.util.StringUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Log4j2
@Service
public class EmployeeCompanyService {

    @Autowired
    private RestTemplate restTemplate;

    private static final String baseURL = String.format("http://%s/internal", Constant.HTCC_EMPLOYEE_SERVICE);

    public BaseResponse getListCompany() {
        return callGet("/companies");
    }

    public List<Company> getListCompanyModel() {
        BaseResponse response = getListCompany();
        if (response == null || response.getReturnCode() != ReturnCodeEnum.SUCCESS.getValue()) {
            log.error("[getListCompany] response = " + StringUtil.toJsonString(response));
            return null;
        }

        String rawValue = StringUtil.toJsonString(response.getData());
        return StringUtil.json2Collection(rawValue, new TypeToken<List<Company>>() {}.getType());
    }

    public BaseResponse createCompany(Company model) {
        try {
            HttpEntity<Company> request = new HttpEntity<>(model);
            String method  = "/companies";
            return restTemplate.postForObject(baseURL + method, request, BaseResponse.class);
        } catch (Exception e) {
            log.error(e);
            return new BaseResponse(e);
        }
    }

    public BaseResponse updateCompanyInfo(Company model) {
        try {
            HttpEntity<Company> request = new HttpEntity<>(model);
            String method = "/companies/update";
            return restTemplate.postForObject(baseURL + method, request, BaseResponse.class);
        } catch (Exception e) {
            log.error(e);
            return new BaseResponse(e);
        }
    }

    public BaseResponse updateCompanyStatus(Company model) {
        try {
            HttpEntity<Company> request = new HttpEntity<>(model);
            String method = "/companies/status";
            return restTemplate.postForObject(baseURL + method, request, BaseResponse.class);
        } catch (Exception e) {
            log.error(e);
            return new BaseResponse(e);
        }
    }

    private BaseResponse callGet(String method) {
        return restTemplate.getForObject(baseURL + method, BaseResponse.class);
    }
}
