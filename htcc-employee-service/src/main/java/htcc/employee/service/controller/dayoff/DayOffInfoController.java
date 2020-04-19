package htcc.employee.service.controller.dayoff;

import htcc.common.constant.ReturnCodeEnum;
import htcc.common.entity.base.BaseResponse;
import htcc.common.entity.dayoff.CompanyDayOffInfo;
import htcc.common.util.StringUtil;
import htcc.employee.service.service.jpa.BuzConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;

@Api(tags = "API quản lý số ngày nghỉ phép và loại phép (CỦA QUẢN LÝ)",
     description = "API quản lý số ngày nghỉ phép và loại phép")
@RestController
@Log4j2
public class DayOffInfoController {

    @Autowired
    private BuzConfigService buzConfigService;


    @ApiOperation(value = "Lấy thông tin nghỉ phép", response = CompanyDayOffInfo.class)
    @GetMapping("/dayoff/{companyId}")
    public BaseResponse getDayOffInfo(@ApiParam(value = "[Path] Mã công ty", required = true)
                                         @PathVariable String companyId) {
        BaseResponse<CompanyDayOffInfo> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        try {
            CompanyDayOffInfo info = buzConfigService.getDayOffInfo(companyId);
            if (info == null) {
                response = new BaseResponse<>(ReturnCodeEnum.DATA_NOT_FOUND);
                response.setReturnMessage("Không tìm thấy công ty " + companyId);
                return response;
            }

            response.setData(info);

        } catch (Exception e) {
            log.error(String.format("getDayOffInfo [%s] ex", companyId), e);
            response = new BaseResponse<>(e);
        }
        return response;
    }




    @ApiOperation(value = "Cập nhật thông tin nghỉ phép", response = CompanyDayOffInfo.class)
    @PutMapping("/dayoff/{companyId}")
    public BaseResponse updateDayOffInfo(@ApiParam(value = "[Body] Trạng thái mới cần update", required = true)
                                         @RequestBody CompanyDayOffInfo request,
                                         @ApiParam(value = "[Path] Mã công ty", required = true)
                                         @PathVariable String companyId) {
        BaseResponse<CompanyDayOffInfo> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        try {
            String error = request.isValid();
            if (!StringUtil.isEmpty(error)) {
                response = new BaseResponse<>(ReturnCodeEnum.PARAM_DATA_INVALID);
                response.setReturnMessage(error);
                return response;
            }

            request.getDayOffByLevel().sort(new Comparator<CompanyDayOffInfo.DayOffByLevelEntity>() {
                @Override
                public int compare(CompanyDayOffInfo.DayOffByLevelEntity o1, CompanyDayOffInfo.DayOffByLevelEntity o2) {
                    return Float.compare(o1.getLevel(), o2.getTotalDayOff());
                }
            });

            CompanyDayOffInfo data = buzConfigService.updateDayOffInfo(companyId, request);
            response.setData(data);

        } catch (Exception e) {
            log.error(String.format("[updateDayOffInfo] company [%s], request [%s] ex", companyId, StringUtil.toJsonString(request)), e);
            response = new BaseResponse<>(e);
        }
        return response;
    }

}
