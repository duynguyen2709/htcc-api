package htcc.admin.service.controller;

import htcc.admin.service.service.ComplaintService;
import htcc.common.constant.ComplaintStatusEnum;
import htcc.common.constant.ReturnCodeEnum;
import htcc.common.entity.base.BaseResponse;
import htcc.common.entity.complaint.ComplaintResponse;
import htcc.common.entity.complaint.UpdateComplaintStatusModel;
import htcc.common.util.DateTimeUtil;
import htcc.common.util.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "API để xử lý phản hồi/ khiếu nại")
@RestController
@Log4j2
public class UpdateComplaintController {

    @Autowired
    private ComplaintService complaintService;



    @ApiOperation(value = "Lấy danh sách khiếu nại", response = ComplaintResponse.class)
    @GetMapping("/complaint/{month}")
    public BaseResponse getListComplaint(@ApiParam(value = "[Path] Tháng (yyyyMM)", required = true)
                                         @PathVariable String month) {
        BaseResponse<List<ComplaintResponse>> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        try {
            String yyyyMM = StringUtil.valueOf(month);
            if (!DateTimeUtil.isRightFormat(yyyyMM, "yyyyMM")) {
                return new BaseResponse<>(ReturnCodeEnum.DATE_WRONG_FORMAT, String.format("Tháng %s không phù hợp định dạng yyyyMM", month));
            }

            List<ComplaintResponse> list = complaintService.getListComplaintLogByMonth(yyyyMM);

            response.setData(list);

        } catch (Exception e) {
            log.error(String.format("getListComplaint [%s] ex", month), e);
            response = new BaseResponse<>(e);
        }
        return response;
    }




    @ApiOperation(value = "Cập nhật trạng thái khiếu nại", response = BaseResponse.class)
    @PutMapping("/complaint/status")
    public BaseResponse updateComplaintStatus(@ApiParam(value = "[Body] Trạng thái mới cần update", required = true)
                                                  @RequestBody UpdateComplaintStatusModel request) {
        BaseResponse response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        try {
            if (request.getStatus() != ComplaintStatusEnum.DONE.getValue() &&
                    request.getStatus() != ComplaintStatusEnum.REJECTED.getValue()) {
                return new BaseResponse(ReturnCodeEnum.PARAM_DATA_INVALID, String.format("Trạng thái %s không hợp lệ", request.getStatus()));
            }

            if (!DateTimeUtil.isRightFormat(request.getYyyyMM(), "yyyyMM")) {
                return new BaseResponse(ReturnCodeEnum.DATE_WRONG_FORMAT, String.format("Tháng %s không phù hợp định dạng yyyyMM", request.getYyyyMM()));
            }

            response = complaintService.updateComplaintStatus(request);
        } catch (Exception e) {
            log.error(String.format("updateComplaintStatus [%s] ex", StringUtil.toJsonString(request)), e);
            response = new BaseResponse<>(e);
        }
        return response;
    }
}
