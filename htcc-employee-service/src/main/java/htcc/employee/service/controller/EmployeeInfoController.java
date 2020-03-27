package htcc.employee.service.controller;

import htcc.common.constant.ReturnCodeEnum;
import htcc.common.entity.base.BaseResponse;
import htcc.common.util.StringUtil;
import htcc.common.entity.jpa.EmployeeInfo;
import htcc.employee.service.service.GoogleDriveService;
import htcc.employee.service.service.jpa.EmployeeInfoService;
import htcc.employee.service.service.redis.RedisUserInfoService;
import io.swagger.annotations.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintViolationException;
import java.util.List;

@Api(tags = "API thông tin cá nhân",
     description = "API get/update thông tin cá nhân của nhân viên")
@RestController
@Log4j2
public class EmployeeInfoController {

    @Autowired
    private EmployeeInfoService service;

    @Autowired
    private RedisUserInfoService redisUserInfo;

    @Autowired
    private GoogleDriveService driveService;



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
            log.error(String.format("getUserInfo [%s - %s] ex", companyId, username), e);
            response = new BaseResponse<>(e);
        } finally {
            if (response.returnCode == ReturnCodeEnum.SUCCESS.getValue()) {
                redisUserInfo.setUserInfo(response.data);
            }
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
        } finally {
            if (response.returnCode == ReturnCodeEnum.SUCCESS.getValue()) {
                redisUserInfo.setUserInfo(response.data);
            }
        }
        return response;
    }




    @ApiOperation(value = "Cập nhật avatar", response = EmployeeInfo.class)
    @PostMapping("/users/avatar/{companyId}/{username}")
    public BaseResponse changeAvatar(@ApiParam(name = "companyId", value = "[Path] Mã công ty", defaultValue = "VNG", required = true)
                                         @PathVariable(required = true) String companyId,
                                     @ApiParam(name = "username", value = "Tên đăng nhập", defaultValue = "admin", required = true)
                                     @PathVariable(required = true) String username,
                                     @ApiParam(value = "[Multipart/form-data] Avatar mới cần update", required = true)
                                         @RequestParam(name = "avatar") MultipartFile avatar) {
        BaseResponse<EmployeeInfo> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        String oldAvatar = "";
        try {
            EmployeeInfo user = service.findById(new EmployeeInfo.Key(companyId, username));
            if (user == null) {
                response = new BaseResponse<>(ReturnCodeEnum.USER_NOT_FOUND);
                response.setReturnMessage(String.format("Không tìm thấy người dùng [%s-%s]",
                        StringUtil.valueOf(companyId), StringUtil.valueOf(username)));
                return response;
            }

            String fileName = String.format("%s_%s", companyId, username);
            String newAvatar = driveService.uploadAvatar(avatar, fileName);

            if (newAvatar == null) {
                throw new Exception("driveService.uploadAvatar return null");
            }

            // save old avatar to delete
            oldAvatar = user.getAvatar();

            // set new avatar
            user.setAvatar(newAvatar);

            // update to db
            user = service.update(user);

            // set response
            response.data = user;
        } catch (Exception e){
            log.error(String.format("[changeAvatar] [%s - %s] ex", companyId, username), e);
            response = new BaseResponse<>(e);
        } finally {
            if (response.returnCode == ReturnCodeEnum.SUCCESS.getValue()) {
                // delete old file on gg drive
                String fileId = StringUtil.getFileIdFromImage(oldAvatar);
                if (!fileId.isEmpty()) {
                    driveService.deleteFile(fileId);
                }
                // reset cache
                redisUserInfo.setUserInfo(response.data);
            }
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
