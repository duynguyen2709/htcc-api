package htcc.employee.service.service.checkin;

import htcc.common.constant.*;
import htcc.common.entity.base.BaseResponse;
import htcc.common.entity.checkin.CheckinModel;
import htcc.common.entity.jpa.EmployeeInfo;
import htcc.common.entity.jpa.Office;
import htcc.common.entity.workingday.WorkingDay;
import htcc.common.util.DateTimeUtil;
import htcc.common.util.LocationUtil;
import htcc.common.util.StringUtil;
import htcc.employee.service.config.DbStaticConfigMap;
import htcc.employee.service.service.jpa.EmployeeInfoService;
import htcc.employee.service.service.jpa.WorkingDayService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@Log4j2
public class CheckInBuzService {

    @Autowired
    private CheckInService checkInService;

    @Autowired
    private EmployeeInfoService employeeService;

    @Autowired
    private WorkingDayService workingDayService;

    public void onCheckInSuccess(CheckinModel model) {
        if (model.type == CheckinTypeEnum.CHECKIN.getValue()) {
            checkInService.setCheckInLog(model);
        } else if (model.type == CheckinTypeEnum.CHECKOUT.getValue()) {
            checkInService.setCheckOutLog(model);
        }

        if (model.getSubType() == CheckinSubTypeEnum.QR_CODE.getValue()) {
            checkInService.setSucceedQrCheckin(model.getQrCodeId());
        }
    }

    public BaseResponse doCheckInBuz(CheckinModel model) throws Exception {
        BaseResponse response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);

        String error = model.isValid();
        if (!error.isEmpty()) {
            response = new BaseResponse<>(ReturnCodeEnum.PARAM_DATA_INVALID);
            response.setReturnMessage(error);
            return response;
        }

        error = validateCheckinModel(model);
        if (!error.isEmpty()) {
            response = new BaseResponse<>(ReturnCodeEnum.PARAM_DATA_INVALID);
            response.setReturnMessage(error);
            return response;
        }


        response = validateListCheckInAndCheckOut(model);
        if (response.getReturnCode() != ReturnCodeEnum.SUCCESS.getValue()){
            return response;
        }

        // get valid checkin time by office
        setValidTimeAndLocation(model);

        //TODO: Set dayCount & allowDiffTime from ShiftTime

        return response;
    }

    private BaseResponse validateListCheckInAndCheckOut(CheckinModel model) throws Exception {
        BaseResponse response = new BaseResponse(ReturnCodeEnum.SUCCESS);
        CompletableFuture<List<CheckinModel>> checkinData  = checkInService.getCheckInLog(model.companyId, model.username, model.date);
        CompletableFuture<List<CheckinModel>> checkoutData = checkInService.getCheckOutLog(model.companyId, model.username, model.date);
        CompletableFuture.allOf(checkinData, checkoutData).join();

        // for QrCode specific
        if (model.getSubType() == CheckinSubTypeEnum.QR_CODE.getValue()) {
            int type = 0;
            if (checkinData.get() == null || checkinData.get().isEmpty()
                    || checkinData.get().size() == checkoutData.get().size()) {
                type = 1;
            } else {
                type = 2;
            }

            model.setType(type);
        }

        if (model.type == CheckinTypeEnum.CHECKIN.getValue()) {
            if (checkinData.get().size() > checkoutData.get().size()){
                response = new BaseResponse<>(ReturnCodeEnum.NOT_CHECKOUT);
                return response;
            }

        } else if (model.type == CheckinTypeEnum.CHECKOUT.getValue()) {
            if (checkinData.get() == null || checkinData.get().isEmpty()
                    || checkinData.get().size() == checkoutData.get().size()) {
                response = new BaseResponse<>(ReturnCodeEnum.NOT_CHECKIN);
                return response;
            }

            if (model.clientTime <= checkinData.get().get(checkinData.get().size() - 1).getClientTime()) {
                response = new BaseResponse<>(ReturnCodeEnum.CHECKIN_TIME_NOT_VALID);
                return response;
            }
        }
        return response;
    }

    /** Description : get valid checkin time & location base on office & shift time
     * @param model
     */
    private void setValidTimeAndLocation(CheckinModel model) throws Exception {
        Office office = DbStaticConfigMap.OFFICE_MAP.get(model.getCompanyId() + "_" + model.getOfficeId());
        if (office == null) {
            throw new Exception("Office is null, model = " + StringUtil.toJsonString(model));
        }
        model.setOfficeId(String.format("%s - %s", office.getOfficeId(), office.getOfficeName()));
        model.setValidLatitude(office.getLatitude());
        model.setValidLongitude(office.getLongitude());
        model.setMaxAllowDistance(office.getMaxAllowDistance());

        // TODO : remove hard code time here,
        // GET from ShiftTime
        if (model.getType() == CheckinTypeEnum.CHECKIN.getValue()) {
            model.setValidTime("08:30");
            model.setOnTime(DateTimeUtil.isBefore(model.getClientTime() - 2 * 60 * 1000, model.getValidTime()));
        } else if (model.getType() == CheckinTypeEnum.CHECKOUT.getValue()) {
            model.setValidTime("17:30");
            model.setOnTime(DateTimeUtil.isAfter(model.getClientTime() + 2 * 60 * 1000, model.getValidTime()));
        }
    }

    private String validateCheckinModel(CheckinModel request) {
        EmployeeInfo employee = employeeService.findById(new EmployeeInfo.Key(request.getCompanyId(), request.getUsername()));
        if (employee == null) {
            return String.format("Không tìm thấy nhân viên %s", request.getUsername());
        }

        Office officeInfo = DbStaticConfigMap.OFFICE_MAP.get(request.getCompanyId() + "_" + request.getOfficeId());
        if (officeInfo == null) {
            return String.format("Không tìm thấy chi nhánh %s", request.getOfficeId());
        }

        CheckinSubTypeEnum subType = CheckinSubTypeEnum.fromInt(request.getSubType());
        switch (subType){
            case LOCATION:
                double distance = LocationUtil.calculateDistance(request.getLatitude(), request.getLongitude(),
                        officeInfo.getLatitude(), officeInfo.getLongitude());

                // extra 1m for delta
                if (distance > (officeInfo.getMaxAllowDistance() + 1)) {
                    return "Khoảng cách điểm danh quá xa. Vui lòng thực hiện lại";
                }
                break;
            case IMAGE:
                break;
            case QR_CODE:
                String today = DateTimeUtil.parseTimestampToString(System.currentTimeMillis(), "yyyyMMdd");
                if (!request.getDate().equals(today)) {
                    return "Thời gian điểm danh không hợp lệ. Vui lòng thực hiện lại";
                }

                // add extra 5 second for request network time from client to server
                if (request.getServerTime() - request.getClientTime() > 5 * 60 * 1000 + 5 * 1000) {
                    return "Mã QR đã hết hạn. Vui lòng thực hiện lại";
                }

                if (checkInService.isQrCodeUsed(request.getQrCodeId())) {
                    log.error("\n### QrCodeId [{}] had been used", request.getQrCodeId());
                    return "Mã QR đã được sử dụng. Vui lòng thực hiện lại";
                }

                break;
            case FORM:
                break;
            default:
                break;
        }
        // TODO : Check ShiftTime

        if (subType != CheckinSubTypeEnum.FORM) {
            if (isOffThisSession(request)) {
                return String.format("Hôm nay là ngày nghỉ của chi nhánh %s. Vui lòng thử lại sau.", request.getOfficeId());
            }
        }

        return StringUtil.EMPTY;
    }

    private boolean isOffThisSession(CheckinModel request) {
        int session = 0;
        LocalDateTime midDay = LocalDate.now().atTime(12, 0, 0, 0);
        long time = Timestamp.valueOf(midDay).getTime();
        String yyyyMMdd = DateTimeUtil.parseTimestampToString(request.getClientTime(), "yyyyMMdd");

        if (request.getClientTime() <= time) {
            session = SessionEnum.MORNING.getValue();
        } else {
            session = SessionEnum.AFTERNOON.getValue();
        }

        List<WorkingDay> workingDays = workingDayService.findByCompanyIdAndOfficeId(request.getCompanyId(), request.getOfficeId());

        for (WorkingDay day : workingDays) {
            if (day.getSession() == session && !day.getIsWorking()){
                if (day.getType() == WorkingDayTypeEnum.SPECIAL.getValue()){
                    if (day.getDate().equals(yyyyMMdd)) {
                        return true;
                    }
                }
                else {
                    int weekDay = DateTimeUtil.getWeekDayInt(yyyyMMdd);
                    if (day.getWeekDay() == weekDay){
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
