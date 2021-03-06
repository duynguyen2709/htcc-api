package htcc.employee.service.controller.statistics;

import htcc.common.constant.CheckinTypeEnum;
import htcc.common.constant.ComplaintStatusEnum;
import htcc.common.constant.ReturnCodeEnum;
import htcc.common.constant.SessionEnum;
import htcc.common.entity.base.BaseResponse;
import htcc.common.entity.checkin.CheckinModel;
import htcc.common.entity.leavingrequest.LeavingRequestModel;
import htcc.common.entity.shift.ShiftArrangementModel;
import htcc.common.entity.statistic.DetailCheckInTime;
import htcc.common.entity.statistic.EmployeeStatisticResponse;
import htcc.common.util.DateTimeUtil;
import htcc.employee.service.config.StatisticConfig;
import htcc.employee.service.service.statistic.EmployeeStatisticService;
import io.swagger.annotations.Api;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Api(tags = "API thống kê", description = "API thống kê nhân viên")
@RestController
@Log4j2
public class EmployeeStatisticController {

    @Autowired
    private EmployeeStatisticService statisticService;

    @Autowired
    private StatisticConfig statisticConfig;

    @GetMapping("/statistic")
    public BaseResponse getStatistics(@RequestParam String companyId,
                                      @RequestParam String username,
                                      @RequestParam String dateFrom,
                                      @RequestParam String dateTo) {
        BaseResponse<EmployeeStatisticResponse> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        try {
            response = validateDate(dateFrom, dateTo);
            if (response.getReturnCode() != ReturnCodeEnum.SUCCESS.getValue()) {
                return response;
            }

            EmployeeStatisticResponse dataResponse = new EmployeeStatisticResponse();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            LocalDate startDate = LocalDate.parse(dateFrom, formatter);
            LocalDate endDate = LocalDate.parse(dateTo, formatter);

            String today = DateTimeUtil.parseTimestampToString(System.currentTimeMillis(), "yyyyMMdd");
            LocalDate todayDate = LocalDate.parse(today, formatter);

            for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
                if (!date.isAfter(todayDate)) {
                    String yyyyMMdd = date.format(formatter);
                    handleBuz(dataResponse, companyId, username, yyyyMMdd);
                }
            }

            dataResponse.setTotalDays(dataResponse.getTotalWorkingDaysSet().size());
            dataResponse.setWorkingDays(dataResponse.getActualWorkingDaysSet().size());

            float nonPermissionOffDays = dataResponse.getTotalDays() - dataResponse.getWorkingDays() - dataResponse.getValidOffDays();
            if (nonPermissionOffDays < 0) {
                throw new Exception("calc nonPermissionOffDays return negative");
            }

            dataResponse.setNonPermissionOffDays(nonPermissionOffDays);
            if (dataResponse.getOvertimeHours() > 0) {
                String otHours = String.format("%.1f", dataResponse.getOvertimeHours());
                dataResponse.setOvertimeHours(Float.valueOf(otHours));
            }
            response.setData(dataResponse);

        } catch (Exception e) {
            log.error("[getStatistics] [{}-{}-{}-{}] ex",
                    companyId, username, dateFrom, dateTo, e);
            response = new BaseResponse<>(e);
        }
        return response;
    }

    private void handleBuz(EmployeeStatisticResponse dataResponse, String companyId, String username, String yyyyMMdd) throws Exception {
        CompletableFuture<List<CheckinModel>> checkInTime = statisticService.getListCheckInTime(companyId, username, yyyyMMdd);
        CompletableFuture<List<ShiftArrangementModel>> shiftArrangementList = statisticService.getListShiftArrangement(companyId, username, yyyyMMdd);
        CompletableFuture<List<LeavingRequestModel>> leaveRequestList = statisticService.getListLeavingRequest(companyId, username, yyyyMMdd);

        CompletableFuture.allOf(checkInTime, shiftArrangementList, leaveRequestList).join();

        EmployeeStatisticResponse.DetailStatisticsByDate detail = new EmployeeStatisticResponse.DetailStatisticsByDate();
        detail.setDate(yyyyMMdd);
        detail.setListCheckInTime(new ArrayList<>());
        detail.setListDayOff(new ArrayList<>());

        for (CheckinModel checkinModel : checkInTime.get()) {
            if (checkinModel.getStatus() == ComplaintStatusEnum.REJECTED.getValue()) {
                continue;
            }

            detail.getListCheckInTime().add(new DetailCheckInTime(checkinModel));

            if (checkinModel.getType() == CheckinTypeEnum.CHECKOUT.getValue() && checkinModel.hasOppositeAction) {
                dataResponse.getActualWorkingDaysSet().add(yyyyMMdd);
            }

            if (isOvertime(checkinModel)) {
                dataResponse.setOvertimeHours(dataResponse.getOvertimeHours() +
                        DateTimeUtil.calcHoursDiff(checkinModel.getShiftTime().getStartTime(), checkinModel.getShiftTime().getEndTime()));
            }

            dataResponse.setCheckinTimes(dataResponse.getCheckinTimes() + 1);
            if (checkinModel.isOnTime()) {
                dataResponse.setOnTimeCount(dataResponse.getOnTimeCount() + 1);
            }
        }

        for (LeavingRequestModel leavingRequestModel : leaveRequestList.get()) {
            EmployeeStatisticResponse.DetailDayOff dayDetail = new EmployeeStatisticResponse.DetailDayOff(leavingRequestModel, yyyyMMdd);
            detail.getListDayOff().add(dayDetail);

            float dayCount = dayDetail.getSession() == SessionEnum.FULL_DAY.getValue() ? 1.0f : 0.5f;
            dataResponse.setValidOffDays(dataResponse.getValidOffDays() + dayCount);
        }

        if (!shiftArrangementList.get().isEmpty()) {
            dataResponse.getTotalWorkingDaysSet().add(yyyyMMdd);
        }

        if (dataResponse.getCheckinTimes() != 0) {
            float percentage = dataResponse.getOnTimeCount() * 1.0f / dataResponse.getCheckinTimes();
            dataResponse.setOnTimePercentage(percentage * 100);
        }

        dataResponse.getDetailList().add(detail);
    }

    private boolean isOvertime(CheckinModel checkinModel) {
        return !checkinModel.isFixedShift &&
                checkinModel.hasOppositeAction &&
                checkinModel.type == CheckinTypeEnum.CHECKIN.getValue();
    }

    private BaseResponse validateDate(String dateFrom, String dateTo) {
        BaseResponse response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);

        if (!DateTimeUtil.isRightFormat(dateFrom, "yyyyMMdd")) {
            response = new BaseResponse(ReturnCodeEnum.PARAM_DATA_INVALID);
            response.setReturnMessage(String.format("Ngày bắt đầu %s không phù hợp định dạng", dateFrom));
            return response;
        }

        if (!DateTimeUtil.isRightFormat(dateTo, "yyyyMMdd")) {
            response = new BaseResponse(ReturnCodeEnum.PARAM_DATA_INVALID);
            response.setReturnMessage(String.format("Ngày kết thúc %s không phù hợp định dạng", dateTo));
            return response;
        }

        if (Long.parseLong(dateTo) < Long.parseLong(dateFrom)) {
            response = new BaseResponse(ReturnCodeEnum.PARAM_DATA_INVALID);
            response.setReturnMessage("Ngày kết thúc không được trước ngày bắt đầu");
            return response;
        }

        if (DateTimeUtil.calcDayDiff(dateFrom, dateTo, "yyyyMMdd") > statisticConfig.getMaxDayDiff()) {
            response = new BaseResponse(ReturnCodeEnum.PARAM_DATA_INVALID);
            response.setReturnMessage(String.format("Khoảng cách tối đa là %s ngày. Vui lòng chọn lại", statisticConfig.getMaxDayDiff()));
            return response;
        }
        return response;
    }
}
