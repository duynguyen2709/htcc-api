package htcc.employee.service.controller.checkin;

import htcc.common.component.redis.RedisService;
import htcc.common.constant.CheckinSubTypeEnum;
import htcc.common.constant.Constant;
import htcc.common.constant.ReturnCodeEnum;
import htcc.common.entity.base.BaseResponse;
import htcc.common.entity.checkin.CheckinModel;
import htcc.common.entity.checkin.CheckinRequest;
import htcc.common.util.StringUtil;
import htcc.employee.service.service.checkin.CheckInBuzService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Api(tags = "API điểm danh",
     description = "API điểm danh của nhân viên")
@RestController
@Log4j2
public class QrCodeCheckinController {

    @Autowired
    private CheckInBuzService checkInBuzService;

    @Autowired
    private RedisService redis;

    @ApiOperation(value = "Điểm danh", response = BaseResponse.class)
    @PostMapping("/checkin/qrcode")
    public BaseResponse checkInByQrCode(@ApiParam(value = "[Body] Thông tin điểm danh", required = true)
                                    @RequestBody CheckinRequest request,
                                @ApiParam(hidden = true) HttpServletRequest httpServletRequest) {
        BaseResponse response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        response.setReturnMessage("Điểm danh thành công");

        if (StringUtil.isEmpty(request.getQrCodeId())) {
            response = new BaseResponse(ReturnCodeEnum.PARAM_DATA_INVALID);
            response.setReturnMessage("Mã QR điểm danh không hợp lệ");
            return response;
        }

        long now = System.currentTimeMillis();
        Object requestTime = httpServletRequest.getAttribute(Constant.REQUEST_TIME);
        if (requestTime != null){
            now = (long)requestTime;
        }

        CheckinModel model = new CheckinModel(request, now);
        model.setSubType(CheckinSubTypeEnum.QR_CODE.getValue());
        model.setQrCodeId(request.getQrCodeId());
        model.setStatus(1);

        try {
            if (!redis.lock(model.getQrCodeId())) {
                throw new Exception("Redis lock failed ! key = " + model.getQrCodeId());
            }

            response = checkInBuzService.doCheckInBuz(model);
            if (response.getReturnCode() != ReturnCodeEnum.SUCCESS.getValue()) {
                return response;
            }

        } catch (Exception e){
            log.error(String.format("[checkInByQrCode] [%s] ex", StringUtil.toJsonString(model)), e);
            response = new BaseResponse<>(e);
        } finally {
            if (response.returnCode == ReturnCodeEnum.SUCCESS.getValue()) {
                checkInBuzService.onCheckInSuccess(model);
            }

            redis.unlock(model.getQrCodeId());
        }
        return response;
    }

}
