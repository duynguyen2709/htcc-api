package htcc.admin.service.controller;

import htcc.admin.service.service.rest.EmployeeInfoService;
import htcc.admin.service.service.rest.GatewayCompanyUserService;
import htcc.common.constant.AccountStatusEnum;
import htcc.common.constant.ReturnCodeEnum;
import htcc.common.entity.base.BaseResponse;
import htcc.common.entity.companyuser.CompanyUserModel;
import htcc.common.util.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "API quản lý Admin của Công Ty",
     description = "API quản lý danh sách Admin của công ty")
@RestController
@Log4j2
public class CompanyUserController {

    @Autowired
    private GatewayCompanyUserService service;

    @Autowired
    private EmployeeInfoService employeeInfoService;

    @ApiOperation(value = "Lấy danh sách admin của công ty", response = CompanyUserModel.class)
    @GetMapping("/companyusers/{companyId}")
    public BaseResponse getListCompanyUser(@ApiParam(value = "[Path] Mã công ty", required = true, defaultValue = "VNG")
                                               @PathVariable String companyId){
        return service.getListCompanyUser(companyId);
    }




    @ApiOperation(value = "Tạo admin công ty mới", response = CompanyUserModel.class)
    @PostMapping("/companyusers")
    public BaseResponse createCompanyUser(@ApiParam(value = "[Body] Thông tin admin mới", required = true)
                                   @RequestBody CompanyUserModel user) {
        BaseResponse response = new BaseResponse(ReturnCodeEnum.SUCCESS);
        try {
            String error = user.isValid();
            if (!error.isEmpty()) {
                response = new BaseResponse(ReturnCodeEnum.PARAM_DATA_INVALID);
                response.setReturnMessage(error);
                return response;
            }

            response = service.createCompanyUser(user);

            if (response.getReturnCode() == ReturnCodeEnum.SUCCESS.getValue()) {
                BaseResponse response1 = employeeInfoService.createDefaultEmployeeInfo(user);
                // if create default employee failed, then rollback
                if (response1.getReturnCode() != ReturnCodeEnum.SUCCESS.getValue()) {
                    log.warn("Rollback on creating new employee {}, response {}",
                            StringUtil.toJsonString(user), StringUtil.toJsonString(response1));
                    service.deleteCompanyUser(user);
                    return response1;
                }
            }
        } catch (Exception e) {
            log.error("[createCompanyUser] {} ex", StringUtil.toJsonString(user), e);
            return new BaseResponse(e);
        }
        return response;
    }




    @ApiOperation(value = "Cập nhật thông tin của admin", response = CompanyUserModel.class)
    @PutMapping("/companyusers/{companyId}/{username}")
    public BaseResponse updateCompanyUserInfo(@ApiParam(value = "[Path] Mã công ty", required = true, defaultValue = "VNG")
                               @PathVariable("companyId") String companyId,
                                          @ApiParam(value = "[Path] Tên đăng nhập", required = true, defaultValue = "admin")
                                          @PathVariable("username") String username,
                               @ApiParam(value = "[Body] Thông tin mới cần update", required = true)
                                          @RequestBody CompanyUserModel user){
        BaseResponse response = new BaseResponse(ReturnCodeEnum.SUCCESS);
        try {
            String error = user.isValid();
            if (!error.isEmpty()) {
                response = new BaseResponse(ReturnCodeEnum.PARAM_DATA_INVALID);
                response.setReturnMessage(error);
                return response;
            }

            user.setCompanyId(companyId);
            user.setUsername(username);

            return service.updateCompanyUserInfo(user);
        } catch (Exception e) {
            log.error("[updateCompanyUserInfo] {} ex", StringUtil.toJsonString(user), e);
            return new BaseResponse(e);
        }
    }




    @ApiOperation(value = "Khóa/Mở khóa admin", response = CompanyUserModel.class)
    @PutMapping("/companyusers/{companyId}/{username}/status/{newStatus}")
    public BaseResponse lockCompanyUser(@ApiParam(value = "[Path] Mã công ty", required = true, defaultValue = "VNG")
                                    @PathVariable("companyId") String companyId,
                                    @ApiParam(value = "[Path] Tên đăng nhập", required = true, defaultValue = "admin")
                                    @PathVariable("username") String username,
                                    @ApiParam(value = "[Path] Trạng thái mới cần update", required = true, defaultValue = "0")
                                    @PathVariable int newStatus) {
        BaseResponse response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        try {
            if (AccountStatusEnum.fromInt(newStatus) == null) {
                response = new BaseResponse(ReturnCodeEnum.PARAM_DATA_INVALID);
                response.setReturnMessage(String.format("Trạng thái mới [%s] không hợp lệ", newStatus));
                return response;
            }

            CompanyUserModel companyUser = new CompanyUserModel();
            companyUser.setCompanyId(companyId);
            companyUser.setUsername(username);
            companyUser.setStatus(newStatus);

            return service.updateCompanyUserStatus(companyUser);
        } catch (Exception e){
            log.error("[lockCompanyUser] [{} - {} - {}] ex", companyId, username, newStatus, e);
            return new BaseResponse<>(e);
        }
    }
}
