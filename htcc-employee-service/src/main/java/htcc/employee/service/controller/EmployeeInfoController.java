package htcc.employee.service.controller;

import htcc.common.constant.ReturnCodeEnum;
import htcc.common.entity.base.BaseResponse;
import htcc.common.util.StringUtil;
import htcc.employee.service.entity.jpa.EmployeeInfo;
import htcc.employee.service.service.jpa.EmployeeInfoService;
import io.swagger.annotations.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.util.List;

@Api(tags = "API thông tin cá nhân",
     description = "API get/update thông tin cá nhân của nhân viên")
@RestController
@Log4j2
public class EmployeeInfoController {

    @Autowired
    private EmployeeInfoService service;



    @ApiOperation(value = "Lấy thông tin của nhân viên", response = EmployeeInfo.class)
    @GetMapping("/users/{companyId}/{username}")
    public BaseResponse getUserInfo(@ApiParam(value = "[Path] Mã công ty", required = true) @PathVariable(required = true) String companyId,
                                       @ApiParam(value = "[Path] Tên đăng nhập", required = true) @PathVariable(required = true) String username) {
        BaseResponse<EmployeeInfo> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        try {
            EmployeeInfo user = service.findById(new EmployeeInfo.Key(companyId, username));
            if (user == null) {
                return new BaseResponse<>(ReturnCodeEnum.USER_NOT_FOUND);
            }

            response.data = user;
        } catch (Exception e){
            log.error(String.format("getCheckinInfo [%s - %s] ex", companyId, username), e);
            response = new BaseResponse<>(e);
        }
        return response;
    }





    @ApiOperation(value = "Cập nhật thông tin của nhân viên (các field * không cần gửi lại khi update) -" +
            "(các field còn lại phải gửi lại kể cả khi không update)",
                  response = EmployeeInfo.class)
    @ApiImplicitParams({
                               @ApiImplicitParam(name = "companyId", dataType = "String", paramType = "path",
                                                 value = "[Path] Mã công ty", defaultValue = "VNG", required = true),
                               @ApiImplicitParam(name = "username", dataType = "String", paramType = "path",
                                                 value = "[Path] Tên đăng nhập", defaultValue = "admin", required = true),
                               @ApiImplicitParam(name = "request", dataType = "EmployeeInfo", paramType = "body",
                                                 value = "[Body] Thông tin mới cần update", required = true)
                       })
    @PutMapping("/users/{companyId}/{username}")
    public BaseResponse update(@PathVariable(required = true) String companyId,
                               @PathVariable(required = true) String username,
                               @RequestBody EmployeeInfo request) {
        BaseResponse<EmployeeInfo> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        try {
            if (!request.isValid()) {
                throw new ConstraintViolationException(
                        "Param Invalid " + StringUtil.toJsonString(request), null);
            }

            EmployeeInfo oldUser = service.findById(new EmployeeInfo.Key(companyId, username));
            if (oldUser == null) {
                return new BaseResponse<>(ReturnCodeEnum.USER_NOT_FOUND);
            }

            // set old value to update
            request.refillImmutableValue(oldUser);

            oldUser = service.update(request);

            response.data = oldUser;
        } catch (ConstraintViolationException| TransactionSystemException e) {
            log.warn("[update] ConstraintViolationException: ", e);
            response = new BaseResponse<>(ReturnCodeEnum.PARAM_DATA_INVALID, e.getMessage());
        } catch (Exception e){
            log.error(String.format("update [%s - %s] ex", companyId, username), e);
            response = new BaseResponse<>(e);
        }
        return response;
    }






    @ApiOperation(value = "Lấy thông tin của tất cả nhân viên (dành cho quản lý)", response = EmployeeInfo.class)
    @GetMapping("/users")
    public BaseResponse getAllUser() {
        BaseResponse<List<EmployeeInfo>> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        try {
            response.data = service.findAll();
        } catch (Exception e){
            log.error("getAllUser ex", e);
            response = new BaseResponse<>(e);
        }
        return response;
    }
}
