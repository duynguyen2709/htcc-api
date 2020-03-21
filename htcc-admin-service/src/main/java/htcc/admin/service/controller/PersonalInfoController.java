package htcc.admin.service.controller;

import htcc.common.entity.jpa.AdminUser;
import htcc.common.constant.Constant;
import htcc.common.entity.base.BaseResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "Personal Info",
     description = "API lấy thông tin cá nhân")
@RestController
@RequestMapping("/")
@Log4j2
public class PersonalInfoController {

    @Autowired
    private AdminUserController controller;



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


}
