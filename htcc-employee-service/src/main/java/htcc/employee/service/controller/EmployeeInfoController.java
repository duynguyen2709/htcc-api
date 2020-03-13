package htcc.employee.service.controller;

import htcc.common.constant.ReturnCodeEnum;
import htcc.common.entity.base.BaseResponse;
import htcc.employee.service.entity.checkin.CheckinRequest;
import htcc.employee.service.entity.checkin.CheckinResponse;
import htcc.employee.service.entity.jpa.EmployeeInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api(tags = "API thông tin cá nhân",
     description = "API get/update thông tin cá nhân của nhân viên")
@RestController
@Log4j2
public class EmployeeInfoController {

    @ApiOperation(value = "Lấy thông tin của nhân viên", response = EmployeeInfo.class)
    @GetMapping("/users/{companyId}/{username}")
    public BaseResponse getUserInfo(@ApiParam(value = "[Path] Mã công ty", required = true) @PathVariable(required = true) String companyId,
                                       @ApiParam(value = "[Path] Tên đăng nhập", required = true) @PathVariable(required = true) String username) {
        BaseResponse<EmployeeInfo> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        try {
            response.data = new EmployeeInfo();
        } catch (Exception e){
            log.error(String.format("getCheckinInfo [%s - %s] ex", companyId, username), e);
            response = new BaseResponse<>(e);
        }
        return response;
    }

    @ApiOperation(value = "Cập nhật thông tin của nhân viên", response = EmployeeInfo.class)
    @PutMapping("/users/{companyId}/{username}")
    public BaseResponse updateUserInfo(@ApiParam(value = "[Body] Thông tin mới cần update", required = true) @RequestBody EmployeeInfo request) {
        BaseResponse<EmployeeInfo> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        try {
            response.data = new EmployeeInfo();
        } catch (Exception e){
            log.error(String.format("checkin [%s - %s] ex", request.companyId, request.username), e);
            response = new BaseResponse<>(e);
        }
        return response;
    }

    @ApiOperation(value = "Lấy thông tin của tất cả nhân viên (dành cho quản lý)", response = EmployeeInfo.class)
    @GetMapping("/users")
    public BaseResponse getUsers() {
        BaseResponse<List<EmployeeInfo>> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        try {
            response.data = new ArrayList<>();
        } catch (Exception e){
            log.error("getUsers ex", e);
            response = new BaseResponse<>(e);
        }
        return response;
    }
}
