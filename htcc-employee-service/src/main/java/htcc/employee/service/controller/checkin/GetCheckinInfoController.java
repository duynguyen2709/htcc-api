package htcc.employee.service.controller.checkin;

import htcc.common.constant.ReturnCodeEnum;
import htcc.common.constant.SessionEnum;
import htcc.common.constant.WorkingDayTypeEnum;
import htcc.common.entity.base.BaseResponse;
import htcc.common.entity.checkin.CheckinModel;
import htcc.common.entity.checkin.CheckinResponse;
import htcc.common.entity.jpa.EmployeeInfo;
import htcc.common.entity.jpa.Office;
import htcc.common.entity.shift.ShiftArrangementModel;
import htcc.common.entity.shift.ShiftTime;
import htcc.common.entity.workingday.WorkingDay;
import htcc.common.util.DateTimeUtil;
import htcc.common.util.StringUtil;
import htcc.employee.service.service.checkin.CheckInService;
import htcc.employee.service.service.jpa.EmployeeInfoService;
import htcc.employee.service.service.jpa.OfficeService;
import htcc.employee.service.service.jpa.WorkingDayService;
import htcc.employee.service.service.shiftarrangement.ShiftArrangementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Api(tags = "API điểm danh", description = "API điểm danh của nhân viên")
@RestController
@Log4j2
public class GetCheckinInfoController {

    @Autowired
    private CheckInService checkInService;

    @Autowired
    private EmployeeInfoService employeeService;

    @Autowired
    private WorkingDayService workingDayService;

    @Autowired
    private OfficeService officeService;

    @Autowired
    private ShiftArrangementService shiftArrangementService;

    @ApiOperation(value = "Kiểm tra thông tin điểm danh của nhân viên", response = CheckinResponse.class)
    @GetMapping("/checkin/{companyId}/{username}")
    public BaseResponse getCheckinInfo(@ApiParam(value = "[Path] Mã công ty", required = true)
                                           @PathVariable(required = true) String companyId,
                                       @ApiParam(value = "[Path] Tên đăng nhập", required = true)
                                       @PathVariable(required = true) String username,
                                       @ApiParam(value = "[Query] Ngày (yyyyMMdd) (nếu ko gửi sẽ lấy ngày hiện tại)", required = false)
                                           @RequestParam(name = "date", required = false) String date) {
        BaseResponse<CheckinResponse> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        String yyyyMMdd = StringUtil.valueOf(date);
        try {
            if (!yyyyMMdd.isEmpty()) {
                if (DateTimeUtil.parseStringToDate(yyyyMMdd, "yyyyMMdd") == null) {
                    response = new BaseResponse<>(ReturnCodeEnum.PARAM_DATA_INVALID);
                    response.setReturnMessage(String.format("Ngày %s không phù hợp định dạng yyyyMMdd", date));
                    return response;
                }
            } else {
                yyyyMMdd = DateTimeUtil.parseTimestampToString(System.currentTimeMillis(), "yyyyMMdd");
            }

            CheckinResponse data = new CheckinResponse(yyyyMMdd);
            String officeId = "";

            // get today checkin info
            CompletableFuture<List<CheckinModel>> checkInFuture =
                    checkInService.getCheckInLog(companyId, username, yyyyMMdd);
            // get today checkout info
            CompletableFuture<List<CheckinModel>> checkOutFuture =
                    checkInService.getCheckOutLog(companyId, username, yyyyMMdd);

            EmployeeInfo employee = employeeService.findById(new EmployeeInfo.Key(companyId, username));
            if (employee == null) {
                response = new BaseResponse<>(ReturnCodeEnum.USER_NOT_FOUND);
                response.setReturnMessage(String.format("Không tìm thấy thông tin nhân viên %s", username));
                return response;
            }

            List<ShiftArrangementModel> shiftArrangementList = new ArrayList<>(
                    shiftArrangementService.getShiftArrangementListByEmployee(companyId, username, yyyyMMdd));

            ShiftTime nearestShift = findNearestShift(shiftArrangementList, yyyyMMdd);
            if (nearestShift != null) {
                officeId = nearestShift.getOfficeId();
            }
            else {
                officeId = employee.getOfficeId();
            }

            if (officeId.isEmpty()) {
                response = new BaseResponse<>(ReturnCodeEnum.DATA_NOT_FOUND);
                response.setReturnMessage(String.format("Không tìm thấy địa điểm điểm danh hợp lệ cho nhân viên %s",
                        username));
                return response;
            }

            CompletableFuture.allOf(checkInFuture, checkOutFuture).join();
            if (checkInFuture == null || checkInFuture.get() == null ||
                    checkOutFuture == null || checkOutFuture.get() == null) {
                throw new Exception("checkInFuture | checkOutFuture return null");
            }

            setDetailCheckinTimes(data, checkInFuture.get(), checkOutFuture.get());
            setDetailOfficeList(data, companyId, yyyyMMdd, shiftArrangementList);
            swapDefaultOffice(data, officeId);

            response.setData(data);
        } catch (Exception e) {
            log.error(String.format("getCheckinInfo [%s - %s - %s] ex", companyId, username, yyyyMMdd), e);
            response = new BaseResponse<>(e);
        }
        return response;
    }

    private ShiftTime findNearestShift(List<ShiftArrangementModel> shiftList, String yyyyMMdd) {
        if (shiftList.isEmpty()) {
            return null;
        }

        ShiftTime shift = shiftList.get(0).getShiftTime();
        boolean isFixed = shiftList.get(0).isFixed();
        long now = System.currentTimeMillis();
        long minDistance = now;

        for (ShiftArrangementModel arrangeModel : shiftList) {
            try {
                String time = arrangeModel.getShiftTime().getStartTime();

                String fullTimeStr = String.format("%s %s", yyyyMMdd, time);
                long timeMillis = new SimpleDateFormat("yyyyMMdd HH:mm").parse(fullTimeStr).getTime();

                long newDistance = Math.abs(now - timeMillis);
                if (newDistance < minDistance) {
                    minDistance = newDistance;
                    shift = arrangeModel.getShiftTime();
                    isFixed = arrangeModel.isFixed();
                }
            } catch (Exception e) {
                log.error("[findNearestShift] ex", e);
            }
        }
        return shift;
    }

    private void swapDefaultOffice(CheckinResponse data, String officeId) {
        List<CheckinResponse.OfficeDetail> officeDetailList = data.getOfficeList();

        int n = officeDetailList.size();
        int index = 0;
        for (int i = 0; i < n; i++) {
            if (officeDetailList.get(i).getOfficeId().equals(officeId)) {
                index = i;
                break;
            }
        }

        if (index != 0) {
            CheckinResponse.OfficeDetail temp = officeDetailList.get(index);
            officeDetailList.set(index, officeDetailList.get(0));
            officeDetailList.set(0, temp);
        }
    }

    private void setDetailCheckinTimes(CheckinResponse data, List<CheckinModel> checkinList,
                                       List<CheckinModel> checkoutList) {

        List<CheckinModel> combinedList = new ArrayList<>(checkinList);
        combinedList.addAll(checkoutList);

        combinedList.sort(new Comparator<CheckinModel>() {
            @Override
            public int compare(CheckinModel o1, CheckinModel o2) {
                return Long.compare(o1.getClientTime(), o2.getClientTime());
            }
        });

        for (CheckinModel model : combinedList) {
            data.detailCheckinTimes.add(new CheckinResponse.DetailCheckinTime(model));
        }
    }

    private void setDetailOfficeList(CheckinResponse data, String companyId, String yyyyMMdd, List<ShiftArrangementModel> shiftArrangementList) {
        List<Office> officeList = officeService.findByCompanyId(companyId);
        List<ShiftArrangementModel> fixedShiftList = shiftArrangementList.stream().filter(c -> c.isFixed()).collect(Collectors.toList());
        List<ShiftArrangementModel> shiftByDateList = shiftArrangementList.stream().filter(c -> !c.isFixed()).collect(Collectors.toList());

        for (Office office : officeList) {
            CheckinResponse.OfficeDetail detail = new CheckinResponse.OfficeDetail();
            detail.setOfficeId(office.getOfficeId());
            detail.setForceUseWifi(office.isForceUseWifi());
            detail.setAllowWifiIP(office.getAllowWifiIP());
            detail.setMaxAllowDistance(office.getMaxAllowDistance());
            detail.setValidLatitude(office.getLatitude());
            detail.setValidLongitude(office.getLongitude());
            detail.setCanCheckin(getCanCheckinByShift(office.getOfficeId(), fixedShiftList));
            detail.setCanCheckin(getCanCheckinByOffice(companyId, office.getOfficeId(), yyyyMMdd));
            if (!shiftByDateList.isEmpty()) {
                detail.setCanCheckin(getCanCheckinByShift(office.getOfficeId(), shiftByDateList));
            }
            data.getOfficeList().add(detail);
        }
    }

    private boolean getCanCheckinByShift(String officeId, List<ShiftArrangementModel> shiftArrangementList) {
        for (ShiftArrangementModel model : shiftArrangementList) {
            if (model.getOfficeId().equals(officeId)) {
                return true;
            }
        }
        return false;
    }

    private boolean getCanCheckinByOffice(String companyId, String officeId, String yyyyMMdd) {
        List<WorkingDay> workingDays = workingDayService.findByCompanyIdAndOfficeId(companyId, officeId);

        List<WorkingDay> normalOffDays = workingDays.stream().filter(d -> !d.getIsWorking() &&
                d.getType() == WorkingDayTypeEnum.NORMAL.getValue() &&
                DateTimeUtil.getWeekDayInt(yyyyMMdd) == d.getWeekDay() &&
                d.getSession() == SessionEnum.FULL_DAY.getValue()).collect(Collectors.toList());

        List<WorkingDay> specialOffDays = workingDays.stream().filter(d -> !d.getIsWorking() &&
                d.getType() == WorkingDayTypeEnum.SPECIAL.getValue() && d.getDate().equals(yyyyMMdd) &&
                d.getSession() == SessionEnum.FULL_DAY.getValue()).collect(Collectors.toList());

        return specialOffDays.isEmpty() && normalOffDays.isEmpty();
    }
}
