package htcc.employee.service.controller.statistics;

import htcc.common.constant.ReturnCodeEnum;
import htcc.common.entity.base.BaseResponse;
import htcc.common.util.DateTimeUtil;
import io.swagger.annotations.Api;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "API thống kê", description = "API thống kê nhân viên")
@RestController
@Log4j2
public class EmployeeStatisticController {

    @GetMapping("/statistic")
    public BaseResponse getStatistics(@RequestParam String companyId,
                                      @RequestParam String username,
                                      @RequestParam String dateFrom,
                                      @RequestParam String dateTo) {
        BaseResponse response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        try {
            response = validateDate(dateFrom, dateTo);
            if (response.getReturnCode() != ReturnCodeEnum.SUCCESS.getValue()) {
                return response;
            }



        } catch (Exception e) {
            log.error("[getStatistics] [{}-{}-{}-{}] ex",
                    companyId, username, dateFrom, dateTo, e);
        }
        return response;
    }

    private BaseResponse validateDate(String dateFrom, String dateTo) {
        BaseResponse response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        if (!DateTimeUtil.isRightFormat(dateFrom, "yyyyMMdd")) {
            response = new BaseResponse(ReturnCodeEnum.PARAM_DATA_INVALID);
            response.setReturnMessage(String.format("Ngày bắt đầu %s không phù hợp định dạng", dateFrom));
            return response;
        }

        if (!DateTimeUtil.isRightFormat(dateTo, "yyyyMMdd")) {
            response = new BaseResponse(ReturnCodeEnum.PARAM_DATA_INVALID);
            response.setReturnMessage(String.format("Ngày kết thúc %s không phù hợp định dạng", dateTo));
            return response;
        }

        if (Long.parseLong(dateTo) < Long.parseLong(dateFrom)) {
            response = new BaseResponse(ReturnCodeEnum.PARAM_DATA_INVALID);
            response.setReturnMessage("Ngày kết thúc không được trước ngày bắt đầu");
            return response;
        }

        if (DateTimeUtil.calcDayDiff(dateFrom, dateTo, "yyyyMMdd") > 31) {
            response = new BaseResponse(ReturnCodeEnum.PARAM_DATA_INVALID);
            response.setReturnMessage("Khoảng cách tối đa là 1 tháng. Vui lòng chọn lại");
            return response;
        }
        return response;
    }
}
