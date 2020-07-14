package htcc.employee.service.controller.employee;

import com.google.gson.reflect.TypeToken;
import htcc.common.component.kafka.KafkaProducerService;
import htcc.common.constant.Constant;
import htcc.common.constant.ReturnCodeEnum;
import htcc.common.entity.base.BaseResponse;
import htcc.common.entity.companyuser.CompanyUserModel;
import htcc.common.entity.jpa.EmployeeInfo;
import htcc.common.entity.jpa.ExtendedEmployeeInfo;
import htcc.common.entity.role.EmployeePermission;
import htcc.common.util.StringUtil;
import htcc.employee.service.service.GatewayService;
import htcc.employee.service.service.jpa.EmployeeInfoService;
import htcc.employee.service.service.jpa.EmployeePermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "API quản lý thông tin nhân viên (CHO QUẢN LÝ)",
     description = "API quản lý thông tin cá nhân của nhân viên")
@RestController
@Log4j2
public class ManagerEmployeeInfoController {

    @Autowired
    private EmployeeInfoService employeeInfoService;

    @Autowired
    private EmployeePermissionService employeePermissionService;

    @Autowired
    private KafkaProducerService kafka;

    @Autowired
    private GatewayService gatewayService;

    @ApiOperation(value = "Lấy thông tin của tất cả nhân viên", response = ExtendedEmployeeInfo.class)
    @GetMapping("/users/{companyId}")
    public BaseResponse getAllUser(@PathVariable String companyId) {
        BaseResponse<List<ExtendedEmployeeInfo>> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        try {
            List<ExtendedEmployeeInfo> dataResponse = new ArrayList<>();

            List<EmployeeInfo> employeeInfoList = employeeInfoService.findByCompanyId(companyId);

            BaseResponse subResponse = gatewayService.getCompanyUsers(companyId);
            if (subResponse != null && subResponse.getReturnCode() == ReturnCodeEnum.SUCCESS.getValue()) {
                String rawValue = StringUtil.toJsonString(subResponse.getData());
                List<CompanyUserModel> companyUserModelList =
                        StringUtil.json2Collection(rawValue, new TypeToken<List<CompanyUserModel>>() {}.getType());

                Map<String, Integer> statusMap = new HashMap<>();
                for (CompanyUserModel companyUserModel : companyUserModelList) {
                    statusMap.put(companyUserModel.getUsername(), companyUserModel.getStatus());
                }

                for (EmployeeInfo employee : employeeInfoList) {
                    ExtendedEmployeeInfo extendedEmployee = new ExtendedEmployeeInfo(employee);
                    extendedEmployee.setStatus(statusMap.getOrDefault(extendedEmployee.getUsername(), 0));
                    dataResponse.add(extendedEmployee);
                }
                response.setData(dataResponse);
            }
            else {
                throw new Exception(
                        "gatewayService.getCompanyUsers response = " + StringUtil.toJsonString(subResponse));
            }

        } catch (Exception e) {
            log.error("[getAllUser] [{}] ex", companyId, e);
            response = new BaseResponse<>(e);
        }
        return response;
    }

    @ApiOperation(value = "Thêm nhân viên mới", response = EmployeeInfo.class)
    @PostMapping("/users")
    public BaseResponse createEmployee(@RequestBody EmployeeInfo request) {
        BaseResponse<EmployeeInfo> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        response.setReturnMessage("Thêm nhân viên mới thành công");
        try {
            String error = request.isValid();
            if (!error.isEmpty()) {
                response = new BaseResponse<>(ReturnCodeEnum.PARAM_DATA_INVALID);
                response.setReturnMessage(error);
                return response;
            }
            request.setAvatar(Constant.USER_DEFAULT_AVATAR);

            EmployeeInfo dataResponse = employeeInfoService.create(request);
            if (dataResponse != null) {
                CompanyUserModel companyUserModel = new CompanyUserModel();
                companyUserModel.setCompanyId(request.getCompanyId());
                companyUserModel.setUsername(request.getUsername());
                companyUserModel.setPhoneNumber(request.getPhoneNumber());
                companyUserModel.setEmail(request.getEmail());
                companyUserModel.setStatus(1);
                companyUserModel.setPassword("123456");

                BaseResponse subResponse = gatewayService.createCompanyUser(companyUserModel);
                if (subResponse == null || subResponse.getReturnCode() != ReturnCodeEnum.SUCCESS.getValue()) {
                    log.error(
                            "\nRollback on creating new Employee, response = " + StringUtil.toJsonString(subResponse));
                    employeeInfoService.delete(new EmployeeInfo.Key(request.getCompanyId(), request.getUsername()));
                    return subResponse;
                }
                else {
                    kafka.sendMessage(kafka.getBuzConfig().getEventCreateUser().getTopicName(), companyUserModel);

                    EmployeePermission permission = new EmployeePermission();
                    permission.setCompanyId(request.getCompanyId());
                    permission.setUsername(request.getUsername());
                    permission.setLineManager(StringUtil.EMPTY);
                    permission.setSubManagers(new ArrayList<>());
                    permission.setSubordinates(new ArrayList<>());
                    permission.setCanManageOffices(new ArrayList<>());
                    permission.setCanManageDepartments(new ArrayList<>());
                    permission.setManagerRole(StringUtil.EMPTY);
                    employeePermissionService.create(permission);
                }
            }

            response.setData(dataResponse);
        } catch (Exception e) {
            log.error("[createEmployee] {} ex", StringUtil.toJsonString(request), e);
            response = new BaseResponse<>(e);
        }
        return response;
    }

    @ApiOperation(value = "Cập nhật thông tin của nhân viên", response = EmployeeInfo.class)
    @PutMapping("/users/{companyId}/{username}")
    public BaseResponse updateEmployeeInfo(@RequestBody EmployeeInfo request,
                                           @PathVariable String companyId,
                                           @PathVariable String username) {
        BaseResponse<EmployeeInfo> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        response.setReturnMessage("Cập nhật thông tin nhân viên thành công");
        boolean needUpdate = false;
        try {
            String error = request.isValid();
            if (!error.isEmpty()) {
                response = new BaseResponse<>(ReturnCodeEnum.PARAM_DATA_INVALID);
                response.setReturnMessage(error);
                return response;
            }

            EmployeeInfo employeeInfo = employeeInfoService.findById(new EmployeeInfo.Key(companyId, username));
            if (employeeInfo == null) {
                response = new BaseResponse<>(ReturnCodeEnum.DATA_NOT_FOUND);
                response.setReturnMessage("Không tìm thấy nhân viên " + username);
                return response;
            }

            request.setCompanyId(companyId);
            request.setUsername(username);
            request.setAvatar(employeeInfo.getAvatar());

            if (!request.getEmail().equals(employeeInfo.getEmail()) ||
                    !request.getPhoneNumber().equals(employeeInfo.getPhoneNumber())) {
                needUpdate = true;
            }

            employeeInfo = employeeInfoService.update(request);
            response.setData(employeeInfo);
        } catch (Exception e) {
            log.error("[updateEmployeeInfo] {} ex", StringUtil.toJsonString(request), e);
            response = new BaseResponse<>(e);
        } finally {
            if (response.getReturnCode() == ReturnCodeEnum.SUCCESS.getValue()) {
                // send kafka
                if (needUpdate) {
                    kafka.sendMessage(kafka.getBuzConfig().getEventUpdateEmployeeInfo().topicName, request);
                }
            }
        }
        return response;
    }

    @ApiOperation(value = "Khóa tài khoản của nhân viên", response = BaseResponse.class)
    @PutMapping("/users/status/{companyId}/{username}/{newStatus}")
    public BaseResponse updateAccountStatus(@PathVariable String companyId,
                                            @PathVariable String username,
                                            @PathVariable int newStatus,
                                            @ApiParam(hidden = true) @RequestHeader(Constant.USERNAME) String actor
    ) {
        BaseResponse response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        response.setReturnMessage("Khóa tài khoản nhân viên thành công");
        try {
            if (username.equals(actor)) {
                response = new BaseResponse(ReturnCodeEnum.PERMISSION_DENIED);
                response.setReturnMessage("Không thể tự khóa / mở khóa tài khoản của bạn");
                return response;
            }

            if (newStatus != 0 && newStatus != 1) {
                response = new BaseResponse(ReturnCodeEnum.PARAM_DATA_INVALID);
                response.setReturnMessage("Trạng thái [" + newStatus + "] không hợp lệ");
                return response;
            }

            EmployeeInfo employeeInfo = employeeInfoService.findById(new EmployeeInfo.Key(companyId, username));
            if (employeeInfo == null) {
                response = new BaseResponse<>(ReturnCodeEnum.DATA_NOT_FOUND);
                response.setReturnMessage("Không tìm thấy nhân viên " + username);
                return response;
            }

            CompanyUserModel model = new CompanyUserModel();
            model.setCompanyId(companyId);
            model.setUsername(username);
            model.setStatus(newStatus);

            return gatewayService.updateCompanyUserStatus(model);

        } catch (Exception e) {
            log.error("[updateAccountStatus] [{}-{}-{}] ex", companyId, username, newStatus, e);
            response = new BaseResponse<>(e);
        }
        return response;
    }
}
