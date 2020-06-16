package htcc.employee.service.controller.shifttime;

import htcc.common.constant.ReturnCodeEnum;
import htcc.common.entity.base.BaseResponse;
import htcc.common.entity.jpa.Office;
import htcc.common.entity.shift.ShiftTime;
import htcc.common.util.StringUtil;
import htcc.employee.service.config.DbStaticConfigMap;
import htcc.employee.service.service.jpa.OfficeService;
import htcc.employee.service.service.jpa.ShiftTimeService;
import htcc.employee.service.service.shiftarrangement.ShiftArrangementService;
import io.swagger.annotations.Api;
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

@Api(tags = "API config ca làm việc (CỦA QUẢN LÝ)",
     description = "API get/config ca làm việc của chi nhánh")
@RestController
@Log4j2
public class ShiftTimeController {

    @Autowired
    private ShiftTimeService shiftTimeService;

    @Autowired
    private OfficeService officeService;

    @Autowired
    private ShiftArrangementService shiftArrangementService;

    @ApiOperation(value = "Lấy thông tin ca làm việc", response = ShiftTime.class)
    @GetMapping("/shifttime/{companyId}/{officeId}")
    public BaseResponse getShiftTimeInfo(@ApiParam(name = "companyId", value = "[Path] Mã công ty", defaultValue = "VNG", required = true)
                                              @PathVariable String companyId,
                                              @ApiParam(name = "officeId", value = "[Path] Mã chi nhánh", defaultValue = "CAMPUS", required = true)
                                              @PathVariable String officeId) {
        BaseResponse<List<ShiftTime>> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        try {
            List<ShiftTime> shiftTimes = shiftTimeService.findByCompanyIdAndOfficeId(companyId, officeId);
            response.setData(shiftTimes);
        } catch (Exception e) {
            log.error("[getShiftTimeInfo] [{} - {}] ex", companyId, officeId, e);
            response = new BaseResponse<>(e);
        }
        return response;
    }

    @GetMapping("/shifttime/{companyId}")
    public BaseResponse getShiftTimeMap(@PathVariable String companyId) {
        BaseResponse response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        try {
            List<ShiftTime> shiftTimes = shiftTimeService.findByCompanyId(companyId);

            Map<String, List<ShiftTime>> dataResponse = new HashMap<>();
            for (ShiftTime shift : shiftTimes) {
                String officeId = shift.getOfficeId();
                if (!dataResponse.containsKey(officeId)) {
                    dataResponse.put(officeId, new ArrayList<>());
                }

                dataResponse.get(officeId).add(shift);
            }

            response.setData(dataResponse);
        } catch (Exception e) {
            log.error("[getShiftTimeMap] [{}] ex", companyId, e);
            response = new BaseResponse<>(e);
        }
        return response;
    }


    @ApiOperation(value = "Thêm mới ca làm việc", response = ShiftTime.class)
    @PostMapping("/shifttime")
    public BaseResponse createShiftTime(@ApiParam(value = "[Body] Thông tin ca làm việc mới cần tạo")
                                                   @RequestBody ShiftTime request) {
        BaseResponse<ShiftTime> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        try {
            String error = request.isValid();
            if (!error.isEmpty()){
                response = new BaseResponse<>(ReturnCodeEnum.PARAM_DATA_INVALID);
                response.setReturnMessage(error);
                return response;
            }

            request.calculateSession();
            response.setData(shiftTimeService.create(request));

        } catch (Exception e){
            log.error("[createShiftTime] [{}] ex", StringUtil.toJsonString(request), e);
            response = new BaseResponse<>(e);
        }

        return response;
    }




    @ApiOperation(value = "Cập nhật thông tin ca làm việc", response = ShiftTime.class)
    @PutMapping("/shifttime/{companyId}/{officeId}/{shiftId}")
    public BaseResponse updateShiftTime(@ApiParam(name = "companyId", value = "[Path] Mã công ty",
                                                  defaultValue = "VNG", required = true)
                                            @PathVariable String companyId,
                                        @ApiParam(name = "officeId", value = "[Path] Mã chi nhánh",
                                                  defaultValue = "CAMPUS", required = true)
                                            @PathVariable String officeId,
                                        @ApiParam(name = "shiftId", value = "[Path] Id ca làm việc",
                                                  defaultValue = "1", required = true)
                                             @PathVariable String shiftId,
                                         @ApiParam(value = "[Body] Thông tin ca làm việc mới cần cập nhật")
                                         @RequestBody ShiftTime request) {
        BaseResponse<ShiftTime> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        try {
            request.setShiftId(shiftId);
            request.setCompanyId(companyId);
            request.setOfficeId(officeId);

            String error = request.isValid();
            if (!error.isEmpty()){
                response = new BaseResponse<>(ReturnCodeEnum.PARAM_DATA_INVALID);
                response.setReturnMessage(error);
                return response;
            }

            ShiftTime shift = shiftTimeService.findById(new ShiftTime.Key(companyId, officeId, shiftId));
            if (shift == null){
                response = new BaseResponse<>(ReturnCodeEnum.DATA_NOT_FOUND);
                response.setReturnMessage("Không tìm thấy thông tin ca làm việc có id " + shiftId);
                return response;
            }

            request.setStartTime(shift.getStartTime());
            request.setEndTime(shift.getEndTime());
            request.setSession(shift.getSession());

            shift = shiftTimeService.update(request);

            response.setData(shift);
        } catch (Exception e){
            log.error("[updateShiftTime] [{}] ex", StringUtil.toJsonString(request), e);
            response = new BaseResponse<>(e);
        }

        return response;
    }




    @ApiOperation(value = "Xóa thông tin ca làm việc", response = BaseResponse.class)
    @DeleteMapping("/shifttime/{companyId}/{officeId}/{shiftId}")
    public BaseResponse deleteShiftTime(@ApiParam(name = "companyId", value = "[Path] Mã công ty",
                                                  defaultValue = "VNG", required = true)
                                            @PathVariable String companyId,
                                        @ApiParam(name = "officeId", value = "[Path] Mã chi nhánh",
                                                  defaultValue = "CAMPUS", required = true)
                                            @PathVariable String officeId,
                                        @ApiParam(name = "shiftId", value = "[Path] Id ca làm việc",
                                                  defaultValue = "1", required = true)
                                            @PathVariable String shiftId) {
        BaseResponse response = new BaseResponse(ReturnCodeEnum.SUCCESS);
        ShiftTime shift = null;
        try {
            ShiftTime.Key key = new ShiftTime.Key(companyId, officeId, shiftId);
            shift = shiftTimeService.findById(key);
            if (shift == null) {
                response = new BaseResponse<>(ReturnCodeEnum.DATA_NOT_FOUND);
                response.setReturnMessage("Không tìm thấy thông tin ca làm việc");
                return response;
            }

            shiftTimeService.delete(key);
        } catch (Exception e){
            log.error("[deleteShiftTime] [{} - {} - {}] ex", companyId, officeId, shiftId, e);
            response = new BaseResponse<>(e);
        } finally {
            if (response.getReturnCode() == ReturnCodeEnum.SUCCESS.getValue()) {
                shiftArrangementService.onEventDeleteShiftTime(shift);
            }
        }

        return response;
    }




    @ApiOperation(value = "Cập nhật thông tin ca làm việc giống trụ sở chính", response = BaseResponse.class)
    @PostMapping("/shifttime/default/{companyId}/{officeId}")
    public BaseResponse applySameAsHeadquarter(@ApiParam(name = "companyId", value = "[Path] Mã công ty", defaultValue = "VNG", required = true)
                                                   @PathVariable String companyId,
                                               @ApiParam(name = "officeId", value = "[Path] Mã chi nhánh", defaultValue = "CAMPUS", required = true)
                                                   @PathVariable String officeId) {
        BaseResponse response = new BaseResponse(ReturnCodeEnum.SUCCESS);
        List<ShiftTime> shiftTimes = null;
        try {

            Office headquarter = officeService.findHeadquarter(companyId);
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

            List<ShiftTime> headQuarterShiftTimes = shiftTimeService.findByCompanyIdAndOfficeId(companyId, headquarter.getOfficeId());
            if (headQuarterShiftTimes.isEmpty()){
                response = new BaseResponse(ReturnCodeEnum.DATA_NOT_FOUND);
                response.setReturnMessage("Trụ sở chính chưa cấu hình ca làm việc. Vui lòng kiểm tra lại.");
                return response;
            }

            shiftTimes = shiftTimeService.findByCompanyIdAndOfficeId(companyId, officeId);

            // delete all in db to insert new data
            shiftTimeService.batchDelete(shiftTimes);

            List<ShiftTime> cloneList = headQuarterShiftTimes.stream()
                    .map(ShiftTime::copy).collect(Collectors.toList());
            cloneList.forEach(c -> c.setOfficeId(officeId));

            cloneList = shiftTimeService.batchInsert(cloneList);

            return response;

        } catch (Exception e){
            log.error("[applySameAsHeadquarter] [{} - {}] ex", companyId, officeId, e);
            response = new BaseResponse(e);
        } finally {
            if (response.getReturnCode() == ReturnCodeEnum.SUCCESS.getValue() && shiftTimes != null) {
                for (ShiftTime shift : shiftTimes) {
                    shiftArrangementService.onEventDeleteShiftTime(shift);
                }
            }
        }

        return response;
    }
}
