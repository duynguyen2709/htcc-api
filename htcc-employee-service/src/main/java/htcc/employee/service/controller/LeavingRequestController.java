package htcc.employee.service.controller;

import htcc.common.constant.ReturnCodeEnum;
import htcc.common.entity.base.BaseResponse;
import htcc.common.entity.leavingrequest.LeavingRequest;
import htcc.common.entity.leavingrequest.LeavingRequestInfo;
import htcc.common.util.DateTimeUtil;
import htcc.common.util.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

@Api(tags = "API Xin Nghỉ Phép",
     description = "API get/submit form xin nghỉ phép của nhân viên")
@RestController
@Log4j2
public class LeavingRequestController {

    @ApiOperation(value = "Lấy thông tin phép còn lại & đơn đã submit", response = LeavingRequestInfo.class)
    @GetMapping("/leaving/{companyId}/{username}/{yyyy}")
    public BaseResponse getLeavingRequestInfo(@PathVariable String companyId, @PathVariable String username, @PathVariable String yyyy){
        BaseResponse response = new BaseResponse(ReturnCodeEnum.SUCCESS);
        try {
            if (!DateTimeUtil.isRightFormat(yyyy, "yyyy")) {
                return new BaseResponse(ReturnCodeEnum.DATE_WRONG_FORMAT, String.format("Năm %s không phù hợp định dạng yyyy", yyyy));
            }

            // TODO : get leaving request info

        } catch (Exception e) {
            log.error("[getLeavingRequestInfo] [{} - {} - {}] ex", companyId, username, yyyy, e);
            return new BaseResponse(e);
        }
        return response;
    }




    @PostMapping("/leaving")
    public BaseResponse submitLeavingRequest(@RequestBody LeavingRequest request){
        BaseResponse response = new BaseResponse(ReturnCodeEnum.SUCCESS);
        response.setReturnMessage("Đơn nghỉ phép của bạn đã gửi thành công. Vui lòng chờ quản lý phê duyệt.");
        try {
            String error = request.isValid();
            if (!error.isEmpty()){
                response = new BaseResponse(ReturnCodeEnum.PARAM_DATA_INVALID);
                response.setReturnMessage(error);
                return response;
            }

            // TODO : validate detail dates

            //

        } catch (Exception e) {
            log.error("[submitLeavingRequest] [{}] ex", StringUtil.toJsonString(request), e);
            response = new BaseResponse(e);
        }
        return response;
    }
}
