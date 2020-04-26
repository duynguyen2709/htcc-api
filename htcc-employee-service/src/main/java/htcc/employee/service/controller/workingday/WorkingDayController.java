package htcc.employee.service.controller.workingday;

import htcc.common.constant.ReturnCodeEnum;
import htcc.common.constant.SessionEnum;
import htcc.common.constant.WorkingDayTypeEnum;
import htcc.common.entity.base.BaseResponse;
import htcc.common.entity.jpa.Office;
import htcc.common.entity.jpa.WorkingDay;
import htcc.common.entity.workingday.WorkingDayModel;
import htcc.common.util.DateTimeUtil;
import htcc.common.util.StringUtil;
import htcc.employee.service.config.DbStaticConfigMap;
import htcc.employee.service.service.jpa.WorkingDayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Api(tags = "API config ngày làm việc (CỦA QUẢN LÝ)",
     description = "API get/config lịch làm việc trong tuần")
@RestController
@Log4j2
public class WorkingDayController {

    @Autowired
    private WorkingDayService workingDayService;

    @ApiOperation(value = "Lấy thông tin ngày làm việc trong 1 năm", response = WorkingDayModel.class)
    @GetMapping("/workingday/{companyId}/{officeId}/{yyyy}")
    public BaseResponse getWorkingDayInfo(@ApiParam(name = "companyId", value = "[Path] Mã công ty", defaultValue = "VNG", required = true)
                                              @PathVariable String companyId,
                                              @ApiParam(name = "officeId", value = "[Path] Mã chi nhánh", defaultValue = "CAMPUS", required = true)
                                              @PathVariable String officeId,
                                              @ApiParam(name = "yyyy", value = "[Path] Năm", defaultValue = "2020", required = true)
                                              @PathVariable String yyyy) {
        BaseResponse<WorkingDayModel> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        try {
            if (!DateTimeUtil.isRightFormat(yyyy, "yyyy")) {
                response = new BaseResponse<>(ReturnCodeEnum.DATE_WRONG_FORMAT,
                        String.format("Năm %s không phù hợp định dạng yyyy", yyyy));
                return response;
            }

            List<WorkingDay> workingDays = workingDayService.findByCompanyIdAndOfficeId(companyId, officeId);

            workingDays = workingDays.stream().filter(d ->
                            (d.getType() == WorkingDayTypeEnum.SPECIAL.getValue() && d.getDate().substring(0, 4).equals(yyyy)
                            || d.getType() == WorkingDayTypeEnum.NORMAL.getValue()))
                    .collect(Collectors.toList());

            WorkingDayModel dataResponse = new WorkingDayModel();
            dataResponse.setCompanyId(companyId);
            dataResponse.setOfficeId(officeId);
            dataResponse.setNormalDays(new ArrayList<>());
            dataResponse.setSpecialDays(new ArrayList<>());

            for (WorkingDay day : workingDays) {
                if (day.getType() == WorkingDayTypeEnum.NORMAL.getValue()){
                    dataResponse.getNormalDays().add(day);
                } else {
                    dataResponse.getSpecialDays().add(day);
                }
            }
            response.setData(dataResponse);

        } catch (Exception e) {
            log.error("[getWorkingDayInfo] [{} - {} - {}] ex", companyId, officeId, yyyy, e);
            response = new BaseResponse<>(e);
        }
        return response;
    }




    @ApiOperation(value = "Thêm mới ngày làm việc/ ngày nghỉ", response = WorkingDay.class)
    @PostMapping("/workingday")
    public BaseResponse createWorkingDay(@ApiParam(value = "[Body] Thông tin ngày làm việc mới cần tạo")
                                                   @RequestBody WorkingDay request) {
        BaseResponse<WorkingDay> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        try {
            request.setId(0);
            String error = request.isValid();
            if (!error.isEmpty()){
                response = new BaseResponse<>(ReturnCodeEnum.PARAM_DATA_INVALID);
                response.setReturnMessage(error);
                return response;
            }

            error = validateDetailDate(request);
            if (!error.isEmpty()){
                response = new BaseResponse<>(ReturnCodeEnum.PARAM_DATA_INVALID);
                response.setReturnMessage(error);
                return response;
            }

            response.setData(workingDayService.create(request));

        } catch (Exception e){
            log.error("[createWorkingDay] [{}] ex", StringUtil.toJsonString(request), e);
            response = new BaseResponse<>(e);
        }

        return response;
    }

    // handle collision/ duplicate dates
    private String validateDetailDate(WorkingDay request) {
        if (request.getType() == WorkingDayTypeEnum.NORMAL.getValue()){
            List<WorkingDay> workingDays = workingDayService.findByCompanyIdAndOfficeId(request.getCompanyId(), request.getOfficeId())
                             .stream()
                             .filter(d -> d.getType() == WorkingDayTypeEnum.NORMAL.getValue()
                             && d.getWeekDay() == request.getWeekDay())
                             .collect(Collectors.toList());

            for (WorkingDay day : workingDays) {
                // skip itself
                if (day.getId() == request.getId()){
                    continue;
                }

                if (day.getSession() == SessionEnum.FULL_DAY.getValue()
                        || request.getSession() == SessionEnum.FULL_DAY.getValue()
                        || day.getSession() == request.getSession()){
                    return String.format("Ngày %s đã có lịch làm việc. Vui lòng kiểm tra lại.",
                            (day.getWeekDay() == 1) ? "Chủ nhật" : "Thứ " + day.getWeekDay());
                }
            }
        } else {
            List<WorkingDay> workingDays = workingDayService.findByCompanyIdAndOfficeId(request.getCompanyId(), request.getOfficeId())
                    .stream()
                    .filter(d -> d.getType() == WorkingDayTypeEnum.SPECIAL.getValue()
                            && d.getDate().equals(request.getDate()))
                    .collect(Collectors.toList());

            for (WorkingDay day : workingDays) {
                // skip itself
                if (day.getId() == request.getId()){
                    continue;
                }

                if (day.getSession() == SessionEnum.FULL_DAY.getValue()
                        || request.getSession() == SessionEnum.FULL_DAY.getValue()
                        || day.getSession() == request.getSession()){
                    return String.format("Ngày %s đã có lịch làm việc. Vui lòng kiểm tra lại.",
                            DateTimeUtil.convertToOtherFormat(day.getDate(), "yyyyMMdd", "dd-MM-yyyy"));
                }
            }
        }

        return StringUtil.EMPTY;
    }

    @ApiOperation(value = "Cập nhật thông tin ngày làm việc/ ngày nghỉ", response = WorkingDay.class)
    @PutMapping("/workingday/{id}")
    public BaseResponse updateWorkingDay(@ApiParam(name = "id", value = "[Path] Id định danh của ngày làm việc", defaultValue = "1", required = true)
                                             @PathVariable int id,
                                         @ApiParam(value = "[Body] Thông tin ngày làm việc mới cần cập nhật")
                                         @RequestBody WorkingDay request) {
        BaseResponse<WorkingDay> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        try {
            request.setId(id);
            String error = request.isValid();
            if (!error.isEmpty()){
                response = new BaseResponse<>(ReturnCodeEnum.PARAM_DATA_INVALID);
                response.setReturnMessage(error);
                return response;
            }

            error = validateDetailDate(request);
            if (!error.isEmpty()){
                response = new BaseResponse<>(ReturnCodeEnum.PARAM_DATA_INVALID);
                response.setReturnMessage(error);
                return response;
            }

            WorkingDay workingDay = workingDayService.findById(id);
            if (workingDay == null){
                response = new BaseResponse<>(ReturnCodeEnum.DATA_NOT_FOUND);
                response.setReturnMessage("Không tìm thấy thông tin ngày làm việc có id " + id);
                return response;
            }

            request.refillImmutableValue(workingDay);

            workingDay = workingDayService.update(request);

            response.setData(workingDay);
        } catch (Exception e){
            log.error("[updateWorkingDay] [{}] ex", StringUtil.toJsonString(request), e);
            response = new BaseResponse<>(e);
        }

        return response;
    }




    @ApiOperation(value = "Xóa thông tin ngày làm việc/ ngày nghỉ", response = BaseResponse.class)
    @DeleteMapping("/workingday/{id}")
    public BaseResponse deleteWorkingDay(@ApiParam(name = "id", value = "[Path] Id định danh của ngày làm việc", defaultValue = "1", required = true)
                                         @PathVariable int id) {
        BaseResponse response = new BaseResponse(ReturnCodeEnum.SUCCESS);
        try {
            WorkingDay workingDay = workingDayService.findById(id);
            if (workingDay == null) {
                response = new BaseResponse<>(ReturnCodeEnum.DATA_NOT_FOUND);
                response.setReturnMessage("Không tìm thấy thông tin ngày làm việc có id " + id);
                return response;
            }

            workingDayService.delete(id);
        } catch (Exception e){
            log.error("[deleteWorkingDay] [{} - {}] ex", id, e);
            response = new BaseResponse<>(e);
        }

        return response;
    }




    @ApiOperation(value = "Cập nhật thông tin ngày làm việc giống trụ sở chính", response = BaseResponse.class)
    @PostMapping("/workingday/default/{companyId}/{officeId}")
    public BaseResponse applySameAsHeadquarter(@ApiParam(name = "companyId", value = "[Path] Mã công ty", defaultValue = "VNG", required = true)
                                                   @PathVariable String companyId,
                                               @ApiParam(name = "officeId", value = "[Path] Mã chi nhánh", defaultValue = "CAMPUS", required = true)
                                                   @PathVariable String officeId) {
        BaseResponse response = new BaseResponse(ReturnCodeEnum.SUCCESS);
        try {

            Office headquarter = DbStaticConfigMap.findHeadquarter(companyId);
            if (headquarter == null){
                response = new BaseResponse(ReturnCodeEnum.DATA_NOT_FOUND);
                response.setReturnMessage("Công ty chưa cấu hình trụ sở chính. Vui lòng kiểm tra lại.");
                return response;
            }

            if (headquarter.getOfficeId().equals(officeId)){
                response = new BaseResponse(ReturnCodeEnum.PARAM_DATA_INVALID);
                response.setReturnMessage("Trụ sở hiện tại đang là trụ sở chính");
                return response;
            }

            List<WorkingDay> headQuarterWorkingDays = workingDayService.findByCompanyIdAndOfficeId(companyId, headquarter.getOfficeId());
            if (headQuarterWorkingDays.isEmpty()){
                response = new BaseResponse(ReturnCodeEnum.DATA_NOT_FOUND);
                response.setReturnMessage("Trụ sở chính chưa cấu hình ngày làm việc. Vui lòng kiểm tra lại.");
                return response;
            }

            List<WorkingDay> workingDays = workingDayService.findByCompanyIdAndOfficeId(companyId, officeId);

            // delete all in db to insert new data
            workingDayService.batchDelete(workingDays);

            List<WorkingDay> cloneList = headQuarterWorkingDays.stream()
                    .map(WorkingDay::copy).collect(Collectors.toList());
            cloneList.forEach(c -> c.setOfficeId(officeId));

            cloneList = workingDayService.batchInsert(cloneList);

            return response;

        } catch (Exception e){
            log.error("[applySameAsHeadquarter] [{} - {}] ex", companyId, officeId, e);
            response = new BaseResponse(e);
        }

        return response;
    }
}
