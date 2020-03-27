package htcc.admin.service.controller;

import htcc.admin.service.service.GoogleDriveService;
import htcc.admin.service.service.jpa.AdminUserInfoService;
import htcc.common.constant.Constant;
import htcc.common.constant.ReturnCodeEnum;
import htcc.common.entity.base.BaseResponse;
import htcc.common.entity.jpa.AdminUser;
import htcc.common.util.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api(tags = "Personal Info",
     description = "API lấy thông tin cá nhân")
@RestController
@RequestMapping("/")
@Log4j2
public class PersonalInfoController {

    @Autowired
    private AdminUserController controller;

    @Autowired
    private AdminUserInfoService userInfoService;

    @Autowired
    private GoogleDriveService driveService;


    @ApiOperation(value = "Lấy thông tin cá nhân", response = AdminUser.class)
    @GetMapping("/me")
    public BaseResponse getPersonalInfo(@ApiParam(hidden = true) @RequestHeader(Constant.USERNAME) String username) {
        return controller.getUserInfo(username);
    }




    @ApiOperation(value = "Cập nhật thông tin của user hiện tại", response = AdminUser.class)
    @PutMapping("/me")
    public BaseResponse update(@ApiParam(hidden = true) @RequestHeader(Constant.USERNAME) String username,
                               @ApiParam(value = "[Body] Thông tin mới cần update", required = true)
                               @RequestBody AdminUser user) {
        return controller.update(username, user);
    }




    @ApiOperation(value = "Cập nhật avatar của user hiện tại", response = AdminUser.class)
    @PostMapping("/me/avatar")
    public BaseResponse updateAvatar(@ApiParam(hidden = true) @RequestHeader(Constant.USERNAME) String username,
                               @ApiParam(value = "[Multipart/form-data] Avatar mới cần update", required = true)
                               @RequestParam(name = "avatar") MultipartFile avatar) {
        BaseResponse<AdminUser> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        String oldAvatar = "";
        try {
            AdminUser admin = userInfoService.findById(username);
            if (admin == null) {
                response = new BaseResponse<>(ReturnCodeEnum.USER_NOT_FOUND);
                response.setReturnMessage("Không tìm thấy người dùng " + StringUtil.valueOf(username));
                return response;
            }

            String fileName = StringUtil.valueOf(username);
            String newAvatar = driveService.uploadAvatar(avatar, fileName);

            if (newAvatar == null) {
                throw new Exception("driveService.uploadAvatar return null");
            }

            oldAvatar = admin.getAvatar();
            admin.setAvatar(newAvatar);
            response.setData(userInfoService.update(admin));

        } catch (Exception e) {
            log.error("[updateAvatar] {} ex", username, e);
            response = new BaseResponse<>(e);
        } finally {
            if (response.getReturnCode() == ReturnCodeEnum.SUCCESS.getValue()) {
                String fileId = StringUtil.getFileIdFromImage(oldAvatar);
                if (!fileId.isEmpty()) {
                    driveService.deleteFile(fileId);
                }
            }
        }

        return response;
    }


}
