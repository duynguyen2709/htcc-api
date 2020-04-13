package htcc.employee.service.controller.leavingRequest;

import htcc.common.comparator.DateComparator;
import htcc.common.comparator.LeavingRequestResponseComparator;
import htcc.common.component.kafka.KafkaProducerService;
import htcc.common.constant.ComplaintStatusEnum;
import htcc.common.constant.LeavingRequestSessionEnum;
import htcc.common.constant.ReturnCodeEnum;
import htcc.common.entity.base.BaseResponse;
import htcc.common.entity.leavingrequest.*;
import htcc.common.util.DateTimeUtil;
import htcc.common.util.StringUtil;
import htcc.employee.service.config.ServiceConfig;
import htcc.employee.service.service.LeavingRequestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Api(tags = "API Xin Nghỉ Phép",
     description = "API get/submit form xin nghỉ phép của nhân viên")
@RestController
@Log4j2
public class LeavingRequestController {

    // TODO : Remove hard code day off
    private static final float HARD_CODE_DAY_OFF = 20.0f;

    // TODO : remove hard code num of day allow cancel, change to get from config
    private static final int HARD_CODE_DAY_ALLOW_CANCEL = 1;

    @Autowired
    private LeavingRequestService service;

    @Autowired
    private KafkaProducerService kafka;

    @Autowired
    private ServiceConfig serviceConfig;

    @ApiOperation(value = "Lấy thông tin phép còn lại & đơn đã submit", response = LeavingRequestInfo.class)
    @GetMapping("/leaving/{companyId}/{username}/{yyyy}")
    public BaseResponse getLeavingRequestInfo(@ApiParam(name = "companyId", value = "[Path] Mã công ty", defaultValue = "VNG", required = true)
                                                  @PathVariable String companyId,
                                              @ApiParam(name = "username", value = "[Path] Tên đăng nhập", defaultValue = "admin", required = true)
                                              @PathVariable String username,
                                              @ApiParam(name = "yyyy", value = "[Path] Năm", defaultValue = "2020", required = true)
                                              @PathVariable String yyyy){
        BaseResponse<LeavingRequestInfo> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        try {
            if (!DateTimeUtil.isRightFormat(yyyy, "yyyy")) {
                return new BaseResponse(ReturnCodeEnum.DATE_WRONG_FORMAT, String.format("Năm %s không phù hợp định dạng yyyy", yyyy));
            }

            List<LeavingRequestResponse> detail = service.getLeavingRequestLog(companyId, username, yyyy);
            if (detail == null) {
                throw new Exception("service.getLeavingRequestLog return null");
            }
            detail.sort(new LeavingRequestResponseComparator());

            LeavingRequestInfo data = new LeavingRequestInfo();
            data.setCategories(service.getCategories());
            data.setListRequest(detail);

            data.setTotalDays(HARD_CODE_DAY_OFF);
            countDayOff(data, detail);

            if (DateTimeUtil.parseTimestampToString(System.currentTimeMillis(), "yyyy").equals(yyyy)) {
                data.setLeftDays(data.getTotalDays() - data.getUsedDays());
            } else {
                data.setLeftDays(0.0f);
            }

            response.setData(data);

        } catch (Exception e) {
            log.error("[getLeavingRequestInfo] [{} - {} - {}] ex", companyId, username, yyyy, e);
            response = new BaseResponse<>(e);
        }
        return response;
    }

    private void countDayOff(LeavingRequestInfo data, List<LeavingRequestResponse> detail){
        float normalOffDay = 0.0f;
        float externalOffDay = 0.0f;

        for (LeavingRequestResponse entity : detail) {
            if (entity.getStatus() == ComplaintStatusEnum.REJECTED.getValue()) {
                // skip rejected forms
                continue;
            }

            for (LeavingRequest.LeavingDayDetail d : entity.detail) {
                if (entity.useDayOff) {
                    if (d.session == LeavingRequestSessionEnum.FULL_DAY.getValue()) {
                        normalOffDay += 1;
                    }
                    else {
                        normalOffDay += 0.5f;
                    }
                } else {
                    if (d.session == LeavingRequestSessionEnum.FULL_DAY.getValue()) {
                        externalOffDay += 1;
                    }
                    else {
                        externalOffDay += 0.5f;
                    }
                }
            }
        }
        data.setUsedDays(Float.valueOf(String.format("%.1f", normalOffDay)));
        data.setExternalDaysOff(Float.valueOf(String.format("%.1f", externalOffDay)));
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

            // TODO : GET CONFIG FROM DB OF COMPANY
            model.setUseDayOff(serviceConfig.getLeavingRequestCategoryList().getOrDefault(request.getCategory(), true));

            // check if days off left is enough to register
            if (model.useDayOff) {
                error = validateNumOfDayOffLeft(model);
                if (!error.isEmpty()) {
                    response = new BaseResponse(ReturnCodeEnum.PARAM_DATA_INVALID);
                    response.setReturnMessage(error);
                    return response;
                }
            }

            // validate list detail dates
            // check collision
            error = validateDetailDates(model);
            if (!error.isEmpty()) {
                response = new BaseResponse(ReturnCodeEnum.PARAM_DATA_INVALID);
                response.setReturnMessage(error);
                return response;
            }

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

    private String validateNumOfDayOffLeft(LeavingRequestModel model) throws Exception {
        BaseResponse response = getLeavingRequestInfo(model.getCompanyId(), model.getUsername(),
                DateTimeUtil.parseTimestampToString(model.getClientTime(), "yyyy"));

        if (response == null || response.getReturnCode() != ReturnCodeEnum.SUCCESS.getValue()){
            throw new Exception("[getLeavingRequestInfo] return null");
        }

        LeavingRequestInfo info = (LeavingRequestInfo) response.getData();

        // count days used
        float daysOff = 0.0f;
        for (LeavingRequestResponse entity : info.listRequest) {
            if (entity.getStatus() == ComplaintStatusEnum.REJECTED.getValue()) {
                // skip rejected forms
                continue;
            }

            for (LeavingRequest.LeavingDayDetail d : entity.detail) {
                if (entity.useDayOff) {
                    if (d.session == LeavingRequestSessionEnum.FULL_DAY.getValue()) {
                        daysOff += 1;
                    } else {
                        daysOff += 0.5f;
                    }
                }
            }
        }
        float daysLeft = HARD_CODE_DAY_OFF - daysOff;

        //count days in request
        float daysInRequest = 0.0f;
        for (LeavingRequest.LeavingDayDetail d : model.detail) {
            if (d.session == LeavingRequestSessionEnum.FULL_DAY.getValue()) {
                daysInRequest += 1;
            } else {
                daysInRequest += 0.5f;
            }
        }

        if (daysInRequest > daysLeft){
            return "Số ngày nghỉ phép còn lại trong năm không đủ";
        }

        return StringUtil.EMPTY;
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


    @ApiOperation(value = "Hủy đơn xin nghỉ phép", response = BaseResponse.class)
    @PostMapping("/leaving/cancel/{leavingRequestId}/{dateSubmit}")
    public BaseResponse cancelLeavingRequest(@ApiParam(name = "leavingRequestId", value = "[Path] Mã đơn nghỉ phép", defaultValue = "#VNG-LR-00001", required = true)
                                                 @PathVariable String leavingRequestId,
                                             @ApiParam(name = "dateSubmit", value = "[Path] Ngày submit đơn gốc (yyyyMMdd)", defaultValue = "20200408", required = true)
                                             @PathVariable String dateSubmit){
        BaseResponse response = new BaseResponse(ReturnCodeEnum.SUCCESS);
        response.setReturnMessage("Đơn nghỉ phép của bạn đã hủy thành công");
        try {
            if (DateTimeUtil.isRightFormat(dateSubmit, "yyyyMMdd") == false) {
                response = new BaseResponse(ReturnCodeEnum.DATE_WRONG_FORMAT,
                        String.format("Ngày %s không phù hợp định dạng yyyyMMdd", dateSubmit));
                return response;
            }

            if (!leavingRequestId.startsWith("#")) {
                leavingRequestId = "#" + leavingRequestId;
            }

            String yyyyMM = DateTimeUtil.convertToOtherFormat(dateSubmit, "yyyyMMdd", "yyyyMM");

            LeavingRequestModel model = service.getOneLeavingRequest(leavingRequestId.substring(1), yyyyMM);

            if (model == null){
                response = new BaseResponse(ReturnCodeEnum.LOG_NOT_FOUND);
                response.setReturnMessage(String.format("Không tìm thấy đơn nghỉ phép %s", leavingRequestId));
                return response;
            }

            if (checkNotAllowDayCancel(model)){
                response = new BaseResponse(ReturnCodeEnum.TIME_LIMIT_EXCEED);
                return response;
            }

            UpdateLeavingRequestStatusModel updateModel = new UpdateLeavingRequestStatusModel();
            updateModel.setYyyyMM(yyyyMM);
            updateModel.setLeavingRequestId(leavingRequestId);
            updateModel.setStatus(ComplaintStatusEnum.REJECTED.getValue());
            updateModel.setResponse("Nhân viên tự hủy đơn");
            updateModel.setApprover(StringUtil.EMPTY);

            BaseResponse response1 = service.updateLeavingRequestStatus(updateModel);
            if (response1 != null && response1.getReturnCode() == ReturnCodeEnum.SUCCESS.getValue()){
                return response;
            } else {
                return response1;
            }

        } catch (Exception e) {
            log.error("[cancelLeavingRequest] [{} - {}] ex", leavingRequestId, dateSubmit, e);
            response = new BaseResponse(e);
        }
        return response;
    }

    private boolean checkNotAllowDayCancel(LeavingRequestModel model){
        model.getDetail().sort(new DateComparator());

        Date now = new Date(System.currentTimeMillis());
        String nowStr = DateTimeUtil.parseDateToString(now, "yyyyMMdd");
        Date today = DateTimeUtil.parseStringToDate(nowStr, "yyyyMMdd");
        Date firstDay = DateTimeUtil.parseStringToDate(model.detail.get(0).date, "yyyyMMdd");

        long diffInMillies = Math.abs(today.getTime() - firstDay.getTime());
        long dayDiff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

        return dayDiff <= HARD_CODE_DAY_ALLOW_CANCEL;
    }
}
