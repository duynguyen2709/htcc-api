package htcc.employee.service.controller.checkin;

import htcc.common.constant.CheckinTypeEnum;
import htcc.common.constant.ReturnCodeEnum;
import htcc.common.entity.base.BaseResponse;
import htcc.common.entity.checkin.CheckinModel;
import htcc.common.entity.checkin.CheckinRequest;
import htcc.common.entity.checkin.CheckinResponse;
import htcc.common.entity.jpa.EmployeeInfo;
import htcc.common.entity.jpa.Office;
import htcc.common.util.DateTimeUtil;
import htcc.common.util.LocationUtil;
import htcc.common.util.StringUtil;
import htcc.employee.service.config.DbStaticConfigMap;
import htcc.employee.service.service.CheckInService;
import htcc.employee.service.service.jpa.EmployeeInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@Api(tags = "API điểm danh",
     description = "API điểm danh của nhân viên")
@RestController
@Log4j2
public class CheckinController {

    @Autowired
    private CheckInService checkInService;

    @Autowired
    private EmployeeInfoService employeeService;

    @Value("${service.allowDeleteCheckin}")
    private boolean allowDeleteCheckin;



    @ApiOperation(value = "Kiểm tra thông tin điểm danh của nhân viên", response = CheckinResponse.class)
    @GetMapping("/checkin/{companyId}/{username}")
    public BaseResponse getCheckinInfo(@ApiParam(value = "[Path] Mã công ty", required = true) @PathVariable(required = true) String companyId,
                                       @ApiParam(value = "[Path] Tên đăng nhập", required = true) @PathVariable(required = true) String username,
                                       @ApiParam(value = "[Query] Ngày (yyyyMMdd) (nếu ko gửi sẽ lấy ngày hiện tại)", required = false) @RequestParam(name = "date", required = false) String date) {

        BaseResponse<CheckinResponse> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        String yyyyMMdd = StringUtil.valueOf(date);

        try {
            if (!yyyyMMdd.isEmpty()){
                if (DateTimeUtil.parseStringToDate(yyyyMMdd, "yyyyMMdd") == null) {
                    return new BaseResponse<>(ReturnCodeEnum.PARAM_DATA_INVALID, String.format("Ngày %s không hợp lệ định dạng yyyyMMdd", date));
                }
            } else {
                yyyyMMdd = DateTimeUtil.parseTimestampToString(System.currentTimeMillis(), "yyyyMMdd");
            }

            // TODO : GET REAL DATA FROM DB CONFIG MAP OF COMPANY
            CheckinResponse data = new CheckinResponse(yyyyMMdd);

            // get today checkin info
            CompletableFuture<CheckinModel> checkInFuture = checkInService.getCheckInLog(companyId, username, yyyyMMdd);
            // get today checkout info
            CompletableFuture<CheckinModel> checkOutFuture = checkInService.getCheckOutLog(companyId, username, yyyyMMdd);

            EmployeeInfo employee = employeeService.findById(new EmployeeInfo.Key(companyId, username));
            if (employee == null) {
                response = new BaseResponse<>(ReturnCodeEnum.PARAM_DATA_INVALID);
                response.setReturnMessage(String.format("Không tìm thấy nhân viên %s", companyId));
                return response;
            }

            String officeId = employee.getOfficeId();
            Office officeInfo = DbStaticConfigMap.OFFICE_MAP.get(companyId + "_" + officeId);
            if (officeInfo == null) {
                response = new BaseResponse<>(ReturnCodeEnum.PARAM_DATA_INVALID);
                response.setReturnMessage(String.format("Không tìm thấy địa điểm điểm danh hợp lệ cho nhân viên %s", username));
                return response;
            }

            data.setForceUseWifi(officeInfo.isForceUseWifi());
            data.setAllowWifiIP(officeInfo.getAllowWifiIP());
            data.setMaxAllowDistance(officeInfo.getMaxAllowDistance());
            data.setValidLatitude(officeInfo.getLatitude());
            data.setValidLongitude(officeInfo.getLongitude());

            // TODO : GET REAL DATA (canCheckIn, validCheckinTime, validCheckoutTime) FROM DB CONFIG MAP OF COMPANY
            // finish all
            CompletableFuture.allOf(checkInFuture,checkOutFuture).join();

            data.setHasCheckedIn(checkInFuture.get());
            data.setHasCheckedOut(checkOutFuture.get());

            response.data = data;
        } catch (Exception e){
            log.error(String.format("getCheckinInfo [%s - %s - %s] ex", companyId, username, yyyyMMdd), e);
            response = new BaseResponse<>(e);
        }
        return response;
    }




    @ApiOperation(value = "Điểm danh", response = BaseResponse.class)
    @PostMapping("/checkin")
    public BaseResponse checkin(@ApiParam(value = "[Body] Thông tin điểm danh vào", required = true)
                                    @RequestBody CheckinRequest request) {
        BaseResponse response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        response.setReturnMessage("Điểm danh thành công");
        CheckinModel model = new CheckinModel(request);

        try {
            String error = model.isValid();
            if (!error.isEmpty()) {
                response = new BaseResponse<>(ReturnCodeEnum.PARAM_DATA_INVALID);
                response.setReturnMessage(error);
                return response;
            }

            error = validateCheckinRequest(request);
            if (!error.isEmpty()) {
                response = new BaseResponse<>(ReturnCodeEnum.PARAM_DATA_INVALID);
                response.setReturnMessage(error);
                return response;
            }

            // Verify time
            if (model.type == CheckinTypeEnum.CHECKIN.getValue()) {
                if (checkInService.getCheckInLog(model.companyId, model.username, model.date).get() != null) {
                    response = new BaseResponse<>(ReturnCodeEnum.CHECKIN_ALREADY);
                    return response;
                }
            } else if (model.type == CheckinTypeEnum.CHECKOUT.getValue()) {
                CheckinModel checkinData = checkInService.getCheckInLog(model.companyId, model.username, model.date).get();

                if (checkinData == null) {
                    response = new BaseResponse<>(ReturnCodeEnum.NOT_CHECKIN);
                    return response;
                }

                // Điểm danh vào trước khi điểm danh ra
                if (model.clientTime <= checkinData.clientTime) {
                    response = new BaseResponse<>(ReturnCodeEnum.CHECKIN_TIME_NOT_VALID);
                    return response;
                }

                //
                CheckinModel checkOutData = checkInService.getCheckOutLog(model.companyId, model.username, model.date).get();
                if (checkOutData != null) {
                    response = new BaseResponse<>(ReturnCodeEnum.CHECKOUT_ALREADY);
                    return response;
                }
            }

        } catch (Exception e){
            log.error(String.format("checkin [%s - %s] ex", request.companyId, request.username), e);
            response = new BaseResponse<>(e);
        } finally {
            if (response.returnCode == ReturnCodeEnum.SUCCESS.getValue()) {
                if (model.type == CheckinTypeEnum.CHECKIN.getValue()) {
                    checkInService.setCheckInLog(model);
                } else if (model.type == CheckinTypeEnum.CHECKOUT.getValue()) {
                    checkInService.setCheckOutLog(model);
                }
            }
        }
        return response;
    }

    private String validateCheckinRequest(CheckinRequest request) {
        EmployeeInfo employee = employeeService.findById(new EmployeeInfo.Key(request.getCompanyId(), request.getUsername()));
        if (employee == null) {
            return String.format("Không tìm thấy nhân viên %s", request.getUsername());
        }

        String officeId = employee.getOfficeId();
        Office officeInfo = DbStaticConfigMap.OFFICE_MAP.get(request.getCompanyId() + "_" + officeId);
        if (officeInfo == null){
            return String.format("Không tìm thấy địa điểm điểm danh hợp lệ cho nhân viên %s", request.getUsername());
        }

        double distance = LocationUtil.calculateDistance(request.getLatitude(), request.getLongitude(),
                officeInfo.getLatitude(), officeInfo.getLongitude());

        // extra 1m for delta
        if (distance > (officeInfo.getMaxAllowDistance() + 1)) {
            return "Khoảng cách điểm danh quá xa. Vui lòng thực hiện lại";
        }

        return StringUtil.EMPTY;
    }

    @ApiOperation(value = "Xóa thông tin điểm danh (testing)", response = BaseResponse.class)
    @DeleteMapping("/checkin/{companyId}/{username}")
    public BaseResponse deleteCheckinInfo(@ApiParam(value = "[Path] Mã công ty", required = true) @PathVariable(required = true) String companyId,
                                       @ApiParam(value = "[Path] Tên đăng nhập", required = true) @PathVariable(required = true) String username,
                                       @ApiParam(value = "[Query] Ngày (yyyyMMdd) (nếu ko gửi sẽ lấy ngày hiện tại)", required = false)
                                              @PathVariable(name = "date", required = false) String date) throws Exception {
        if (!allowDeleteCheckin){
            throw new Exception("Method Not Supported");
        }

        BaseResponse response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        String yyyyMMdd = StringUtil.valueOf(date);
        try {
            if (!yyyyMMdd.isEmpty()){
                if (DateTimeUtil.parseStringToDate(yyyyMMdd, "yyyyMMdd") == null) {
                    return new BaseResponse<>(ReturnCodeEnum.PARAM_DATA_INVALID, String.format("Ngày %s không hợp lệ định dạng yyyyMMdd", date));
                }
            } else {
                yyyyMMdd = DateTimeUtil.parseTimestampToString(System.currentTimeMillis(), "yyyyMMdd");
            }

            checkInService.deleteCheckInLog(companyId, username, yyyyMMdd);
        } catch (Exception e){
            log.error(String.format("deleteCheckinInfo [%s - %s - %s] ex", companyId, username, yyyyMMdd), e);
            response = new BaseResponse<>(e);
        }
        return response;
    }

}
