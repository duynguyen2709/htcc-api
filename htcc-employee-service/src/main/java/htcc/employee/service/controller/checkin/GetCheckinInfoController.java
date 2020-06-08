package htcc.employee.service.controller.checkin;

import htcc.common.constant.ReturnCodeEnum;
import htcc.common.constant.SessionEnum;
import htcc.common.constant.WorkingDayTypeEnum;
import htcc.common.entity.base.BaseResponse;
import htcc.common.entity.checkin.CheckinModel;
import htcc.common.entity.checkin.CheckinResponse;
import htcc.common.entity.jpa.EmployeeInfo;
import htcc.common.entity.jpa.Office;
import htcc.common.entity.workingday.WorkingDay;
import htcc.common.util.DateTimeUtil;
import htcc.common.util.StringUtil;
import htcc.employee.service.config.DbStaticConfigMap;
import htcc.employee.service.service.checkin.CheckInService;
import htcc.employee.service.service.jpa.EmployeeInfoService;
import htcc.employee.service.service.jpa.OfficeService;
import htcc.employee.service.service.jpa.WorkingDayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

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

            // get today checkin info
            CompletableFuture<List<CheckinModel>> checkInFuture =
                    checkInService.getCheckInLog(companyId, username, yyyyMMdd);
            // get today checkout info
            CompletableFuture<List<CheckinModel>> checkOutFuture =
                    checkInService.getCheckOutLog(companyId, username, yyyyMMdd);

            EmployeeInfo employee = employeeService.findById(new EmployeeInfo.Key(companyId, username));
            if (employee == null) {
                response = new BaseResponse<>(ReturnCodeEnum.USER_NOT_FOUND);
                response.setReturnMessage(String.format("Không tìm thấy thông tin nhân viên %s", companyId));
                return response;
            }

            String officeId   = employee.getOfficeId();
            Office officeInfo = DbStaticConfigMap.OFFICE_MAP.get(companyId + "_" + officeId);
            if (officeId.isEmpty() || officeInfo == null) {
                response = new BaseResponse<>(ReturnCodeEnum.DATA_NOT_FOUND);
                response.setReturnMessage(String.format("Không tìm thấy địa điểm điểm danh hợp lệ cho nhân viên %s",
                        username));
                return response;
            }

            CompletableFuture.allOf(checkInFuture, checkOutFuture).join();
            setDetailCheckinTimes(data, checkInFuture.get(), checkOutFuture.get());
            setDetailOfficeList(data, companyId, yyyyMMdd);
            swapDefaultOffice(data, officeId);

            response.data = data;
        } catch (Exception e) {
            log.error(String.format("getCheckinInfo [%s - %s - %s] ex", companyId, username, yyyyMMdd), e);
            response = new BaseResponse<>(e);
        }
        return response;
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
        int n = checkinList.size();
        for (int i = 0; i < n; i++) {
            data.detailCheckinTimes.add(new CheckinResponse.DetailCheckinTime(checkinList.get(i)));

            if (checkoutList.size() > i) {
                data.detailCheckinTimes.add(new CheckinResponse.DetailCheckinTime(checkoutList.get(i)));
            }
        }
    }

    private void setDetailOfficeList(CheckinResponse data, String companyId, String yyyyMMdd) {
        List<Office> officeList = officeService.findByCompanyId(companyId);
        for (Office office : officeList) {
            CheckinResponse.OfficeDetail detail = new CheckinResponse.OfficeDetail();
            detail.setOfficeId(office.getOfficeId());
            detail.setForceUseWifi(office.isForceUseWifi());
            detail.setAllowWifiIP(office.getAllowWifiIP());
            detail.setMaxAllowDistance(office.getMaxAllowDistance());
            detail.setValidLatitude(office.getLatitude());
            detail.setValidLongitude(office.getLongitude());
            detail.setCanCheckin(getCanCheckinByOffice(companyId, office.getOfficeId(), yyyyMMdd));
            data.getOfficeList().add(detail);
        }
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
