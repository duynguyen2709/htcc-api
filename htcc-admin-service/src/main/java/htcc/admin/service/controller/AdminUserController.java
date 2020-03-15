package htcc.admin.service.controller;

import htcc.admin.service.entity.jpa.AdminUser;
import htcc.admin.service.service.jpa.AdminUserInfoService;
import htcc.common.constant.AccountStatusEnum;
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
import java.util.List;
import java.util.stream.Collectors;

@Api(tags = "Admin REST APIs",
     description = "API của quản trị viên")
@RestController
@RequestMapping("/")
@Log4j2
public class AdminUserController {

    @Autowired
    private AdminUserInfoService service;

    @ApiOperation(value = "Lấy thông tin của các user", response = AdminUser.class)
    @GetMapping("/users")
    public BaseResponse getListUser() {
        BaseResponse<List<AdminUser>> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        try {
            List<AdminUser> list = service.findAll().stream()
                    .filter(c -> c.role != 0).collect(Collectors.toList());

            response.data = list;

        } catch (Exception e){
            log.error("[findByUsername] ex", e);
            response = new BaseResponse<>(e);
        }
        return response;
    }




    @ApiOperation(value = "Lấy thông tin của user", response = AdminUser.class)
    @GetMapping("/users/{username}")
    public BaseResponse getUserInfo(@ApiParam(value = "[Path] Tên đăng nhập", required = true)
                                           @PathVariable("username") String username) {
        BaseResponse<AdminUser> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        try {
            AdminUser user = service.findById(username);
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




    @ApiOperation(value = "Cập nhật thông tin của user hiện tại", response = AdminUser.class)
    @PutMapping("/users/{username}")
    public BaseResponse update(@ApiParam(value = "[Path] Tên đăng nhập", required = true)
                                   @PathVariable("username") String username,
                               @ApiParam(value = "[Body] Thông tin mới cần update", required = true)
                               @RequestBody AdminUser user) {
        BaseResponse<AdminUser> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        try {
            if (!user.isValid()) {
                throw new ConstraintViolationException(
                        "Param Invalid " + StringUtil.toJsonString(user), null);
            }

            AdminUser oldUser = service.findById(username);
            if (oldUser == null) {
                return new BaseResponse<>(ReturnCodeEnum.USER_NOT_FOUND);
            }

            user.role = oldUser.role;
            user.status = oldUser.status;
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




    @ApiOperation(value = "Khóa/Mở khóa tài khoản", response = AdminUser.class)
    @PutMapping("/users/{username}/status/{newStatus}")
    public BaseResponse lockAccount(@ApiParam(value = "[Path] Tên đăng nhập", required = true)
                               @PathVariable("username") String username,
                               @ApiParam(value = "[Path] Trạng thái mới cần update", required = true)
                               @PathVariable int newStatus) {
        BaseResponse<AdminUser> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        try {
            if (AccountStatusEnum.fromInt(newStatus) == null) {
                throw new ConstraintViolationException(
                        "Param Invalid " + StringUtil.toJsonString(newStatus), null);
            }

            AdminUser oldUser = service.findById(username);
            if (oldUser == null) {
                return new BaseResponse<>(ReturnCodeEnum.USER_NOT_FOUND);
            }

            if (oldUser.status == newStatus) {
                new BaseResponse<>(ReturnCodeEnum.PARAM_DATA_INVALID, "Trạng thái mới không được giống với trạng thái cũ.");
            }

            oldUser.status = newStatus;
            response.data = service.update(oldUser);
        } catch (ConstraintViolationException| TransactionSystemException e) {
            log.warn("[lockAccount] ConstraintViolationException: " + e.getMessage());
            response = new BaseResponse<>(ReturnCodeEnum.PARAM_DATA_INVALID, e.getMessage());
        } catch (Exception e){
            log.error("[update] ex", e);
            response = new BaseResponse<>(e);
        } finally {
            // TODO: blacklist or delete token
        }
        return response;
    }
}
