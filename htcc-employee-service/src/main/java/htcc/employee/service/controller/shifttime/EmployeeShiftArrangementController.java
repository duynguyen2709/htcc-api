package htcc.employee.service.controller.shifttime;

import htcc.common.constant.ReturnCodeEnum;
import htcc.common.entity.base.BaseResponse;
import htcc.common.entity.jpa.Office;
import htcc.common.entity.shift.EmployeeShiftArrangementResponse;
import htcc.common.entity.shift.FixedShiftArrangement;
import htcc.common.entity.shift.ShiftArrangementModel;
import htcc.common.entity.shift.ShiftTime;
import htcc.common.util.DateTimeUtil;
import htcc.employee.service.repository.PermissionRepository;
import htcc.employee.service.service.jpa.FixedShiftArrangementService;
import htcc.employee.service.service.jpa.OfficeService;
import htcc.employee.service.service.jpa.ShiftTimeService;
import htcc.employee.service.service.shiftarrangement.ShiftArrangementService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Log4j2
public class EmployeeShiftArrangementController {

    @Autowired
    private ShiftArrangementService shiftArrangementService;

    @Autowired
    private ShiftTimeService shiftTimeService;

    @Autowired
    private FixedShiftArrangementService fixedShiftArrangementService;

    @Autowired
    private OfficeService officeService;

    @Autowired
    private PermissionRepository permissionRepo;

    @GetMapping("/shifts/personal/{companyId}/{username}/{yyyyMM}")
    public BaseResponse getShiftTimeInfo(@PathVariable String companyId,
                                         @PathVariable String username,
                                         @PathVariable String yyyyMM) {
        BaseResponse<List<EmployeeShiftArrangementResponse>> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        try {
            if (!DateTimeUtil.isRightFormat(yyyyMM, "yyyyMM")) {
                response = new BaseResponse<>(ReturnCodeEnum.DATE_WRONG_FORMAT,
                        String.format("Tháng %s không phù hợp định dạng yyyyMM", yyyyMM));
                return response;
            }

            // do buz
            DateTimeFormatter formatter   = DateTimeFormatter.ofPattern("yyyyMMdd");
            LocalDate startDate = LocalDate.parse(yyyyMM + "01", formatter);
            LocalDate endDate = startDate.plusDays(startDate.lengthOfMonth());

            List<FixedShiftArrangement> fixedShiftArrangementList = fixedShiftArrangementService.findByCompanyIdAndUsername(companyId, username);
            Map<String, ShiftTime> cacheShiftTimeMap = new HashMap<>();
            Map<String, Office> cacheOfficeMap = new HashMap<>();
            Map<String, EmployeeShiftArrangementResponse> dataResponseMap = new HashMap<>();

            for (LocalDate date = startDate; date.isBefore(endDate); date = date.plusDays(1)) {
                String yyyyMMdd   = date.format(formatter);
                int    weekDayInt = DateTimeUtil.getWeekDayInt(yyyyMMdd);

                List<ShiftArrangementModel> logEntities = shiftArrangementService.getShiftArrangementListByEmployee(companyId, username, yyyyMMdd);
                if (logEntities == null) {
                    throw new Exception("shiftArrangementService.getShiftArrangementListByEmployee return null");
                }

                EmployeeShiftArrangementResponse entity = new EmployeeShiftArrangementResponse();
                entity.setDate(yyyyMMdd);

                List<EmployeeShiftArrangementResponse.MiniShiftDetail> miniShiftList = new ArrayList<>();
                entity.setShiftList(miniShiftList);

                for (FixedShiftArrangement fixedShift : fixedShiftArrangementList) {
                    if (fixedShift.getWeekDay() != weekDayInt) {
                        continue;
                    }

                    // TODO : DELETE OFFICE/ SHIFT -> DELETE FIXEDSHIFT
                    String shiftTimeKey = String.format("%s_%s_%s", fixedShift.getCompanyId(), fixedShift.getOfficeId(), fixedShift.getShiftId());
                    ShiftTime shiftTime = null;
                    if (!cacheShiftTimeMap.containsKey(shiftTimeKey)) {
                        ShiftTime shift = shiftTimeService.findById(new ShiftTime.Key(fixedShift.getCompanyId(), fixedShift.getOfficeId(), fixedShift.getShiftId()));
                        if (shift == null) {
                            throw new Exception("shiftTimeService.findById return null");
                        }
                        cacheShiftTimeMap.put(shiftTimeKey, shift);
                    }

                    shiftTime = cacheShiftTimeMap.get(shiftTimeKey);

                    Office office = null;
                    if (!cacheOfficeMap.containsKey(fixedShift.getOfficeId())) {
                        Office office1 = officeService.findById(new Office.Key(fixedShift.getCompanyId(), fixedShift.getOfficeId()));
                        if (office1 == null) {
                            throw new Exception("officeService.findById return null");
                        }
                        cacheOfficeMap.put(fixedShift.getOfficeId(), office1);
                    }

                    office = cacheOfficeMap.get(fixedShift.getOfficeId());

                    boolean isSkipped = false;
                    for (ShiftArrangementModel model : logEntities) {
                        if (DateTimeUtil.isConflictTime(shiftTime.getStartTime(), shiftTime.getEndTime(),
                                model.getShiftTime().getStartTime(), model.getShiftTime().getEndTime())) {
                            isSkipped = true;
                            break;
                        }
                    }

                    if (isSkipped) {
                        continue;
                    }

                    EmployeeShiftArrangementResponse.MiniShiftDetail detail = new EmployeeShiftArrangementResponse.MiniShiftDetail();
                    detail.setOfficeId(String.format("%s (%s)", office.getOfficeName(), office.getOfficeId()));
                    detail.setShiftId(String.format("%s (%s)", shiftTime.getShiftName(), shiftTime.getShiftId()));
                    detail.setShiftTime(String.format("%s - %s", shiftTime.getStartTime(), shiftTime.getEndTime()));

                    miniShiftList.add(detail);
                }

                for (ShiftArrangementModel model : logEntities) {
                    ShiftTime shiftTime = model.getShiftTime();
                    Office office = null;
                    if (!cacheOfficeMap.containsKey(model.getOfficeId())) {
                        Office office1 = officeService.findById(new Office.Key(model.getCompanyId(), model.getOfficeId()));
                        if (office1 == null) {
                            throw new Exception("officeService.findById return null");
                        }
                        cacheOfficeMap.put(model.getOfficeId(), office1);
                    }

                    office = cacheOfficeMap.get(model.getOfficeId());

                    EmployeeShiftArrangementResponse.MiniShiftDetail detail = new EmployeeShiftArrangementResponse.MiniShiftDetail();
                    detail.setOfficeId(String.format("%s (%s)", office.getOfficeName(), office.getOfficeId()));
                    detail.setShiftId(String.format("%s (%s)", shiftTime.getShiftName(), shiftTime.getShiftId()));
                    detail.setShiftTime(String.format("%s - %s", shiftTime.getStartTime(), shiftTime.getEndTime()));

                    miniShiftList.add(detail);
                }

                //
                dataResponseMap.put(yyyyMMdd, entity);
            }

            List<EmployeeShiftArrangementResponse> dataResponse = new ArrayList<>(dataResponseMap.values());
            dataResponse.sort(EmployeeShiftArrangementResponse.getComparator());
            response.setData(dataResponse);
        } catch (Exception e) {
            log.error("[getShiftTimeInfo] [{} - {} - {}] ex", companyId, username, yyyyMM, e);
            response = new BaseResponse<>(e);
        }
        return response;
    }
}
