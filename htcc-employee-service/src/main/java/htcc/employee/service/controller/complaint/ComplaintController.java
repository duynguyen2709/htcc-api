package htcc.employee.service.controller.complaint;

import htcc.common.constant.Constant;
import htcc.common.constant.ReturnCodeEnum;
import htcc.common.constant.ServiceSystemEnum;
import htcc.common.entity.base.BaseResponse;
import htcc.common.entity.complaint.ComplaintModel;
import htcc.common.entity.complaint.ComplaintRequest;
import htcc.common.entity.complaint.ComplaintResponse;
import htcc.common.entity.complaint.ResubmitComplaintModel;
import htcc.common.entity.log.RequestLogEntity;
import htcc.common.util.DateTimeUtil;
import htcc.common.util.StringUtil;
import htcc.employee.service.service.ComplaintService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@Api(tags = "API phản hồi/ khiếu nại",
     description = "API để phản hồi/ khiếu nại của nhân viên")
@RestController
@Log4j2
public class ComplaintController {

    @Autowired
    private ComplaintService service;



    @ApiOperation(value = "Gửi khiếu nại", response = BaseResponse.class)
    @PostMapping(value = "/complaint",
                 consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public BaseResponse complain(@ApiParam(value = "Đối tượng nhận (1: Hệ thống / 2: Công ty)", required = true, example = "2")
                                     @RequestParam int receiverType,
                                 @ApiParam(value = "Gửi ẩn danh hay không (0/1)", required = true, example = "0")
                                    @RequestParam int isAnonymous,
                                 @ApiParam(value = "Mã công ty", required = true, example = "VNG")
                                    @RequestParam String companyId,
                                 @ApiParam(value = "Tên đăng nhập", required = true, example = "duytv")
                                    @RequestParam String username,
                                 @ApiParam(value = "Thời gian client gửi request", required = true, example = "123")
                                    @RequestParam long clientTime,
                                 @ApiParam(value = "Loại phản hồi/ khiếu nại", required = true, example = "phiếu lương")
                                    @RequestParam String category,
                                 @ApiParam(value = "Nội dung phản hồi/ khiếu nại", required = true, example = "Abc")
                                    @RequestParam String content,
                                 @ApiParam(value = "Hình ảnh mô tả (tối đa 3 ảnh)",name = "images[]", required = false)
                                    @RequestParam(name = "images[]", required = false) MultipartFile images[],
                                 @ApiParam(hidden = true) HttpServletRequest httpServletRequest) {
        BaseResponse response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        response.setReturnMessage("Gửi phản hồi thành công");
        ComplaintRequest request = null;
        ComplaintModel model = null;

        long now = System.currentTimeMillis();
        Object requestTime = httpServletRequest.getAttribute(Constant.REQUEST_TIME);
        if (requestTime != null){
            now = (long)requestTime;
        }
        try {
            if (images.length > 3) {
                response = new BaseResponse(ReturnCodeEnum.MAXIMUM_FILES_EXCEED);
                return response;
            }

            request = new ComplaintRequest(receiverType, isAnonymous, companyId, username, clientTime, category, content, Arrays.asList(images));

            String error = request.isValid();
            if (!error.isEmpty()){
                response = new BaseResponse(ReturnCodeEnum.PARAM_DATA_INVALID);
                response.setReturnMessage(error);
                return response;
            }

            model = new ComplaintModel(request);

        } catch (Exception e){
            log.error(String.format("complain [%s] ex", StringUtil.toJsonString(request)), e);
            response = new BaseResponse<>(e);
        } finally {
            if (response.getReturnCode() == ReturnCodeEnum.SUCCESS.getValue()){
                service.handleUploadImage(Arrays.asList(images), model);
            }

            printRequestLogEntity(httpServletRequest, response, model, now);
        }
        return response;
    }

    private void printRequestLogEntity(HttpServletRequest request, BaseResponse response, ComplaintModel model, long requestTime){
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




    @ApiOperation(value = "Resubmit khiếu nại", response = BaseResponse.class)
    @PutMapping("/complaint/resubmit")
    public BaseResponse resubmitComplaint(@ApiParam(value = "[Body] Thông tin mới cần update", required = true)
                                              @RequestBody ResubmitComplaintModel request) {
        BaseResponse response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        try {
            if (!DateTimeUtil.isRightFormat(request.getYyyyMM(), "yyyyMM")) {
                return new BaseResponse(ReturnCodeEnum.DATE_WRONG_FORMAT, String.format("Tháng %s không phù hợp định dạng yyyyMM", request.getYyyyMM()));
            }

            response = service.resubmitComplaint(request);
        } catch (Exception e) {
            log.error(String.format("resubmitComplaint [%s] ex", StringUtil.toJsonString(request)), e);
            response = new BaseResponse<>(e);
        }
        return response;
    }




    @ApiOperation(value = "Lấy danh sách khiếu nại", response = ComplaintResponse.class)
    @GetMapping("/complaint/{companyId}/{username}/{month}")
    public BaseResponse getListComplaint(@ApiParam(value = "[Path] Mã công ty", required = true)
                                             @PathVariable String companyId,
                                         @ApiParam(value = "[Path] Tên đăng nhập", required = true)
                                         @PathVariable String username,
                                         @ApiParam(value = "[Path] Tháng (yyyyMM)", required = true)
                                             @PathVariable String month) {
        BaseResponse response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        try {
            String yyyyMM = StringUtil.valueOf(month);
            if (!DateTimeUtil.isRightFormat(yyyyMM, "yyyyMM")) {
                return new BaseResponse<>(ReturnCodeEnum.DATE_WRONG_FORMAT, String.format("Tháng %s không phù hợp định dạng yyyyMM", month));
            }

            List<ComplaintResponse> list = service.getComplaintLog(companyId, username, yyyyMM);
            if (list == null){
                throw new Exception("ComplaintService [getComplaintLog] return null");
            }

            response.data = list;

        } catch (Exception e) {
            log.error(String.format("getListComplaint [%s-%s-%s] ex", companyId, username, month), e);
            response = new BaseResponse<>(e);
        }
        return response;
    }
}
