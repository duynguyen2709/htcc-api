package htcc.employee.service.controller.checkin;

import htcc.common.constant.*;
import htcc.common.entity.base.BaseResponse;
import htcc.common.entity.checkin.CheckinModel;
import htcc.common.entity.checkin.CheckinRequest;
import htcc.common.entity.log.RequestLogEntity;
import htcc.common.util.StringUtil;
import htcc.employee.service.service.checkin.CheckInService;
import htcc.employee.service.service.googledrive.GoogleDriveService;
import htcc.employee.service.service.checkin.CheckInBuzService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;

@Api(tags = "API điểm danh",
     description = "API điểm danh của nhân viên")
@RestController
@Log4j2
public class ImageCheckinController {

    @Autowired
    private GoogleDriveService googleDriveService;

    @Autowired
    private CheckInBuzService checkInBuzService;

    @ApiOperation(value = "Điểm danh", response = BaseResponse.class)
    @PostMapping(value = "/checkin/image",
                 consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public BaseResponse checkInByImage(@ApiParam(value = "[Param] Loại điểm danh (1: Vào / 2: Ra)", required = true)
                                            @RequestParam int type,
                                       @ApiParam(value = "[Param] Mã công ty", defaultValue = "VNG", required = true)
                                            @RequestParam String companyId,
                                       @ApiParam(value = "[Param] Mã chi nhánh đã chọn", defaultValue = "CAMPUS", required = true)
                                           @RequestParam String officeId,
                                       @ApiParam(value = "[Param] Tên đăng nhập", defaultValue = "admin", required = true)
                                           @RequestParam String username,
                                       @ApiParam(value = "[Param] Thời gian client gửi request", defaultValue = "123", required = true)
                                           @RequestParam long clientTime,
                                       @ApiParam(value = "[Param] Vĩ độ", defaultValue = "10.762462", required = true)
                                           @RequestParam float latitude,
                                       @ApiParam(value = "[Param] Kinh độ", defaultValue = "106.682752", required = true)
                                           @RequestParam float longitude,
                                       @ApiParam(value = "[Param] Điểm danh bằng wifi", defaultValue = "false", required = true)
                                           @RequestParam boolean usedWifi,
                                       @ApiParam(value = "[Param] IP đã thực hiện điểm danh", defaultValue = "127.0.0.1", required = true)
                                           @RequestParam String ip,
                                       @ApiParam(value = "[Param] Hình ảnh điểm danh", required = true)
                                           @RequestParam MultipartFile image,
                                       @ApiParam(hidden = true) HttpServletRequest httpServletRequest) {
        BaseResponse response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        response.setReturnMessage("Điểm danh thành công");

        CheckinRequest request = new CheckinRequest(type, StringUtil.EMPTY, StringUtil.EMPTY,
                companyId, officeId, username, clientTime, latitude, longitude, usedWifi, ip);

        long now = System.currentTimeMillis();
        Object requestTime = httpServletRequest.getAttribute(Constant.REQUEST_TIME);
        if (requestTime != null){
            now = (long)requestTime;
        }

        CheckinModel model = new CheckinModel(request, now);
        model.setSubType(CheckinSubTypeEnum.IMAGE.getValue());
        model.setStatus(2);

        try {
            response = checkInBuzService.doCheckInBuz(model);
            if (response.getReturnCode() != ReturnCodeEnum.SUCCESS.getValue()) {
                return response;
            }

            // upload image to ggdrive
            model.setImage(getCheckInImage(model, image));

        } catch (Exception e){
            log.error(String.format("[checkInByImage] [%s] ex", StringUtil.toJsonString(model)), e);
            response = new BaseResponse<>(e);
        } finally {
            if (response.returnCode == ReturnCodeEnum.SUCCESS.getValue()) {
                checkInBuzService.onCheckInSuccess(model);
            }

            printRequestLogEntity(httpServletRequest, response, model, now);
        }
        return response;
    }

    private void printRequestLogEntity(HttpServletRequest request, BaseResponse response, CheckinModel model, long requestTime){
        RequestLogEntity logEnt = new RequestLogEntity();
        try {
            logEnt.setRequestTime(requestTime);
            logEnt.setResponseTime(System.currentTimeMillis());
            logEnt.setMethod(request.getMethod());
            logEnt.setPath(request.getRequestURI());
            logEnt.setParams(request.getParameterMap());
            logEnt.setRequest(UriComponentsBuilder.fromHttpRequest(new ServletServerHttpRequest(request)).build().toUriString());
            logEnt.setServiceId(ServiceSystemEnum.getServiceFromUri(logEnt.path));
            logEnt.setIp(request);
            logEnt.body = StringUtil.toJsonString(model);
            logEnt.setResponse(StringUtil.toJsonString(response));
        } catch (Exception e) {
            log.warn("printRequestLogEntity ex {}", e.getMessage(), e);
        } finally {
            log.info(String.format("%s , Total Time : %sms\n",
                    StringUtil.toJsonString(logEnt), (logEnt.responseTime - logEnt.requestTime)));
        }
    }

    private String getCheckInImage(CheckinModel model, MultipartFile image) throws Exception {
        if (image == null){
            throw new Exception("CheckInImage is null");
        }

        String fileName = String.format("%s_%s_%s_%s_%s",
                (model.getType() == CheckinTypeEnum.CHECKIN.getValue() ? "CheckIn" : "CheckOut"),
                model.getCompanyId(), model.getUsername(), model.getDate(), model.getClientTime());

        String imageURL = googleDriveService.uploadCheckInImage(image, fileName);
        return imageURL;
    }
}
