package htcc.employee.service.controller.checkin;

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
public class LocationCheckinController {

    @Autowired
    private CheckInBuzService checkInBuzService;

    @ApiOperation(value = "Điểm danh", response = BaseResponse.class)
    @PostMapping("/checkin/location")
    public BaseResponse checkInByLocation(@ApiParam(value = "[Body] Thông tin điểm danh vào", required = true)
                                    @RequestBody CheckinRequest request,
                                @ApiParam(hidden = true) HttpServletRequest httpServletRequest) {
        BaseResponse response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        response.setReturnMessage("Điểm danh thành công");

        long now = System.currentTimeMillis();
        Object requestTime = httpServletRequest.getAttribute(Constant.REQUEST_TIME);
        if (requestTime != null){
            now = (long)requestTime;
        }

        CheckinModel model = new CheckinModel(request, now);
        model.setSubType(CheckinSubTypeEnum.LOCATION.getValue());
        model.setStatus(1);

        try {
            response = checkInBuzService.doCheckInBuz(model);
            if (response.getReturnCode() != ReturnCodeEnum.SUCCESS.getValue()) {
                return response;
            }

        } catch (Exception e){
            log.error(String.format("[checkInByLocation] [%s] ex", StringUtil.toJsonString(model)), e);
            response = new BaseResponse<>(e);
        } finally {
            if (response.returnCode == ReturnCodeEnum.SUCCESS.getValue()) {
                checkInBuzService.onCheckInSuccess(model);
            }
        }
        return response;
    }

}
