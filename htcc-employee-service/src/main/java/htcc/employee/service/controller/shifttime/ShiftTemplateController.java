package htcc.employee.service.controller.shifttime;

import com.google.gson.reflect.TypeToken;
import htcc.common.constant.ReturnCodeEnum;
import htcc.common.entity.base.BaseResponse;
import htcc.common.entity.shift.ShiftArrangementTemplate;
import htcc.common.entity.shift.ShiftTemplateRequest;
import htcc.common.entity.shift.ShiftTime;
import htcc.common.util.StringUtil;
import htcc.employee.service.service.jpa.ShiftArrangementTemplateService;
import htcc.employee.service.service.jpa.ShiftTimeService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Log4j2
public class ShiftTemplateController {

    @Autowired
    private ShiftArrangementTemplateService shiftArrangementTemplateService;

    @Autowired
    private ShiftTimeService shiftTimeService;

    @GetMapping("/shifttemplates/{companyId}")
    public BaseResponse getShiftTemplates(@PathVariable String companyId) {
        BaseResponse response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        try {
            List<ShiftArrangementTemplate> dataResponse = shiftArrangementTemplateService.findByCompanyId(companyId);
            dataResponse.forEach(c -> c.shiftTimeList = StringUtil.json2Collection(c.data,
                    new TypeToken<List<ShiftArrangementTemplate.MiniShiftTime>>(){}.getType()));

            response.setData(dataResponse);
        } catch (Exception e) {
            log.error("[getShiftTemplates] [{}] ex", companyId, e);
            response = new BaseResponse<>(e);
        }
        return response;
    }

    @DeleteMapping("/shifttemplates/{templateId}")
    public BaseResponse deleteShiftTemplate(@PathVariable int templateId) {
        BaseResponse response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        response.setReturnMessage("Xóa ca mẫu thành công");
        try {
            if (shiftArrangementTemplateService.findById(templateId) == null) {
                response = new BaseResponse(ReturnCodeEnum.DATA_NOT_FOUND);
                response.setReturnMessage("");
                return response;
            }

            shiftArrangementTemplateService.delete(templateId);
        } catch (Exception e) {
            log.error("[deleteShiftTemplate] [{} - {}] ex", templateId, e);
            response = new BaseResponse<>(e);
        }
        return response;
    }

    @PostMapping("/shifttemplates")
    public BaseResponse insertShiftTemplate(@RequestBody ShiftTemplateRequest request) {
        BaseResponse<ShiftArrangementTemplate> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        response.setReturnMessage("Tạo ca mẫu thành công");
        try {
            String error = request.isValid();
            if (!error.isEmpty()) {
                response = new BaseResponse<>(ReturnCodeEnum.PARAM_DATA_INVALID);
                response.setReturnMessage(error);
                return response;
            }

            ShiftArrangementTemplate entity = new ShiftArrangementTemplate();
            entity.setActor(request.getActor());
            entity.setCompanyId(request.getCompanyId());
            entity.setTemplateName(request.getTemplateName());
            entity.setShiftTimeList(new ArrayList<>());
            for (ShiftTemplateRequest.MiniShiftTime miniShiftTime : request.getShiftTimeList()) {
                ShiftArrangementTemplate.MiniShiftTime detail = new ShiftArrangementTemplate.MiniShiftTime();
                detail.setWeekDay(miniShiftTime.getWeekDay());
                detail.setOfficeId(miniShiftTime.getOfficeId());
                detail.setShiftId(miniShiftTime.getShiftId());

                ShiftTime shiftTime = shiftTimeService.findById(new ShiftTime.Key(request.getCompanyId(),
                        detail.getOfficeId(), detail.getShiftId()));
                if (shiftTime == null) {
                    throw new Exception("shiftTimeService.findById return null");
                }

                detail.setShiftName(shiftTime.getShiftName());
                detail.setStartTime(shiftTime.getStartTime());
                detail.setEndTime(shiftTime.getEndTime());
                entity.getShiftTimeList().add(detail);
            }
            entity.setData(entity.getShiftTimeList());
            entity = shiftArrangementTemplateService.create(entity);

            response.setData(entity);
        } catch (Exception e) {
            log.error("[insertShiftArrangement] {} ex", StringUtil.toJsonString(request), e);
            response = new BaseResponse<>(e);
        }
        return response;
    }
}
