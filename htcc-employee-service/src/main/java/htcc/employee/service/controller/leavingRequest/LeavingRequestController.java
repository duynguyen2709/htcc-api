package htcc.employee.service.controller.leavingRequest;

import htcc.common.component.kafka.KafkaProducerService;
import htcc.common.constant.ComplaintStatusEnum;
import htcc.common.constant.LeavingRequestSessionEnum;
import htcc.common.constant.ReturnCodeEnum;
import htcc.common.entity.base.BaseResponse;
import htcc.common.entity.leavingrequest.LeavingRequest;
import htcc.common.entity.leavingrequest.LeavingRequestInfo;
import htcc.common.entity.leavingrequest.LeavingRequestModel;
import htcc.common.entity.leavingrequest.LeavingRequestResponse;
import htcc.common.util.DateTimeUtil;
import htcc.common.util.StringUtil;
import htcc.employee.service.service.LeavingRequestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Api(tags = "API Xin Nghỉ Phép",
     description = "API get/submit form xin nghỉ phép của nhân viên")
@RestController
@Log4j2
public class LeavingRequestController {

    @Autowired
    private LeavingRequestService service;

    @Autowired
    private KafkaProducerService kafka;

    @ApiOperation(value = "Lấy thông tin phép còn lại & đơn đã submit", response = LeavingRequestInfo.class)
    @GetMapping("/leaving/{companyId}/{username}/{yyyy}")
    public BaseResponse getLeavingRequestInfo(@ApiParam(name = "companyId", value = "[Path] Mã công ty", defaultValue = "VNG", required = true)
                                                  @PathVariable String companyId,
                                              @ApiParam(name = "username", value = "[Path] Tên đăng nhập", defaultValue = "admin", required = true)
                                              @PathVariable String username,
                                              @ApiParam(name = "yyyy", value = "[Path] Năm", defaultValue = "2020", required = true)
                                              @PathVariable String yyyy){
        BaseResponse response = new BaseResponse(ReturnCodeEnum.SUCCESS);
        try {
            if (!DateTimeUtil.isRightFormat(yyyy, "yyyy")) {
                return new BaseResponse(ReturnCodeEnum.DATE_WRONG_FORMAT, String.format("Năm %s không phù hợp định dạng yyyy", yyyy));
            }

            List<LeavingRequestResponse> detail = service.getLeavingRequestLog(companyId, username, yyyy);
            if (detail == null) {
                throw new Exception("service.getLeavingRequestLog return null");
            }

            LeavingRequestInfo data = new LeavingRequestInfo();
            data.setCategories(service.getCategories());
            data.setListRequest(detail);
            data.setTotalDays(20.0f);
            data.setUsedDays(countUsedDay(detail));

            if (DateTimeUtil.parseTimestampToString(System.currentTimeMillis(), "yyyy").equals(yyyy)) {
                data.setLeftDays(data.getTotalDays() - data.getUsedDays());
            } else {
                data.setLeftDays(0.0f);
            }

            response.setData(data);

        } catch (Exception e) {
            log.error("[getLeavingRequestInfo] [{} - {} - {}] ex", companyId, username, yyyy, e);
            return new BaseResponse(e);
        }
        return response;
    }

    private float countUsedDay(List<LeavingRequestResponse> detail) {
        float count = 0.0f;
        for (LeavingRequestResponse entity : detail) {
            if (entity.getStatus() == ComplaintStatusEnum.REJECTED.getValue()) {
                // skip rejected forms
                continue;
            }

            for (LeavingRequest.LeavingDayDetail d : entity.detail) {
                if (d.session == LeavingRequestSessionEnum.FULL_DAY.getValue()){
                    count += 1;
                } else {
                    count += 0.5f;
                }
            }
        }

        return Float.valueOf(String.format("%.1f", count));
    }

    @ApiOperation(value = "Nộp đơn xin nghỉ phép", response = BaseResponse.class)
    @PostMapping("/leaving")
    public BaseResponse submitLeavingRequest(@RequestBody LeavingRequest request){
        BaseResponse response = new BaseResponse(ReturnCodeEnum.SUCCESS);
        response.setReturnMessage("Đơn nghỉ phép của bạn đã gửi thành công. Vui lòng chờ quản lý phê duyệt.");
        LeavingRequestModel model = null;
        try {
            String error = request.isValid();
            if (!error.isEmpty()){
                response = new BaseResponse(ReturnCodeEnum.PARAM_DATA_INVALID);
                response.setReturnMessage(error);
                return response;
            }

            model = new LeavingRequestModel(request);

            // validate list detail dates
            // check collision
            error = validateDetailDates(model);
            if (!error.isEmpty()) {
                response = new BaseResponse(ReturnCodeEnum.PARAM_DATA_INVALID);
                response.setReturnMessage(error);
                return response;
            }
            //

        } catch (Exception e) {
            log.error("[submitLeavingRequest] [{}] ex", StringUtil.toJsonString(request), e);
            response = new BaseResponse(e);
        } finally {
            if (response.getReturnCode() == ReturnCodeEnum.SUCCESS.getValue()){
                kafka.sendMessage(kafka.getBuzConfig().leavingRequestLog.topicName, model);
            }
        }
        return response;
    }

    private String validateDetailDates(LeavingRequestModel model) {
        Set<String> listYear = new HashSet<>();
        for (LeavingRequest.LeavingDayDetail detail : model.detail) {
            listYear.add(detail.date.substring(0, 4));
        }

        for (String year : listYear) {
            // get list leavingRequest in year
            List<LeavingRequestResponse> response = service.getLeavingRequestLog(model.companyId, model.username, year)
                                         .stream()
                                         .filter(c -> c.getStatus() != ComplaintStatusEnum.REJECTED.getValue())
                                         .collect(Collectors.toList());

            // check collision in day
            for (LeavingRequestResponse each : response) {
                for (LeavingRequest.LeavingDayDetail detailInHistory : each.detail) {
                    for (LeavingRequest.LeavingDayDetail detailInNewForm : model.detail) {
                        if (detailInHistory.date.equals(detailInNewForm.date)) {
                            if (detailInHistory.session == LeavingRequestSessionEnum.FULL_DAY.getValue() ||
                                detailInNewForm.session == LeavingRequestSessionEnum.FULL_DAY.getValue()) {
                                return String.format("Ngày %s đã được đăng ký nghỉ trước đó",
                                        DateTimeUtil.convertToOtherFormat(detailInHistory.date, "yyyyMMdd", "dd/MM/yyyy"));

                            } else if (detailInHistory.session == detailInNewForm.session) {
                                return String.format("Buổi %s ngày %s đã được đăng ký nghỉ trước đó",
                                        detailInHistory.session == LeavingRequestSessionEnum.MORNING.getValue() ? "sáng" : "chiều",
                                        DateTimeUtil.convertToOtherFormat(detailInHistory.date, "yyyyMMdd", "dd/MM/yyyy"));
                            }
                        }
                    }
                }
            }
        }

        return StringUtil.EMPTY;
    }
}
