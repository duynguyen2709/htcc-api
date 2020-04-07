package htcc.employee.service.service;

import com.google.gson.reflect.TypeToken;
import htcc.common.constant.ReturnCodeEnum;
import htcc.common.entity.base.BaseResponse;
import htcc.common.entity.leavingrequest.LeavingRequestModel;
import htcc.common.entity.leavingrequest.LeavingRequestResponse;
import htcc.common.util.StringUtil;
import htcc.employee.service.config.ServiceConfig;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
public class LeavingRequestService {

    @Autowired
    private ServiceConfig serviceConfig;

    @Autowired
    private LogService logService;

    public List<String> getCategories(){
        return serviceConfig.leavingRequestCategoryList;
    }

    public List<LeavingRequestResponse> getLeavingRequestLog(String companyId, String username, String year){
        List<LeavingRequestResponse> result = new ArrayList<>();
        try {
            BaseResponse response = logService.getListLeavingRequestLog(companyId, username, year);
            List<LeavingRequestModel> list = parseResponse(response);
            if (list == null){
                throw new Exception("parseResponse return null");
            }

            list.forEach(c -> result.add(new LeavingRequestResponse(c)));
            return result;
        } catch (Exception e){
            log.error("[getLeavingRequestLog] [{} - {} - {}]", companyId, username, year, e);
            return null;
        }
    }

    private List<LeavingRequestModel> parseResponse(BaseResponse res) {
        try {
            if (res == null || res.getReturnCode() != ReturnCodeEnum.SUCCESS.getValue() ||
                    res.getData() == null) {
                throw new Exception("Call API Failed");
            }

            String data = StringUtil.toJsonString(res.data);

            return StringUtil.json2Collection(data, new TypeToken<List<LeavingRequestModel>>(){}.getType());
        } catch (Exception e){
            log.warn("parseResponse {} return null, ex {}", StringUtil.toJsonString(res), e.getMessage());
            return null;
        }
    }
}
