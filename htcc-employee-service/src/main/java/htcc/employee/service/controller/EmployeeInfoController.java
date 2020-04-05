package htcc.employee.service.controller;

import htcc.common.component.kafka.KafkaProducerService;
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

    @Autowired
    private KafkaProducerService kafka;



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




    @ApiOperation(value = "Cập nhật thông tin của nhân viên", response = EmployeeInfo.class)
    @PostMapping("/users/{companyId}/{username}")
    public BaseResponse updateUserInfo(@ApiParam(name = "companyId", value = "[Path] Mã công ty", defaultValue = "VNG", required = true)
                                         @PathVariable(required = true) String companyId,
                                     @ApiParam(name = "username", value = "[Path] Tên đăng nhập", defaultValue = "admin", required = true)
                                     @PathVariable(required = true) String username,
                                     @ApiParam(name = "employeeId", value = "(*) Mã nhân viên", defaultValue = "VNG-00001", required = false)
                                     @RequestParam(name = "employeeId", required = false) String employeeId,
                                     @ApiParam(name = "officeId", value = "(*) Mã chi nhánh", defaultValue = "CAMPUS", required = false)
                                     @RequestParam(name = "officeId", required = false) String officeId,
                                     @ApiParam(name = "department", value = "(*) Phòng ban/ bộ phận làm việc", defaultValue = "PMA", required = false)
                                     @RequestParam(name = "department", required = false) String department,
                                     @ApiParam(name = "title", value = "(*) Chức danh/ Cấp độ nhân viên", defaultValue = "Junior Developer", required = false)
                                     @RequestParam(name = "title", required = false) String title,
                                     @ApiParam(name = "fullName", value = "Họ tên", defaultValue = "NGUYỄN ANH DUY", required = true)
                                     @RequestParam(name = "fullName", required = true) String fullName,
                                     @ApiParam(name = "birthDate", value = "Ngày sinh (yyyy-MM-dd)", defaultValue = "1998-09-27", required = true)
                                     @RequestParam(name = "birthDate", required = true) String birthDate,
                                     @ApiParam(name = "email", value = "Email", defaultValue = "naduy.hcmus@gmail.com", required = true)
                                     @RequestParam(name = "email", required = true) String email,
                                     @ApiParam(name = "identityCardNo", value = "CMND", defaultValue = "272683901", required = true)
                                     @RequestParam(name = "identityCardNo", required = true) String identityCardNo,
                                     @ApiParam(name = "phoneNumber", value = "SDT", defaultValue = "0948202709", required = true)
                                     @RequestParam(name = "phoneNumber", required = true) String phoneNumber,
                                     @ApiParam(name = "address", value = "Địa chỉ nơi ở", defaultValue = "TPHCM", required = true)
                                     @RequestParam(name = "address", required = true) String address,
                                     @ApiParam(value = "[Multipart/form-data] Avatar mới cần update", required = false)
                                         @RequestParam(name = "avatar", required = false) MultipartFile avatar) {
        BaseResponse<EmployeeInfo> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        response.setReturnMessage("Cập nhật thông tin thành công");
        String oldAvatar = "";
        boolean needUpdate = false;
        EmployeeInfo model = null;
        try {
            model = new EmployeeInfo(companyId, username, employeeId, officeId, department, title, fullName,
                    null, email, identityCardNo, phoneNumber, address, StringUtil.EMPTY);
            model.setBirthDate(birthDate);

            log.info("[UpdateUserInfo] EmployeeInfo: " + StringUtil.toJsonString(model));

            // validate model
            String errorMessage = model.isValid();
            if (!errorMessage.isEmpty()) {
                response = new BaseResponse<>(ReturnCodeEnum.PARAM_DATA_INVALID);
                response.setReturnMessage(errorMessage);
                return response;
            }

            // find current user in db
            EmployeeInfo oldUser = service.findById(new EmployeeInfo.Key(companyId, username));
            if (oldUser == null) {
                response = new BaseResponse<>(ReturnCodeEnum.USER_NOT_FOUND);
                response.setReturnMessage(String.format("Không tìm thấy người dùng [%s-%s]",
                        StringUtil.valueOf(companyId), StringUtil.valueOf(username)));
                return response;
            }

            // check need send kafka
            if (!oldUser.getEmail().equals(model.getEmail()) ||
                    !oldUser.getPhoneNumber().equals(model.getPhoneNumber())) {
                needUpdate = true;
            }

            model.refillImmutableValue(oldUser);

            if (avatar != null) {
                String fileName  = String.format("%s_%s", companyId, username);
                String newAvatar = driveService.uploadAvatar(avatar, fileName);

                if (newAvatar == null) {
                    throw new Exception("driveService.uploadAvatar return null");
                }

                // save old avatar to delete
                oldAvatar = oldUser.getAvatar();

                // set new avatar
                model.setAvatar(newAvatar);
            }

            // update to db
            model = service.update(model);

            // set response
            response.data = model;

        } catch (Exception e){
            log.error(String.format("[updateUserInfo] [%s - %s] ex", companyId, username), e);
            response = new BaseResponse<>(e);
        } finally {
            if (response.getReturnCode() == ReturnCodeEnum.SUCCESS.getValue()) {
                // delete old file on gg drive
                if (avatar != null) {
                    String fileId = StringUtil.getFileIdFromImage(oldAvatar);
                    if (!fileId.isEmpty()) {
                        driveService.deleteFile(fileId);
                    }
                }
                // reset cache
                redisUserInfo.setUserInfo(response.data);

                // send kafka
                if (needUpdate) {
                    kafka.sendMessage(kafka.getBuzConfig().eventUpdateEmployeeInfo.topicName, model);
                }
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
