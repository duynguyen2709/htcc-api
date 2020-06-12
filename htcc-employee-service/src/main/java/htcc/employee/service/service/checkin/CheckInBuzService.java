package htcc.employee.service.service.checkin;

import htcc.common.constant.*;
import htcc.common.entity.base.BaseResponse;
import htcc.common.entity.checkin.CheckinModel;
import htcc.common.entity.jpa.EmployeeInfo;
import htcc.common.entity.jpa.Office;
import htcc.common.entity.shift.ShiftArrangementModel;
import htcc.common.entity.shift.ShiftTime;
import htcc.common.entity.workingday.WorkingDay;
import htcc.common.util.DateTimeUtil;
import htcc.common.util.LocationUtil;
import htcc.common.util.StringUtil;
import htcc.employee.service.config.DbStaticConfigMap;
import htcc.employee.service.service.jpa.EmployeeInfoService;
import htcc.employee.service.service.jpa.WorkingDayService;
import htcc.employee.service.service.shiftarrangement.ShiftArrangementService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@Log4j2
public class CheckInBuzService {

    @Autowired
    private CheckInService checkInService;

    @Autowired
    private EmployeeInfoService employeeService;

    @Autowired
    private WorkingDayService workingDayService;

    @Autowired
    private ShiftArrangementService shiftArrangementService;

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

        response = validateListCheckInAndCheckOut(model);
        if (response.getReturnCode() != ReturnCodeEnum.SUCCESS.getValue()){
            return response;
        }

        error = validateCheckinModel(model);
        if (!error.isEmpty()) {
            response = new BaseResponse<>(ReturnCodeEnum.PARAM_DATA_INVALID);
            response.setReturnMessage(error);
            return response;
        }

        setValidTimeAndLocation(model);

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

        if (model.getType() == CheckinTypeEnum.CHECKIN.getValue()) {
            model.setValidTime(model.getShiftTime().getStartTime());
            model.setOnTime(DateTimeUtil.isBefore(model.getClientTime() - (model.getShiftTime().getAllowLateMinutes() + 2) * 60 * 1000, model.getValidTime()));
        } else if (model.getType() == CheckinTypeEnum.CHECKOUT.getValue()) {
            model.setValidTime(model.getShiftTime().getEndTime());
            model.setOnTime(DateTimeUtil.isAfter(model.getClientTime() + (model.getShiftTime().getAllowLateMinutes() + 2) * 60 * 1000, model.getValidTime()));
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

        if (subType != CheckinSubTypeEnum.FORM) {
            if (isOffThisSession(request)) {
                return String.format("Hôm nay là ngày nghỉ của chi nhánh %s. Vui lòng thử lại sau.", request.getOfficeId());
            }
        }

        String validShift = validateShiftTime(request);
        if (!validShift.isEmpty()) {
            return validShift;
        }

        return StringUtil.EMPTY;
    }

    private String validateShiftTime(CheckinModel model) {
        if (model.getType() == CheckinTypeEnum.CHECKIN.getValue()) {
            List<ShiftArrangementModel> shiftArrangementList = shiftArrangementService.getShiftArrangementListByEmployee(model.getCompanyId(), model.getUsername(), model.getDate());

            if (shiftArrangementList == null || shiftArrangementList.isEmpty()) {
                return "Chưa có ca làm việc hôm nay. Vui lòng liên hệ quản lý để xếp ca";
            }

            List<ShiftArrangementModel> fixedShiftList = shiftArrangementList.stream()
                    .filter(c -> c.isFixed)
                    .collect(Collectors.toList());

            List<ShiftArrangementModel> shiftByDateList = shiftArrangementList.stream()
                    .filter(c -> !c.isFixed)
                    .collect(Collectors.toList());

            if (shiftByDateList.isEmpty()) {
                ShiftTime shiftTime = findNearestShift(fixedShiftList, model);
                model.setShiftTime(shiftTime);
            }
            else {
                ShiftTime shiftTime = findNearestShift(shiftByDateList, model);
                model.setShiftTime(shiftTime);
            }
        }
        else {
            CheckinModel lastCheckinModel = checkInService.getLastCheckInTime(model.getCompanyId(), model.getUsername());
            if (lastCheckinModel == null) {
                return "Không tìm thấy dữ liệu điểm danh vào";
            }

            model.setShiftTime(lastCheckinModel.getShiftTime());
            model.setOppositeModel(lastCheckinModel);
            model.setOppositeId(lastCheckinModel.getCheckInId());
        }

        return StringUtil.EMPTY;
    }

    private ShiftTime findNearestShift(List<ShiftArrangementModel> shiftList, CheckinModel model) {
        ShiftTime shift = shiftList.get(0).getShiftTime();
        long minDistance = model.getClientTime();

        for (ShiftArrangementModel arrangeModel : shiftList) {
            try {
                String time = (model.getType() == CheckinTypeEnum.CHECKIN.getValue()) ?
                        arrangeModel.getShiftTime().getStartTime() : arrangeModel.getShiftTime().getEndTime();

                String fullTimeStr = String.format("%s %s", model.getDate(), time);
                long timeMillis = new SimpleDateFormat("yyyyMMdd HH:mm").parse(fullTimeStr).getTime();

                long newDistance = Math.abs(model.getClientTime() - timeMillis);
                if (newDistance < minDistance) {
                    minDistance = newDistance;
                    shift = arrangeModel.getShiftTime();
                }
            } catch (Exception e) {
                log.error("[findNearestShift] ex", e);
            }
        }

        return shift;
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
