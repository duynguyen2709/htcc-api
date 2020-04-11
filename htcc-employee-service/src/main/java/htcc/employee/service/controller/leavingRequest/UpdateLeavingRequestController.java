package htcc.employee.service.controller.leavingRequest;

import htcc.common.constant.ReturnCodeEnum;
import htcc.common.entity.base.BaseResponse;
import htcc.common.entity.leavingrequest.LeavingRequestResponse;
import htcc.common.entity.leavingrequest.UpdateLeavingRequestStatusModel;
import htcc.common.util.DateTimeUtil;
import htcc.common.util.StringUtil;
import htcc.employee.service.service.LeavingRequestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "API xử lý đơn xin nghỉ phép (CỦA QUẢN LÝ)",
     description = "API để phê duyệt đơn xin nghỉ phép")
@RestController
@Log4j2
public class UpdateLeavingRequestController {

    @Autowired
    private LeavingRequestService service;



    @ApiOperation(value = "Lấy danh sách đơn xin nghỉ phép", response = LeavingRequestResponse.class)
    @GetMapping("/leaving/{companyId}/{month}")
    public BaseResponse getListLeavingRequest(@ApiParam(value = "[Path] Mã công ty", required = true)
                                         @PathVariable String companyId,
                                         @ApiParam(value = "[Path] Tháng (yyyyMM)", required = true)
                                         @PathVariable String month) {
        BaseResponse response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        try {
            String yyyyMM = StringUtil.valueOf(month);
            if (DateTimeUtil.isRightFormat(yyyyMM, "yyyyMM") == false) {
                return new BaseResponse<>(ReturnCodeEnum.DATE_WRONG_FORMAT, String.format("Tháng %s không phù hợp định dạng yyyyMM", month));
            }

            List<LeavingRequestResponse> list = service.getLeavingRequestLogByCompanyId(companyId, yyyyMM);
            response.data = list;

        } catch (Exception e) {
            log.error(String.format("getListLeavingRequest [%s-%s] ex", companyId,month), e);
            response = new BaseResponse<>(e);
        }
        return response;
    }




    @ApiOperation(value = "Cập nhật trạng thái khiếu nại", response = BaseResponse.class)
    @PutMapping("/leaving/status")
    public BaseResponse updateLeavingRequestStatus(@ApiParam(value = "[Body] Trạng thái mới cần update", required = true)
                                                  @RequestBody UpdateLeavingRequestStatusModel request) {
        BaseResponse response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        try {
            String error = request.isValid();
            if (!StringUtil.isEmpty(error)) {
                response = new BaseResponse(ReturnCodeEnum.PARAM_DATA_INVALID);
                response.setReturnMessage(error);
                return response;
            }

            response = service.updateLeavingRequestStatus(request);
        } catch (Exception e) {
            log.error(String.format("updateLeavingRequestStatus [%s] ex", StringUtil.toJsonString(request)), e);
            response = new BaseResponse<>(e);
        }
        return response;
    }

}
