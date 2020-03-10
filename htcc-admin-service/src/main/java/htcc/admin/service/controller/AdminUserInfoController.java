package htcc.admin.service.controller;

import htcc.admin.service.entity.jpa.AdminUserInfo;
import htcc.admin.service.service.jpa.AdminUserInfoService;
import htcc.common.constant.ReturnCodeEnum;
import htcc.common.entity.base.BaseResponse;
import htcc.common.util.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;

@Api(tags = "Admin REST APIs",
     description = "API của quản trị viên")
@RestController
@RequestMapping("/")
@Log4j2
public class AdminUserInfoController {

    @Autowired
    private AdminUserInfoService service;

    @ApiOperation(value = "Lấy thông tin của user", response = AdminUserInfo.class)
    @GetMapping("/users/{username}")
    public BaseResponse findByUsername(@ApiParam(value = "[Path] Tên đăng nhập", required = true)
                                           @PathVariable("username") String username) {
        BaseResponse<AdminUserInfo> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        try {
            AdminUserInfo user = service.findById(username);
            if (user == null) {
                return new BaseResponse<>(ReturnCodeEnum.USER_NOT_FOUND);
            }

            response.data = user;
        } catch (Exception e){
            log.error("[findByUsername] ex", e);
            response = new BaseResponse<>(e);
        }
        return response;
    }

    @ApiOperation(value = "Cập nhật thông tin của user hiện tại", response = AdminUserInfo.class)
    @PutMapping("/users/{username}")
    public BaseResponse update(@ApiParam(value = "[Path] Tên đăng nhập", required = true)
                                   @PathVariable("username") String username,
                               @ApiParam(value = "[Body] Thông tin mới cần update", required = true)
                               @RequestBody AdminUserInfo user) {
        BaseResponse<AdminUserInfo> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        try {
            if (!user.isValid()) {
                throw new ConstraintViolationException(
                        "Param Invalid " + StringUtil.toJsonString(user), null);
            }

            AdminUserInfo oldUser = service.findById(username);
            if (oldUser == null) {
                return new BaseResponse<>(ReturnCodeEnum.USER_NOT_FOUND);
            }

            user = service.update(user);
            response.data = user;
        } catch (ConstraintViolationException| TransactionSystemException e) {
            log.warn("[update] ConstraintViolationException: " + e.getMessage());
            response = new BaseResponse<>(ReturnCodeEnum.PARAM_DATA_INVALID, e.getMessage());
        } catch (Exception e){
            log.error("[update] ex", e);
            response = new BaseResponse<>(e);
        }
        return response;
    }
}
