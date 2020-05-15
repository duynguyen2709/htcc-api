package htcc.employee.service.service.shiftarrangement;

import com.google.gson.reflect.TypeToken;
import htcc.common.constant.ReturnCodeEnum;
import htcc.common.constant.ShiftArrangementTypeEnum;
import htcc.common.entity.base.BaseResponse;
import htcc.common.entity.shift.FixedShiftArrangement;
import htcc.common.entity.shift.ShiftArrangementModel;
import htcc.common.entity.shift.ShiftArrangementRequest;
import htcc.common.entity.shift.ShiftTime;
import htcc.common.util.DateTimeUtil;
import htcc.common.util.StringUtil;
import htcc.employee.service.service.LogService;
import htcc.employee.service.service.jpa.FixedShiftArrangementService;
import htcc.employee.service.service.jpa.ShiftTimeService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class ShiftArrangementService {

    @Autowired
    private LogService logService;

    @Autowired
    private ShiftTimeService shiftTimeService;

    @Autowired
    private FixedShiftArrangementService fixedShiftArrangementService;

    public List<ShiftArrangementModel> getShiftArrangementLog(String companyId, int week) {
        return parseResponse(logService.getShiftArrangementLog(companyId, week));
    }

    public BaseResponse deleteShiftArrangement(int type, String arrangementId) {
        BaseResponse response = new BaseResponse(ReturnCodeEnum.SUCCESS);
        response.setReturnMessage("Xóa lịch xếp ca thành công");

        if (type == ShiftArrangementTypeEnum.DAY.getValue()) {
            return logService.deleteShiftArrangement(arrangementId);
        }
        else if (type == ShiftArrangementTypeEnum.FIXED.getValue()) {
            int id = Integer.parseInt(arrangementId);
            FixedShiftArrangement shift = fixedShiftArrangementService.findById(id);
            if (shift == null) {
                response = new BaseResponse(ReturnCodeEnum.DATA_NOT_FOUND);
                response.setReturnMessage("Không tìm thấy dữ liệu ca làm việc");
                return response;
            }

            // delete JPA
            fixedShiftArrangementService.delete(id);

            long now = System.currentTimeMillis();
            String yyyyMMdd = DateTimeUtil.parseTimestampToString(now, "yyyyMMdd");
            int weekDay = DateTimeUtil.getWeekDayInt(yyyyMMdd);

            if (weekDay == shift.getWeekDay()) {
                ShiftArrangementModel model = new ShiftArrangementModel();
                model.setCompanyId(shift.getCompanyId());
                model.setUsername(shift.getUsername());
                model.setOfficeId(shift.getOfficeId());
                model.setShiftId(shift.getShiftId());
                model.setArrangeDate(yyyyMMdd);
                model.setArrangementId(String.format("%s-%s-%s-%s-%s-%s",
                        model.getArrangeDate(), model.getCompanyId(), model.getOfficeId(),
                        model.getShiftId(), model.getUsername(), type));
                return logService.deleteShiftArrangement(model.getArrangementId());

                // TODO : Delete checkin log today
            }
        }

        return response;
    }

    public BaseResponse insertShiftArrangement(ShiftArrangementRequest request) {
        BaseResponse response = new BaseResponse(ReturnCodeEnum.SUCCESS);
        response.setReturnMessage("Xếp ca thành công");

        ShiftTime shiftTime = shiftTimeService.findById(
                new ShiftTime.Key(request.getCompanyId(), request.getOfficeId(), request.getShiftId()));

        if (shiftTime == null) {
            response = new BaseResponse(ReturnCodeEnum.DATA_NOT_FOUND);
            response.setReturnMessage(String.format("Không tìm thấy ca %s", request.getShiftId()));
            return response;
        }

        if (request.getType() == ShiftArrangementTypeEnum.DAY.getValue()) {
            ShiftArrangementModel model = new ShiftArrangementModel(request);
            model.setShiftData(shiftTime);

            return logService.insertShiftArrangement(model);
        }
        else if (request.getType() == ShiftArrangementTypeEnum.FIXED.getValue()) {
            fixedShiftArrangementService.create(new FixedShiftArrangement(request));

            long now = System.currentTimeMillis();
            String yyyyMMdd = DateTimeUtil.parseTimestampToString(now, "yyyyMMdd");
            int weekDay = DateTimeUtil.getWeekDayInt(yyyyMMdd);

            if (weekDay == request.getWeekDay()) {
                ShiftArrangementModel model = new ShiftArrangementModel();
                model.setActionTime(now);
                model.setCompanyId(request.getCompanyId());
                model.setUsername(request.getUsername());
                model.setOfficeId(request.getOfficeId());
                model.setShiftId(request.getShiftId());
                model.setShiftData(shiftTime);
                model.setArrangeDate(yyyyMMdd);
                model.setWeek(DateTimeUtil.getWeekNum(yyyyMMdd));
                model.setFixed(true);
                model.setActor(request.getActor());
                model.setArrangementId(String.format("%s-%s-%s-%s-%s-%s",
                        model.getArrangeDate(), model.getCompanyId(), model.getOfficeId(), model.getShiftId(),
                        model.getUsername(), request.getType()));

                return logService.insertShiftArrangement(model);
            }
        }

        return response;
    }

    private List<ShiftArrangementModel> parseResponse(BaseResponse res) {
        try {
            if (res == null || res.getReturnCode() != ReturnCodeEnum.SUCCESS.getValue() ||
                    res.getData() == null) {
                throw new Exception("Call API Failed");
            }

            String data = StringUtil.toJsonString(res.data);

            return StringUtil.json2Collection(data, new TypeToken<List<ShiftArrangementModel>>(){}.getType());
        } catch (Exception e){
            log.error("parseResponse {} return null, ex {}", StringUtil.toJsonString(res), e.getMessage());
            return null;
        }
    }

    @Async("asyncExecutor")
    public void onEventDeleteShiftTime(ShiftTime shift) {
        try {
            List<FixedShiftArrangement> fixedShiftArrangementList = fixedShiftArrangementService.findByCompanyIdAndOfficeIdAndShiftId(
                    shift.getCompanyId(), shift.getOfficeId(), shift.getShiftId()
            );
            if (fixedShiftArrangementList != null && !fixedShiftArrangementList.isEmpty()) {
                fixedShiftArrangementService.batchDelete(fixedShiftArrangementList);
            }

            BaseResponse response = logService.deleteShiftArrangement(shift);
            if (response == null || response.getReturnCode() != ReturnCodeEnum.SUCCESS.getValue()) {
                log.error("[logService.deleteShiftArrangement] request = {}, response = {}",
                        StringUtil.toJsonString(shift), StringUtil.toJsonString(response));
            }

        } catch (Exception e){
            log.error("[onEventDeleteShiftTime] {} ex", StringUtil.toJsonString(shift), e);
        }
    }
}
