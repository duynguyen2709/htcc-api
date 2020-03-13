package htcc.employee.service.controller;

import htcc.common.constant.ReturnCodeEnum;
import htcc.common.entity.base.BaseResponse;
import htcc.employee.service.entity.checkin.CheckinRequest;
import htcc.employee.service.entity.checkin.CheckinResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

@Api(tags = "API điểm danh",
     description = "API điểm danh của nhân viên")
@RestController
@Log4j2
public class CheckinController {

    @ApiOperation(value = "Kiểm tra thông tin điểm danh của nhân viên", response = CheckinResponse.class)
    @GetMapping("/checkin")
    public BaseResponse getCheckinInfo(@ApiParam(value = "[Query] Mã công ty", required = true) @RequestParam(required = true) String companyId,
                                       @ApiParam(value = "[Query] Tên đăng nhập", required = true) @RequestParam(required = true) String username) {
        BaseResponse<CheckinResponse> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        try {
            response.data = new CheckinResponse();
        } catch (Exception e){
            log.error(String.format("getCheckinInfo [%s - %s] ex", companyId, username), e);
            response = new BaseResponse<>(e);
        }
        return response;
    }

    @ApiOperation(value = "Điểm danh vào", response = CheckinResponse.class)
    @PostMapping("/checkin")
    public BaseResponse checkin(@ApiParam(value = "[Body] Thông tin điểm danh", required = true) @RequestBody CheckinRequest request) {
        BaseResponse<CheckinResponse> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        try {
            response.data = new CheckinResponse();
        } catch (Exception e){
            log.error(String.format("checkin [%s - %s] ex", request.companyId, request.username), e);
            response = new BaseResponse<>(e);
        }
        return response;
    }
}
