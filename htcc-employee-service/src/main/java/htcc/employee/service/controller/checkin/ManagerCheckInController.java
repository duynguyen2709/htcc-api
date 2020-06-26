package htcc.employee.service.controller.checkin;

import htcc.common.constant.CheckinSubTypeEnum;
import htcc.common.constant.ComplaintStatusEnum;
import htcc.common.constant.Constant;
import htcc.common.constant.ReturnCodeEnum;
import htcc.common.entity.base.BaseResponse;
import htcc.common.entity.checkin.*;
import htcc.common.entity.jpa.EmployeeInfo;
import htcc.common.util.DateTimeUtil;
import htcc.common.util.StringUtil;
import htcc.employee.service.repository.EmployeePermissionRepository;
import htcc.employee.service.service.checkin.CheckInBuzService;
import htcc.employee.service.service.checkin.CheckInService;
import htcc.employee.service.service.jpa.EmployeeInfoService;
import htcc.employee.service.service.statistic.EmployeeStatisticService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Api(tags = "API lấy danh sách điểm danh (CỦA QUẢN LÝ)")
@RestController
@Log4j2
public class ManagerCheckInController {

    @Autowired
    private EmployeeStatisticService statisticService;

    @Autowired
    private EmployeePermissionRepository permissionRepository;

    @Autowired
    private CheckInService checkInService;

    @Autowired
    private CheckInBuzService checkInBuzService;

    @Autowired
    private EmployeeInfoService employeeInfoService;

    @ApiOperation(value = "Danh sách điểm danh đã phê duyệt", response = BaseResponse.class)
    @GetMapping("/checkin/manager/{companyId}/{yyyyMM}")
    public BaseResponse getCheckInTimeForManager(@PathVariable String companyId,
                                                 @ApiParam(hidden = true) @RequestHeader(Constant.USERNAME) String username,
                                                 @PathVariable String yyyyMM) {
        BaseResponse<ManagerCheckInTimeResponse> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        try {
            if (!DateTimeUtil.isRightFormat(yyyyMM, "yyyyMM")) {
                response = new BaseResponse<>(ReturnCodeEnum.PARAM_DATA_INVALID);
                response.setReturnMessage(String.format("Tháng %s không phù hợp định dạng", yyyyMM));
                return response;
            }

            ManagerCheckInTimeResponse dataResponse = new ManagerCheckInTimeResponse();
            dataResponse.setDetailMap(new HashMap<>());

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            LocalDate startDate = LocalDate.parse(yyyyMM + "01", formatter);
            LocalDate endDate   = startDate.plusDays(startDate.lengthOfMonth());

            List<EmployeeInfo> employeeInfoList = permissionRepository.getCanManageEmployees(companyId, username);

            if (!employeeInfoList.isEmpty()) {
                for (EmployeeInfo employee : employeeInfoList) {
                    for (LocalDate date = startDate; date.isBefore(endDate); date = date.plusDays(1)) {
                        String yyyyMMdd = date.format(formatter);
                        handleBuz(dataResponse, companyId, employee.getUsername(), yyyyMMdd);
                    }
                }
            }

            response.setData(dataResponse);

        } catch (Exception e) {
            log.error("[getCheckInTimeForManager] [{}-{}-{}] ex", companyId, username, yyyyMM, e);
            response = new BaseResponse<>(e);
        }
        return response;
    }

    private void handleBuz(ManagerCheckInTimeResponse dataResponse, String companyId, String username, String yyyyMMdd) throws Exception {
        CompletableFuture<List<CheckinModel>> checkInTimeFuture = statisticService.getListCheckInTime(companyId, username, yyyyMMdd);
        if (!dataResponse.getDetailMap().containsKey(username)) {
            dataResponse.getDetailMap().put(username, new HashMap<>());
        }

        Map<String, List<ManagerDetailCheckInTime>> mapByDate = dataResponse.getDetailMap().get(username);
        if (!mapByDate.containsKey(yyyyMMdd)) {
            mapByDate.put(yyyyMMdd, new ArrayList<>());
        }

        List<CheckinModel> checkInTime = checkInTimeFuture.get();
        for (CheckinModel checkinModel : checkInTime) {
            if (checkinModel.getStatus() == ComplaintStatusEnum.DONE.getValue()) {
                mapByDate.get(yyyyMMdd).add(new ManagerDetailCheckInTime(checkinModel));
            }
        }
    }

    @ApiOperation(value = "Danh sách điểm danh cần phê duyệt", response = BaseResponse.class)
    @GetMapping("/checkin/manager/pending/{companyId}/{yyyyMM}")
    public BaseResponse getPendingCheckInTimeForManager(@PathVariable String companyId,
                                                        @ApiParam(hidden = true) @RequestHeader(Constant.USERNAME) String username,
                                                        @PathVariable String yyyyMM) {
        BaseResponse<List<ManagerDetailCheckInTime>> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        try {
            if (!DateTimeUtil.isRightFormat(yyyyMM, "yyyyMM")) {
                response = new BaseResponse<>(ReturnCodeEnum.PARAM_DATA_INVALID);
                response.setReturnMessage(String.format("Tháng %s không phù hợp định dạng", yyyyMM));
                return response;
            }

            List<CheckinModel> checkinModelList = checkInService.getListPendingCheckInLog(companyId, yyyyMM);
            if (checkinModelList == null) {
                throw new Exception("[checkInService.getListPendingCheckInLog] return null");
            }

            List<ManagerDetailCheckInTime> dataResponse = new ArrayList<>();
            Map<String, Boolean> canManageEmployeeMap = new HashMap<>();

            for (CheckinModel model : checkinModelList) {
                if (!canManageEmployeeMap.containsKey(model.getUsername())) {
                    boolean canManage = permissionRepository.canManageEmployee(companyId, username, model.getUsername());
                    canManageEmployeeMap.put(model.getUsername(), canManage);
                }

                if (canManageEmployeeMap.get(model.getUsername())) {
                    dataResponse.add(new ManagerDetailCheckInTime(model));
                }
            }

            response.setData(dataResponse);

        } catch (Exception e) {
            log.error("[getCheckInTimeForManager] [{}-{}-{}] ex", companyId, username, yyyyMM, e);
            response = new BaseResponse<>(e);
        }
        return response;
    }


    @ApiOperation(value = "Cập nhật trạng thái checkin", response = BaseResponse.class)
    @PutMapping("/checkin/status")
    public BaseResponse updateCheckInStatus(@ApiParam(value = "[Body] Trạng thái mới cần update", required = true)
                                                   @RequestBody UpdateCheckInStatusModel request) {
        BaseResponse response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        try {
            String error = request.isValid();
            if (!StringUtil.isEmpty(error)) {
                response = new BaseResponse(ReturnCodeEnum.PARAM_DATA_INVALID);
                response.setReturnMessage(error);
                return response;
            }

            response = checkInService.updateCheckInStatus(request);
        } catch (Exception e) {
            log.error(String.format("updateCheckInStatus [%s] ex", StringUtil.toJsonString(request)), e);
            response = new BaseResponse<>(e);
        }
        return response;
    }

    @ApiOperation(value = "Quản lý điểm danh thay nhân viên", response = BaseResponse.class)
    @PostMapping("/checkin/manager")
    public BaseResponse managerCheckIn(@ApiParam(value = "[Body] Thông tin điểm danh", required = true)
                                      @RequestBody ManagerCheckinRequest request,
                                      @ApiParam(hidden = true) HttpServletRequest httpServletRequest,
                                       @ApiParam(hidden = true) @RequestHeader(Constant.USERNAME) String username) {
        BaseResponse response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        response.setReturnMessage("Điểm danh thành công");

        long now = System.currentTimeMillis();
        Object requestTime = httpServletRequest.getAttribute(Constant.REQUEST_TIME);
        if (requestTime != null){
            now = (long)requestTime;
        }

        CheckinRequest checkinRequest = new CheckinRequest(request);
        CheckinModel model = new CheckinModel(checkinRequest, now);
        model.setSubType(CheckinSubTypeEnum.MANAGER.getValue());
        model.setStatus(ComplaintStatusEnum.DONE.getValue());

        try {
            EmployeeInfo manager = employeeInfoService.findById(new EmployeeInfo.Key(request.getCompanyId(), username));
            model.setApprover(String.format("%s (%s)", manager.getFullName(), manager.getUsername()));

            response = checkInBuzService.doCheckInBuz(model);
            if (response.getReturnCode() != ReturnCodeEnum.SUCCESS.getValue()) {
                return response;
            }

        } catch (Exception e){
            log.error(String.format("[managerCheckIn] [%s] ex", StringUtil.toJsonString(model)), e);
            response = new BaseResponse<>(e);
        } finally {
            if (response.returnCode == ReturnCodeEnum.SUCCESS.getValue()) {
                checkInBuzService.onCheckInSuccess(model);
            }
        }
        return response;
    }
}
