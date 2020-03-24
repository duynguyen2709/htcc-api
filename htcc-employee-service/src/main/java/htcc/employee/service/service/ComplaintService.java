package htcc.employee.service.service;

import com.google.gson.reflect.TypeToken;
import htcc.common.constant.ReturnCodeEnum;
import htcc.common.entity.base.BaseResponse;
import htcc.common.entity.complaint.ComplaintModel;
import htcc.common.entity.complaint.ComplaintResponse;
import htcc.common.util.StringUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
public class ComplaintService {

    @Autowired
    private LogService logService;

    public List<ComplaintResponse> getComplaintLog(String companyId, String username, String yyyyMM) {
        List<ComplaintResponse> result = new ArrayList<>();
        try {
            BaseResponse response = logService.getComplaintLog(companyId, username, yyyyMM);
            List<ComplaintModel> models = parseResponse(response);
            if (models == null) {
                throw new Exception(String.format("parseResponse %s return null", response));
            }

            models.forEach(c -> result.add(new ComplaintResponse(c)));

        } catch (Exception e) {
            log.error(String.format("[getComplaintLog] : [%s-%s-%s] ex", companyId, username, yyyyMM), e);
        }

        return result;
    }


    private List<ComplaintModel> parseResponse(BaseResponse res) {
        try {
            if (res == null ||
                    res.getReturnCode() != ReturnCodeEnum.SUCCESS.getValue() ||
                    res.getData() == null) {
                return null;
            }

            String data = StringUtil.toJsonString(res.data);

            return StringUtil.json2Collection(data, new TypeToken<List<ComplaintModel>>(){}.getType());
        } catch (Exception e){
            log.warn("parseResponse {} return null, ex {}", StringUtil.toJsonString(res), e.getMessage());
            return null;
        }
    }
}
