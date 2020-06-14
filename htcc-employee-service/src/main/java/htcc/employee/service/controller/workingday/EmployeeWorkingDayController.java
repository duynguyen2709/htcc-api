package htcc.employee.service.controller.workingday;

import htcc.common.constant.ReturnCodeEnum;
import htcc.common.constant.WorkingDayTypeEnum;
import htcc.common.entity.base.BaseResponse;
import htcc.common.entity.jpa.Office;
import htcc.common.entity.workingday.WorkingDay;
import htcc.common.entity.workingday.WorkingDayResponse;
import htcc.common.util.DateTimeUtil;
import htcc.employee.service.service.jpa.OfficeService;
import htcc.employee.service.service.jpa.WorkingDayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
import java.util.stream.Collectors;

@Api(tags = "API xem ngày làm việc (CỦA NHÂN VIÊN)")
@RestController
@Log4j2
public class EmployeeWorkingDayController {

    @Autowired
    private WorkingDayService workingDayService;

    @Autowired
    private OfficeService officeService;

    @ApiOperation(value = "Lấy thông tin ngày làm việc trong 1 tháng", response = WorkingDayResponse.class)
    @GetMapping("/workingday/{companyId}/{yyyyMM}")
    public BaseResponse getWorkingDayInfo(@ApiParam(name = "companyId", value = "[Path] Mã công ty", defaultValue = "HCMUS", required = true)
                                              @PathVariable String companyId,
                                              @ApiParam(name = "yyyyMM", value = "[Path] Tháng", defaultValue = "202006", required = true)
                                              @PathVariable String yyyyMM) {
        BaseResponse<WorkingDayResponse> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        try {
            if (!DateTimeUtil.isRightFormat(yyyyMM, "yyyyMM")) {
                response = new BaseResponse<>(ReturnCodeEnum.DATE_WRONG_FORMAT,
                        String.format("Tháng %s không phù hợp định dạng yyyyMM", yyyyMM));
                return response;
            }

            // do buz
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            LocalDate startDate = LocalDate.parse(yyyyMM + "01", formatter);
            LocalDate endDate   = startDate.plusDays(startDate.lengthOfMonth());

            WorkingDayResponse dataResponse = new WorkingDayResponse();
            dataResponse.setDetailMap(new HashMap<>());

            List<Office> officeList = officeService.findByCompanyId(companyId);
            for (Office office : officeList) {
                List<WorkingDay> workingDays = workingDayService.findByCompanyIdAndOfficeId(companyId, office.getOfficeId());

                Map<String, WorkingDayResponse.WorkingDayDetail> detailMap = new HashMap<>();

                List<WorkingDay> normalDays = workingDays
                        .stream()
                        .filter(c -> c.getType() == WorkingDayTypeEnum.NORMAL.getValue())
                        .collect(Collectors.toList());

                for (LocalDate date = startDate; date.isBefore(endDate); date = date.plusDays(1)) {
                    String yyyyMMdd = date.format(formatter);
                    int weekDayInt = DateTimeUtil.getWeekDayInt(yyyyMMdd);

                    List<WorkingDay> specialDays = workingDays
                            .stream()
                            .filter(c -> c.getType() == WorkingDayTypeEnum.SPECIAL.getValue() &&
                                    c.getDate().equals(yyyyMMdd))
                            .collect(Collectors.toList());

                    WorkingDayResponse.WorkingDayDetail workingDayDetail = new WorkingDayResponse.WorkingDayDetail();
                    workingDayDetail.setDate(yyyyMMdd);

                    for (WorkingDay normal : normalDays) {
                        if (weekDayInt == normal.getWeekDay()) {
                            workingDayDetail.setSession(normal.getSession());
                            workingDayDetail.setIsWorking(normal.getIsWorking());
                            workingDayDetail.setExtraInfo(normal.getExtraInfo());
                        }
                    }

                    for (WorkingDay special : specialDays) {
                        workingDayDetail.setSession(special.getSession());
                        workingDayDetail.setIsWorking(special.getIsWorking());
                        workingDayDetail.setExtraInfo(special.getExtraInfo());
                    }

                    detailMap.put(yyyyMMdd, workingDayDetail);
                }

                // put to response
                String key = String.format("%s (%s)", office.getOfficeName(), office.getOfficeId());
                List<WorkingDayResponse.WorkingDayDetail> value = new ArrayList<>(detailMap.values());
                value.sort(WorkingDayResponse.WorkingDayDetail.getComparator());
                dataResponse.getDetailMap().put(key, value);
            }

            response.setData(dataResponse);

        } catch (Exception e) {
            log.error("[getWorkingDayInfo] [{} - {}] ex", companyId, yyyyMM, e);
            response = new BaseResponse<>(e);
        }
        return response;
    }
}
