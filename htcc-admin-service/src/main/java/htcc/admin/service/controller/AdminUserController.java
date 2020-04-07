package htcc.admin.service.controller;

import htcc.common.component.kafka.KafkaProducerService;
import htcc.common.entity.base.BaseUser;
import htcc.common.entity.jpa.AdminUser;
import htcc.admin.service.service.jpa.AdminUserInfoService;
import htcc.admin.service.service.redis.RedisTokenService;
import htcc.admin.service.service.redis.RedisUserInfoService;
import htcc.common.constant.AccountStatusEnum;
import htcc.common.constant.Constant;
import htcc.common.constant.ReturnCodeEnum;
import htcc.common.entity.base.BaseResponse;
import htcc.common.util.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    @Autowired
    private RedisTokenService redis;

    @Autowired
    private RedisUserInfoService redisUserInfo;

    @Autowired
    private KafkaProducerService kafka;


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
        } finally {
            if (response.returnCode == ReturnCodeEnum.SUCCESS.getValue()){
                redisUserInfo.setUserInfo(response.data);
            }
        }
        return response;
    }




    @ApiOperation(value = "Tạo quản trị viên mới", response = AdminUser.class)
    @PostMapping("/users")
    public BaseResponse createUser(@ApiParam(value = "[Body] Thông tin user mới", required = true)
                                    @RequestBody AdminUser user) {
        BaseResponse<AdminUser> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        String rawPassword = user.getPassword();
        try {
            String error = user.isValid();
            if (!error.isEmpty()) {
                response = new BaseResponse<>(ReturnCodeEnum.PARAM_DATA_INVALID);
                response.setReturnMessage(error);
                return response;
            }

            user.password = new BCryptPasswordEncoder().encode(user.password);
            user.role = 1;
            user.status = 1;
            user.avatar = Constant.USER_DEFAULT_AVATAR;

            response.data = service.create(user);
        } catch (ConstraintViolationException| TransactionSystemException e) {
            log.warn("[createUser] ConstraintViolationException: " + e.getMessage());
            response = new BaseResponse<>(ReturnCodeEnum.PARAM_DATA_INVALID, e.getMessage());
        } catch (Exception e){
            log.error("[createUser] ex", e);
            response = new BaseResponse<>(e);
        } finally {
            if (response.getReturnCode() == ReturnCodeEnum.SUCCESS.getValue()){
                BaseUser model = new BaseUser(user);
                model.setPassword(rawPassword);

                kafka.sendMessage(kafka.getBuzConfig().eventCreateUser.topicName, model);
            }
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
            String error = user.isValid();
            if (!error.isEmpty()) {
                response = new BaseResponse<>(ReturnCodeEnum.PARAM_DATA_INVALID);
                response.setReturnMessage(error);
                return response;
            }

            AdminUser oldUser = service.findById(username);
            if (oldUser == null) {
                return new BaseResponse<>(ReturnCodeEnum.USER_NOT_FOUND);
            }
            user.username = oldUser.username;
            user.password = oldUser.password;
            user.role = oldUser.role;
            user.status = oldUser.status;
            user.avatar = oldUser.avatar;

            response.data = service.update(user);

        } catch (ConstraintViolationException| TransactionSystemException e) {
            log.warn("[update] ConstraintViolationException: " + e.getMessage());
            response = new BaseResponse<>(ReturnCodeEnum.PARAM_DATA_INVALID, e.getMessage());
        } catch (Exception e){
            log.error("[update] ex", e);
            response = new BaseResponse<>(e);
        }  finally {
            if (response.returnCode == ReturnCodeEnum.SUCCESS.getValue()){
                redisUserInfo.setUserInfo(response.data);
            }
        }
        return response;
    }




    @ApiOperation(value = "Khóa/Mở khóa tài khoản", response = AdminUser.class)
    @PutMapping("/users/{username}/status/{newStatus}")
    public BaseResponse lockAccount(@ApiParam(value = "[Path] Tên đăng nhập", required = true, defaultValue = "admin")
                               @PathVariable("username") String username,
                               @ApiParam(value = "[Path] Trạng thái mới cần update", required = true, defaultValue = "0")
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
            log.error("[lockAccount] ex", e);
            response = new BaseResponse<>(e);
        } finally {
            if (response.returnCode == ReturnCodeEnum.SUCCESS.getValue()){
                if (newStatus == AccountStatusEnum.BLOCK.getValue()) {
                    redis.setBlacklistToken(username);
                } else if (newStatus == AccountStatusEnum.ACTIVE.getValue()) {
                    redis.deleteBlacklistToken(username);
                }

                redisUserInfo.setUserInfo(response.data);
            }
        }
        return response;
    }



    @ApiOperation(value = "Xóa user", response = BaseResponse.class)
    @DeleteMapping("/users/{username}")
    public BaseResponse deleteUser(@ApiParam(value = "[Path] Tên đăng nhập", required = true, defaultValue = "admin")
                               @PathVariable("username") String username) {
        BaseResponse response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        try {
            AdminUser oldUser = service.findById(username);
            if (oldUser == null) {
                return new BaseResponse<>(ReturnCodeEnum.USER_NOT_FOUND);
            }

            if (oldUser.role == 0) {
                return new BaseResponse<>(ReturnCodeEnum.PERMISSION_DENIED);
            }

            service.delete(username);
        } catch (Exception e){
            log.error("[deleteUser] ex", e);
            response = new BaseResponse<>(e);
        } finally {
            if (response.returnCode == ReturnCodeEnum.SUCCESS.getValue()){
                redis.deleteBlacklistToken(username);
                redisUserInfo.deleteUserInfo(username);
            }
        }
        return response;
    }
}
