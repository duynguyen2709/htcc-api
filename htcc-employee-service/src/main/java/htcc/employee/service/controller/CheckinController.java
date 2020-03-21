package htcc.employee.service.controller;

import htcc.common.constant.CheckinTypeEnum;
import htcc.common.constant.ReturnCodeEnum;
import htcc.common.entity.base.BaseResponse;
import htcc.common.entity.checkin.CheckinModel;
import htcc.common.entity.checkin.CheckinRequest;
import htcc.common.entity.checkin.CheckinResponse;
import htcc.common.util.DateTimeUtil;
import htcc.common.util.StringUtil;
import htcc.employee.service.service.CheckInService;
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
    private CheckInService service;

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

            CheckinResponse data = new CheckinResponse(yyyyMMdd);

            // get today checkin info
            CompletableFuture<CheckinModel> checkInFuture = service.getCheckInLog(companyId, username, yyyyMMdd);
            // get today checkout info
            CompletableFuture<CheckinModel> checkOutFuture = service.getCheckOutLog(companyId, username, yyyyMMdd);

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




    @ApiOperation(value = "Điểm danh vào", response = BaseResponse.class)
    @PostMapping("/checkin")
    public BaseResponse checkin(@ApiParam(value = "[Body] Thông tin điểm danh vào", required = true) @RequestBody CheckinRequest request) {
        BaseResponse response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        CheckinModel model = new CheckinModel(request);

        try {
            String error = model.isValid();
            if (!error.isEmpty()) {
                response = new BaseResponse<>(ReturnCodeEnum.PARAM_DATA_INVALID, error);
                return response;
            }

            /*
             TODO: Verify Checkin Info (Get Company Info, Long & Lat to compare)
             Not Implemented
            */

            // Verify time
            if (model.type == CheckinTypeEnum.CHECKIN.getValue()) {
                if (service.getCheckInLog(model.companyId, model.username, model.date).get() != null) {
                    response = new BaseResponse<>(ReturnCodeEnum.CHECKIN_ALREADY);
                    return response;
                }
            } else if (model.type == CheckinTypeEnum.CHECKOUT.getValue()) {
                CheckinModel checkinData = service.getCheckInLog(model.companyId, model.username, model.date).get();
                if (checkinData == null) {
                    response = new BaseResponse<>(ReturnCodeEnum.NOT_CHECKIN);
                    return response;
                }

                // Điểm danh vào trước khi điểm danh ra
                if (model.clientTime <= checkinData.clientTime) {
                    response = new BaseResponse<>(ReturnCodeEnum.CHECKIN_TIME_NOT_VALID);
                    return response;
                }

                if (service.getCheckOutLog(model.companyId, model.username, model.date) != null) {
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
                    service.setCheckInLog(model);
                } else if (model.type == CheckinTypeEnum.CHECKOUT.getValue()) {
                    service.setCheckOutLog(model);
                }
            }
        }
        return response;
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

            service.deleteCheckInLog(companyId, username, yyyyMMdd);
        } catch (Exception e){
            log.error(String.format("deleteCheckinInfo [%s - %s - %s] ex", companyId, username, yyyyMMdd), e);
            response = new BaseResponse<>(e);
        }
        return response;
    }

}
