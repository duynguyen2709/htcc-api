package htcc.admin.service.controller;

import com.google.gson.reflect.TypeToken;
import htcc.admin.service.service.rest.EmployeeCompanyService;
import htcc.admin.service.service.rest.EmployeeInfoService;
import htcc.admin.service.service.rest.GatewayCompanyUserService;
import htcc.common.component.kafka.KafkaProducerService;
import htcc.common.constant.AccountStatusEnum;
import htcc.common.constant.ReturnCodeEnum;
import htcc.common.entity.base.BaseResponse;
import htcc.common.entity.base.BaseUser;
import htcc.common.entity.companyuser.CompanyUserModel;
import htcc.common.entity.jpa.Company;
import htcc.common.util.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api(tags = "API quản lý Admin của Công Ty",
     description = "API quản lý danh sách Admin của công ty")
@RestController
@Log4j2
public class CompanyUserController {

    @Autowired
    private GatewayCompanyUserService service;

    @Autowired
    private EmployeeInfoService employeeInfoService;

    @Autowired
    private EmployeeCompanyService companyService;

    @Autowired
    private KafkaProducerService kafka;

    @ApiOperation(value = "Lấy danh sách admin của công ty", response = CompanyUserModel.class)
    @GetMapping("/companyusers/{companyId}")
    public BaseResponse getListCompanyUser(@ApiParam(value = "[Path] Mã công ty", required = true, defaultValue = "VNG")
                                               @PathVariable String companyId){
        BaseResponse<List<CompanyUserModel>> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        try {
            BaseResponse subResponse = service.getListCompanyUser(companyId);
            if (subResponse == null || subResponse.getReturnCode() != ReturnCodeEnum.SUCCESS.getValue()) {
                return subResponse;
            }

            String rawValue = StringUtil.toJsonString(subResponse.getData());
            List<CompanyUserModel> companyUserModelList = StringUtil.json2Collection(rawValue,
                    new TypeToken<List<CompanyUserModel>>() {}.getType());

            List<CompanyUserModel> dataResponse = new ArrayList<>();
            for (CompanyUserModel model : companyUserModelList) {
                if (service.isSuperAdmin(model)) {
                    dataResponse.add(model);
                }
            }
            response.setData(dataResponse);
        } catch (Exception e) {
            log.error("[getListCompanyUser] [{}] ex", companyId, e);
            return new BaseResponse(e);
        }
        return response;
    }




    @ApiOperation(value = "Tạo admin công ty mới", response = CompanyUserModel.class)
    @PostMapping("/companyusers")
    public BaseResponse createCompanyUser(@ApiParam(value = "[Body] Thông tin admin mới", required = true)
                                   @RequestBody CompanyUserModel user) {
        log.info(StringUtil.toJsonString(user));
        BaseResponse response = new BaseResponse(ReturnCodeEnum.SUCCESS);
        response.setReturnMessage("Tạo người dùng thành công");
        String rawPassword = user.getPassword();
        try {
            String error = user.isValid();
            if (!error.isEmpty()) {
                response = new BaseResponse(ReturnCodeEnum.PARAM_DATA_INVALID);
                response.setReturnMessage(error);
                return response;
            }

            Company company = findCompany(user.getCompanyId());
            if (company == null) {
                response = new BaseResponse(ReturnCodeEnum.DATA_NOT_FOUND);
                response.setReturnMessage(String.format("Không tìm thấy công ty [%s]", user.companyId));
                return response;
            }

            response = service.createCompanyUser(user);
            if (response == null || response.getReturnCode() != ReturnCodeEnum.SUCCESS.getValue()) {
                throw new Exception("GatewayCompanyUserService.createCompanyUser response = " + StringUtil.toJsonString(response));
            }

            BaseResponse response1 = employeeInfoService.createDefaultEmployeeInfo(user);
            // if create default employee failed, then rollback
            if (response1 == null || response1.getReturnCode() != ReturnCodeEnum.SUCCESS.getValue()) {
                log.error("Rollback on creating new employee {}, response {}",
                        StringUtil.toJsonString(user), StringUtil.toJsonString(response1));
                service.deleteCompanyUser(user);
                throw new Exception("employeeInfoService.createDefaultEmployeeInfo response = " + StringUtil.toJsonString(response1));
            }
        } catch (Exception e) {
            log.error("[createCompanyUser] {} ex", StringUtil.toJsonString(user), e);
            response = new BaseResponse(e);
        } finally {
            if (response != null && response.getReturnCode() == ReturnCodeEnum.SUCCESS.getValue()){
                BaseUser model = new BaseUser(user);
                model.setPassword(rawPassword);

                kafka.sendMessage(kafka.getBuzConfig().getEventCreateUser().topicName, model);
            }
        }
        return response;
    }

    private Company findCompany(String companyId) {
        try {
            BaseResponse response = companyService.getListCompany();
            if (response == null || response.getReturnCode() != ReturnCodeEnum.SUCCESS.getValue()) {
                throw new Exception(String.format("[companyService.getListCompany] response [%s]", StringUtil.toJsonString(response)));
            }
            String data = StringUtil.toJsonString(response.getData());

            List<Company> list = StringUtil.json2Collection(data, new TypeToken<List<Company>>(){}.getType());
            for (Company c : list) {
                if (c.getCompanyId().equals(companyId)){
                    return c;
                }
            }
        } catch (Exception e){
            log.error("[findCompany] [{}] ex", companyId, e);
        }
        return null;
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
            user.setCompanyId(companyId);
            user.setUsername(username);

            String error = user.isValid();
            if (!error.isEmpty()) {
                response = new BaseResponse(ReturnCodeEnum.PARAM_DATA_INVALID);
                response.setReturnMessage(error);
                return response;
            }

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
