package htcc.employee.service.controller.shifttime;

import htcc.common.constant.Constant;
import htcc.common.constant.ReturnCodeEnum;
import htcc.common.constant.ShiftArrangementTypeEnum;
import htcc.common.constant.WeekDayEnum;
import htcc.common.entity.base.BaseResponse;
import htcc.common.entity.jpa.EmployeeInfo;
import htcc.common.entity.jpa.Office;
import htcc.common.entity.shift.*;
import htcc.common.util.DateTimeUtil;
import htcc.common.util.StringUtil;
import htcc.employee.service.repository.EmployeePermissionRepository;
import htcc.employee.service.service.jpa.EmployeeInfoService;
import htcc.employee.service.service.jpa.FixedShiftArrangementService;
import htcc.employee.service.service.jpa.OfficeService;
import htcc.employee.service.service.jpa.ShiftTimeService;
import htcc.employee.service.service.shiftarrangement.ShiftArrangementService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@Log4j2
public class ShiftArrangementController {

    @Autowired
    private ShiftArrangementService shiftArrangementService;

    @Autowired
    private ShiftTimeService shiftTimeService;

    @Autowired
    private FixedShiftArrangementService fixedShiftArrangementService;

    @Autowired
    private OfficeService officeService;

    @Autowired
    private EmployeePermissionRepository permissionRepo;

    @GetMapping("/shifts/{companyId}/{year}/{week}")
    public BaseResponse getShiftTimeInfo(@PathVariable String companyId,
                                         @PathVariable int year,
                                         @PathVariable int week,
                                         @ApiParam(hidden = true) @RequestHeader(Constant.USERNAME) String actor) {
        BaseResponse<ShiftArrangementResponse> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        try {
            List<ShiftArrangementModel> logEntities = shiftArrangementService.getShiftArrangementLog(companyId, week);
            if (logEntities == null) {
                throw new Exception("shiftArrangementService.getShiftArrangementLog return null");
            }

            List<FixedShiftArrangement> fixedShiftArrangementList = fixedShiftArrangementService.findByCompanyId(companyId);

            ShiftArrangementResponse dataResponse = new ShiftArrangementResponse();
            dataResponse.setWeek(week);
            dataResponse.setYear(year);

            // !important! : don't change the order of these functions
            initShiftList(dataResponse, fixedShiftArrangementList, companyId, actor);
            setCanManageEmployees(dataResponse, companyId, actor);

            setFixedShiftList(dataResponse, fixedShiftArrangementList);
            setShiftByDateList(dataResponse, logEntities);

            response.setData(dataResponse);
        } catch (Exception e) {
            log.error("[getShiftTimeInfo] [{} - {}] ex", companyId, week, e);
            response = new BaseResponse<>(e);
        }
        return response;
    }

    private void initShiftList(ShiftArrangementResponse dataResponse, List<FixedShiftArrangement> fixedShiftArrangementList,
                               String companyId, String actor) throws Exception {

        final int week = dataResponse.getWeek();
        final int year = dataResponse.getYear();
        Map<String, Boolean> canManageOfficesMap = new HashMap<>();

        List<Office> officeList = officeService.findByCompanyId(companyId);

        dataResponse.setFixedShiftList(new ArrayList<>());
        dataResponse.setShiftByDateList(new ArrayList<>());

        for (Office office : officeList) {
            if (permissionRepo.canManageOffice(companyId, actor, office.getOfficeId())) {
                canManageOfficesMap.put(office.getOfficeId(), true);
                // add office
                ShiftArrangementResponse.OfficeShiftInfo officeShiftInfo = new ShiftArrangementResponse.OfficeShiftInfo();
                officeShiftInfo.setOfficeId(office.getOfficeId());
                officeShiftInfo.setShiftDetailList(new ArrayList<>());

                // add list shift time of office
                List<ShiftTime> shiftTimeByOfficeList = shiftTimeService.findByCompanyIdAndOfficeId(companyId, office.getOfficeId());
                for (ShiftTime shiftTime : shiftTimeByOfficeList) {
                    // add detail for each shift
                    ShiftArrangementResponse.ShiftDetail shiftDetail = new ShiftArrangementResponse.ShiftDetail();
                    shiftDetail.setShiftId(shiftTime.getShiftId());
                    shiftDetail.setShiftTime(String.format("%s - %s", shiftTime.getStartTime(), shiftTime.getEndTime()));
                    shiftDetail.setShiftName(shiftTime.getShiftName());
                    shiftDetail.setDetailList(new ArrayList<>());

                    // add date & list employee
                    for (int plusDay = 0; plusDay < 7 ; plusDay++) {
                        String yyyyMMdd = DateTimeUtil.getDateStringFromWeek(plusDay, week, year, "yyyyMMdd");
                        ShiftArrangementResponse.ShiftByDayDetail shiftByDayDetail = new ShiftArrangementResponse.ShiftByDayDetail();
                        // [important] week start from Monday and value = 2
                        shiftByDayDetail.setWeekDay(plusDay + 2);
                        if (shiftByDayDetail.getWeekDay() == WeekDayEnum.SUNDAY.getValue()) {
                            shiftByDayDetail.setWeekDay(WeekDayEnum.SUNDAY_LAST_WEEK.getValue());
                        }
                        shiftByDayDetail.setDate(yyyyMMdd);
                        shiftByDayDetail.setEmployeeList(new ArrayList<>());

                        shiftDetail.getDetailList().add(shiftByDayDetail);
                    }

                    // add shift detail to list
                    officeShiftInfo.getShiftDetailList().add(shiftDetail);
                }

                // add office info to response
                // !Important! : only set the copy value, not set the reference else
                // it will point to same list
                dataResponse.getShiftByDateList().add(officeShiftInfo.copy());
                dataResponse.getFixedShiftList().add(officeShiftInfo.copy());
            }
        }


        List<FixedShiftArrangement> toRemove = fixedShiftArrangementList.stream()
                                    .filter(c -> !canManageOfficesMap.containsKey(c.getOfficeId()))
                .collect(Collectors.toList());
        fixedShiftArrangementList.removeAll(toRemove);

    }

    private void setShiftByDateList(ShiftArrangementResponse dataResponse, List<ShiftArrangementModel> logEntities) {

        List<ShiftArrangementModel> modelList = logEntities.stream()
                .filter(c -> !c.isFixed())
                .collect(Collectors.toList());

        for (ShiftArrangementModel entity : modelList) {
            ShiftArrangementResponse.OfficeShiftInfo officeShiftInfos = dataResponse.findOfficeShiftInfo(entity.getOfficeId(), false);

            if (officeShiftInfos != null) {
                ShiftArrangementResponse.ShiftDetail shiftDetail = officeShiftInfos.findShiftDetail(entity.getShiftId());

                if (shiftDetail != null) {
                    ShiftArrangementResponse.ShiftByDayDetail shiftByDayDetail = shiftDetail.findShiftByDateDetail(entity.getArrangeDate(), false);

                    if (shiftByDayDetail != null) {
                        String username = entity.getUsername();
                        if (dataResponse.getCanManageEmployeesMap().containsKey(username)) {
                            shiftByDayDetail.getEmployeeList().add(new ShiftArrangementResponse.EmployeeShiftInfo(
                                    username, entity.getArrangementId(), ShiftArrangementTypeEnum.DAY.getValue()));
                        }
                    }
                }
            }
        }
    }

    private void setFixedShiftList(ShiftArrangementResponse dataResponse, List<FixedShiftArrangement> shiftArrangementList) {

        for (FixedShiftArrangement entity : shiftArrangementList) {
            ShiftArrangementResponse.OfficeShiftInfo officeShiftInfos = dataResponse.findOfficeShiftInfo(entity.getOfficeId(), true);
            if (officeShiftInfos != null) {
                ShiftArrangementResponse.ShiftDetail shiftDetail = officeShiftInfos.findShiftDetail(entity.getShiftId());

                if (shiftDetail != null) {
                    ShiftArrangementResponse.ShiftByDayDetail shiftByDayDetail =
                            shiftDetail.findShiftByDateDetail(entity.getWeekDay() + "", true);

                    if (shiftByDayDetail != null) {
                        String username = entity.getUsername();
                        if (dataResponse.getCanManageEmployeesMap().containsKey(username)) {
                            shiftByDayDetail.getEmployeeList().add(new ShiftArrangementResponse.EmployeeShiftInfo(
                                    username,entity.getId() + "", ShiftArrangementTypeEnum.FIXED.getValue()));
                        }
                    }
                }
            }
        }
    }

    private void setCanManageEmployees(ShiftArrangementResponse dataResponse, String companyId, String actor) {
        List<EmployeeInfo> employeeInfoList = permissionRepo.getCanManageEmployees(companyId, actor);

        Map<String, EmployeeInfo> map = new HashMap<>();
        employeeInfoList.forEach(c -> {
            map.put(c.getUsername(), c);
        });

        dataResponse.setCanManageEmployees(employeeInfoList);
        dataResponse.setCanManageEmployeesMap(map);
    }

    @DeleteMapping("/shifts/{type}/{arrangementId}")
    public BaseResponse deleteShiftArrangement(@PathVariable int type,
                                               @PathVariable String arrangementId) {
        BaseResponse response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        response.setReturnMessage("Xóa lịch xếp ca thành công");
        try {
            if (type != 1 && type != 2) {
                response = new BaseResponse(ReturnCodeEnum.PARAM_DATA_INVALID);
                response.setReturnMessage("Loại ca không hợp lệ");
                return response;
            }

            if (type == 2) {
                String yyyyMMdd = arrangementId.substring(0, 8);
                String today    = DateTimeUtil.parseTimestampToString(System.currentTimeMillis(), "yyyyMMdd");

                if (Integer.parseInt(yyyyMMdd) < Integer.parseInt(today)) {
                    response = new BaseResponse(ReturnCodeEnum.PARAM_DATA_INVALID);
                    response.setReturnMessage("Không thể xóa các ca làm việc đã xếp trước ngày hôm nay");
                    return response;
                }
            }

            response = shiftArrangementService.deleteShiftArrangement(type, arrangementId);
        } catch (Exception e) {
            log.error("[deleteShiftArrangement] [{} - {}] ex", type, arrangementId, e);
            response = new BaseResponse<>(e);
        }
        return response;
    }

    @PostMapping("/shifts")
    public BaseResponse insertShiftArrangement(@RequestBody ShiftArrangementRequest request) {
        BaseResponse response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        response.setReturnMessage("Xếp ca thành công");
        try {
            String error = request.isValid();
            if (!error.isEmpty()) {
                response = new BaseResponse<>(ReturnCodeEnum.PARAM_DATA_INVALID);
                response.setReturnMessage(error);
                return response;
            }

            if (!permissionRepo.canManageEmployee(request.getCompanyId(), request.getActor(), request.getUsername())) {
                response = new BaseResponse(ReturnCodeEnum.PARAM_DATA_INVALID);
                response.setReturnMessage(String.format("Nhân viên %s không thuộc quyền quản lý của bạn", request.getUsername()));
                return response;
            }

            response = shiftArrangementService.insertShiftArrangement(request);
        } catch (Exception e) {
            log.error("[insertShiftArrangement] [{}] ex", StringUtil.toJsonString(request), e);
            response = new BaseResponse<>(e);
        }
        return response;
    }
}
