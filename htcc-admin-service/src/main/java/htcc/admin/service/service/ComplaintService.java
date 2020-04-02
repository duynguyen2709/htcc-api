package htcc.admin.service.service;

import com.google.gson.reflect.TypeToken;
import htcc.admin.service.service.rest.LogService;
import htcc.common.constant.ReturnCodeEnum;
import htcc.common.entity.base.BaseResponse;
import htcc.common.entity.complaint.ComplaintModel;
import htcc.common.entity.complaint.ComplaintResponse;
import htcc.common.entity.complaint.UpdateComplaintStatusModel;
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

    public List<ComplaintResponse> getListComplaintLogByMonth(String yyyyMM) {
        List<ComplaintResponse> result = new ArrayList<>();
        try {
            BaseResponse response = logService.getListComplaintLogByMonth(yyyyMM);
            List<ComplaintModel> models = parseResponse(response);
            if (models == null) {
                throw new Exception(String.format("parseResponse %s return null", response));
            }

            models.forEach(c -> result.add(new ComplaintResponse(c)));

        } catch (Exception e) {
            log.error(String.format("[getListComplaintLogByMonth] : [%s] ex", yyyyMM), e);
        }

        return result;
    }


    public BaseResponse updateComplaintStatus(UpdateComplaintStatusModel model) {
        return logService.updateComplaintStatus(model);
    }


    private List<ComplaintModel> parseResponse(BaseResponse res) {
        try {
            if (res == null) {
                return null;
            }

            if (res.getReturnCode() != ReturnCodeEnum.SUCCESS.getValue() ||
                    res.getData() == null) {
                log.warn("parseResponse Failed {}", StringUtil.toJsonString(res));
                return new ArrayList<>();
            }

            String data = StringUtil.toJsonString(res.data);

            return StringUtil.json2Collection(data, new TypeToken<List<ComplaintModel>>(){}.getType());
        } catch (Exception e){
            log.warn("parseResponse {} return null, ex {}", StringUtil.toJsonString(res), e.getMessage());
            return null;
        }
    }

}
