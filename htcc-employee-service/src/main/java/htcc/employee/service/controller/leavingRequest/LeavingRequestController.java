package htcc.employee.service.controller.leavingRequest;

import htcc.common.comparator.DateComparator;
import htcc.common.comparator.LeavingRequestResponseComparator;
import htcc.common.component.kafka.KafkaProducerService;
import htcc.common.constant.ComplaintStatusEnum;
import htcc.common.constant.ReturnCodeEnum;
import htcc.common.constant.SessionEnum;
import htcc.common.constant.WorkingDayTypeEnum;
import htcc.common.entity.base.BaseResponse;
import htcc.common.entity.dayoff.CompanyDayOffInfo;
import htcc.common.entity.jpa.EmployeeInfo;
import htcc.common.entity.workingday.WorkingDay;
import htcc.common.entity.leavingrequest.*;
import htcc.common.util.DateTimeUtil;
import htcc.common.util.StringUtil;
import htcc.employee.service.config.DbStaticConfigMap;
import htcc.employee.service.config.ServiceConfig;
import htcc.employee.service.service.LeavingRequestService;
import htcc.employee.service.service.jpa.EmployeeInfoService;
import htcc.employee.service.service.jpa.WorkingDayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
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

    @Autowired
    private ServiceConfig serviceConfig;

    @Autowired
    private EmployeeInfoService employeeInfoService;

    @Autowired
    private WorkingDayService workingDayService;

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
                response = new BaseResponse<>(ReturnCodeEnum.DATE_WRONG_FORMAT,
                        String.format("Năm %s không phù hợp định dạng yyyy", yyyy));
                return response;
            }

            CompletableFuture<List<LeavingRequestResponse>> detailFuture = service.getLeavingRequestLog(companyId, username, yyyy);

            // running async here to get total days off based on employee level
            CompletableFuture<Float> totalDaysOff = employeeInfoService.getTotalDayOff(companyId, username);

            List<LeavingRequestResponse> detail = detailFuture.get();
            if (detail == null) {
                throw new Exception("[LogService.getLeavingRequestLog] return null");
            }
            detail.sort(new LeavingRequestResponseComparator());

            // dataResponse
            LeavingRequestInfo data = new LeavingRequestInfo();

            List<String> categoryList = DbStaticConfigMap.COMPANY_DAY_OFF_INFO_MAP
                                        .get(companyId)
                                        .getCategoryList()
                                        .stream()
                                        .map(CompanyDayOffInfo.CategoryEntity::getCategory)
                                        .collect(Collectors.toList());

            data.setCategories(categoryList);
            data.setListRequest(detail);
            data.setTotalDays(totalDaysOff.get());
            countDayOff(data, detail);

            // if it was history year, then day off left = 0
            if (!DateTimeUtil.parseTimestampToString(System.currentTimeMillis(), "yyyy").equals(yyyy)) {
                data.setTotalDays(0.0f);
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
                    if (d.session == SessionEnum.FULL_DAY.getValue()) {
                        normalOffDay += 1;
                    }
                    else {
                        normalOffDay += 0.5f;
                    }
                } else {
                    if (d.session == SessionEnum.FULL_DAY.getValue()) {
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
        data.setLeftDays(data.getTotalDays() - data.getUsedDays());
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

            removeNonWorkingDays(model);
            if (model.getDetail().isEmpty()){
                response = new BaseResponse(ReturnCodeEnum.DAY_OFF_CONFLICT_REMOVED);
                return response;
            }

            setUseDayOff(model);

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

    private void setUseDayOff(LeavingRequestModel model) {
        boolean useDayOff = true;
        boolean hasSalary = false;

        List<CompanyDayOffInfo.CategoryEntity> categoryLists = DbStaticConfigMap.COMPANY_DAY_OFF_INFO_MAP.get(model.getCompanyId()).getCategoryList();
        for (CompanyDayOffInfo.CategoryEntity category : categoryLists) {
            if (category.getCategory().equals(model.getCategory())){
                useDayOff = category.isUseDayOff();
                hasSalary = category.isHasSalary();
                break;
            }
        }

        model.setUseDayOff(useDayOff);
        model.setHasSalary(hasSalary);
    }

    private void removeNonWorkingDays(LeavingRequestModel model) throws Exception {
        String officeId = employeeInfoService.findById(new EmployeeInfo.Key(model.getCompanyId(), model.getUsername())).getOfficeId();

        if (officeId.isEmpty()){
            throw new Exception("[removeNonWorkingDays] officeId empty");
        }

        List<WorkingDay> workingDays = workingDayService.findByCompanyIdAndOfficeId(model.getCompanyId(), officeId);

        List<WorkingDay> normalOffDays = workingDays.stream()
                .filter(d -> d.getType() == WorkingDayTypeEnum.NORMAL.getValue())
                         .collect(Collectors.toList());

        List<WorkingDay> specialOffDays = workingDays.stream()
                .filter(d -> d.getType() == WorkingDayTypeEnum.SPECIAL.getValue() && !d.getIsWorking())
                .collect(Collectors.toList());

        removeSpecialOffDays(model, specialOffDays);
        removeNormalOffDays(model, normalOffDays);
    }

    private void removeNormalOffDays(LeavingRequestModel model, List<WorkingDay> normalOffDays) {
        List<LeavingRequest.LeavingDayDetail> toRemove = new ArrayList<>();

        Map<String, WorkingDay> fullWeekWorkingDays = new HashMap<>();
        for (WorkingDay day : normalOffDays){
            String key = String.format("%s_%s", day.getWeekDay(), day.getSession());
            fullWeekWorkingDays.put(key, day);
        }

        for (int i = 1; i <= 7; i++){
            boolean isFullDay = false;
            if (fullWeekWorkingDays.containsKey(String.format("%s_0", i))){
                isFullDay = true;
            }

            if (fullWeekWorkingDays.containsKey(String.format("%s_1", i)) &&
                    fullWeekWorkingDays.containsKey(String.format("%s_2", i))){
                isFullDay = true;
            }

            if (!isFullDay){
                // there is at least 1 session
                if (fullWeekWorkingDays.containsKey(String.format("%s_1", i))){
                    // has session 1, then add session 2
                    WorkingDay workingDay = new WorkingDay();
                    workingDay.setType(WorkingDayTypeEnum.NORMAL.getValue());
                    workingDay.setWeekDay(i);
                    workingDay.setSession(SessionEnum.AFTERNOON.getValue());
                    workingDay.setIsWorking(false);
                    normalOffDays.add(workingDay);
                } else if (fullWeekWorkingDays.containsKey(String.format("%s_2", i))) {
                    // has session 1, then add session 2
                    WorkingDay workingDay = new WorkingDay();
                    workingDay.setType(WorkingDayTypeEnum.NORMAL.getValue());
                    workingDay.setWeekDay(i);
                    workingDay.setSession(SessionEnum.MORNING.getValue());
                    workingDay.setIsWorking(false);
                    normalOffDays.add(workingDay);
                } else {
                    // no session at all, then add full day
                    WorkingDay workingDay = new WorkingDay();
                    workingDay.setType(WorkingDayTypeEnum.NORMAL.getValue());
                    workingDay.setWeekDay(i);
                    workingDay.setSession(SessionEnum.FULL_DAY.getValue());
                    workingDay.setIsWorking(false);
                    normalOffDays.add(workingDay);
                }
            }
        }

        normalOffDays = normalOffDays.stream().filter(d -> !d.getIsWorking())
                .collect(Collectors.toList());

        for (LeavingRequest.LeavingDayDetail day : model.getDetail()){
            for (WorkingDay normalDay : normalOffDays){
                int weekDay = DateTimeUtil.getWeekDayInt(day.date);

                if (weekDay == normalDay.getWeekDay()){
                    // collision here, need to remove
                    if (normalDay.getSession() == SessionEnum.FULL_DAY.getValue()){
                        toRemove.add(day);
                    } else if (normalDay.getSession() == SessionEnum.MORNING.getValue()){
                        if (day.session == SessionEnum.FULL_DAY.getValue()) {
                            day.session = SessionEnum.AFTERNOON.getValue();
                        } else if (day.session == SessionEnum.MORNING.getValue()) {
                            toRemove.add(day);
                        }
                    } else if (normalDay.getSession() == SessionEnum.AFTERNOON.getValue()){
                        if (day.session == SessionEnum.FULL_DAY.getValue()) {
                            day.session = SessionEnum.MORNING.getValue();
                        } else if (day.session == SessionEnum.AFTERNOON.getValue()){
                            toRemove.add(day);
                        }
                    }
                }
            }
        }

        // remove all normal day off
        model.getDetail().removeAll(toRemove);
    }

    private void removeSpecialOffDays(LeavingRequestModel model, List<WorkingDay> specialOffDays) {
        List<LeavingRequest.LeavingDayDetail> toRemove = new ArrayList<>();
        for (LeavingRequest.LeavingDayDetail day : model.getDetail()){
            for (WorkingDay specialDay : specialOffDays){
                if (day.date.equals(specialDay.date)){
                    // collision here, need to remove
                    if (specialDay.getSession() == SessionEnum.FULL_DAY.getValue()){
                        toRemove.add(day);
                    } else if (specialDay.getSession() == SessionEnum.MORNING.getValue()){
                        if (day.session == SessionEnum.FULL_DAY.getValue()) {
                            day.session = SessionEnum.AFTERNOON.getValue();
                        } else if (day.session == SessionEnum.MORNING.getValue()){
                            toRemove.add(day);
                        }
                    } else if (specialDay.getSession() == SessionEnum.AFTERNOON.getValue()){
                        if (day.session == SessionEnum.FULL_DAY.getValue()) {
                            day.session = SessionEnum.MORNING.getValue();
                        } else if (day.session == SessionEnum.AFTERNOON.getValue()){
                            toRemove.add(day);
                        }
                    }
                }
            }
        }

        // remove all special day off
        model.getDetail().removeAll(toRemove);
    }

    private String validateNumOfDayOffLeft(LeavingRequestModel model) throws Exception {
        BaseResponse response = getLeavingRequestInfo(model.getCompanyId(), model.getUsername(),
                DateTimeUtil.parseTimestampToString(model.getClientTime(), "yyyy"));

        if (response == null || response.getReturnCode() != ReturnCodeEnum.SUCCESS.getValue()){
            throw new Exception("[getLeavingRequestInfo] return null");
        }
        // running async here to get total days off based on employee level
        CompletableFuture<Float> totalDaysOff = employeeInfoService.getTotalDayOff(model.getCompanyId(), model.getUsername());

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
                    if (d.session == SessionEnum.FULL_DAY.getValue()) {
                        daysOff += 1;
                    } else {
                        daysOff += 0.5f;
                    }
                }
            }
        }

        //count days in request
        float daysInRequest = 0.0f;
        for (LeavingRequest.LeavingDayDetail d : model.detail) {
            if (d.session == SessionEnum.FULL_DAY.getValue()) {
                daysInRequest += 1;
            } else {
                daysInRequest += 0.5f;
            }
        }

        float daysLeft = totalDaysOff.get() - daysOff;
        if (daysInRequest > daysLeft){
            return "Số ngày nghỉ phép còn lại trong năm không đủ";
        }

        return StringUtil.EMPTY;
    }

    private String validateDetailDates(LeavingRequestModel model) throws Exception {
        Set<String> listYear = new HashSet<>();
        for (LeavingRequest.LeavingDayDetail detail : model.detail) {
            listYear.add(detail.date.substring(0, 4));
        }

        for (String year : listYear) {
            // get list leavingRequest in year
            List<LeavingRequestResponse> response = service.getLeavingRequestLog(model.companyId, model.username, year).get();
            if (response == null){
                throw new Exception(String.format("[service.getLeavingRequestLog] [%s - %s - %s] return null", model.companyId, model.username, year));
            }

            response = response.stream()
                    .filter(c -> c.getStatus() != ComplaintStatusEnum.REJECTED.getValue())
                    .collect(Collectors.toList());

            // check collision in day
            for (LeavingRequestResponse each : response) {
                for (LeavingRequest.LeavingDayDetail detailInHistory : each.detail) {
                    for (LeavingRequest.LeavingDayDetail detailInNewForm : model.detail) {
                        if (detailInHistory.date.equals(detailInNewForm.date)) {
                            if (detailInHistory.session == SessionEnum.FULL_DAY.getValue() ||
                                detailInNewForm.session == SessionEnum.FULL_DAY.getValue()) {
                                return String.format("Ngày %s đã được đăng ký nghỉ trước đó",
                                        DateTimeUtil.convertToOtherFormat(detailInHistory.date, "yyyyMMdd", "dd/MM/yyyy"));

                            } else if (detailInHistory.session == detailInNewForm.session) {
                                return String.format("Buổi %s ngày %s đã được đăng ký nghỉ trước đó",
                                        detailInHistory.session == SessionEnum.MORNING.getValue() ? "sáng" : "chiều",
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
            if (!DateTimeUtil.isRightFormat(dateSubmit, "yyyyMMdd")) {
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

            String error = checkNotAllowDayCancel(model);
            if (!error.isEmpty()){
                response = new BaseResponse(ReturnCodeEnum.NOT_ALLOW_CANCEL_LEAVING_REQUEST);
                response.setReturnMessage(error);
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

    private String checkNotAllowDayCancel(LeavingRequestModel model){
        CompanyDayOffInfo info = DbStaticConfigMap.COMPANY_DAY_OFF_INFO_MAP.get(model.getCompanyId());
        if (!info.isAllowCancelRequest()) {
            return "Công ty không cho phép hủy đơn nghỉ phép";
        }

        model.getDetail().sort(new DateComparator());

        Date now = new Date(System.currentTimeMillis());
        String nowStr = DateTimeUtil.parseDateToString(now, "yyyyMMdd");
        Date today = DateTimeUtil.parseStringToDate(nowStr, "yyyyMMdd");
        Date firstDay = DateTimeUtil.parseStringToDate(model.detail.get(0).date, "yyyyMMdd");

        long diffInMillies = Math.abs(today.getTime() - firstDay.getTime());
        long dayDiff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

        return (dayDiff <= info.getMaxDayAllowCancel()) ? "Quá thời gian cho phép hủy đơn nghỉ phép" : StringUtil.EMPTY;
    }
}
