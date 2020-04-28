package htcc.employee.service.controller.home;

import htcc.common.constant.Constant;
import htcc.common.constant.ReturnCodeEnum;
import htcc.common.entity.base.BaseResponse;
import htcc.common.entity.home.EmployeeHomeResponse;
import htcc.common.entity.home.HomeResponse;
import htcc.common.entity.jpa.Office;
import htcc.common.util.DateTimeUtil;
import htcc.employee.service.config.DbStaticConfigMap;
import htcc.employee.service.service.ComplaintService;
import htcc.employee.service.service.LeavingRequestService;
import htcc.employee.service.service.LogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Api(tags = "API ở màn hình chính của nhân viên")
@RestController
@Log4j2
public class EmployeeHomeController {

    @Autowired
    private LogService logService;


    @ApiOperation(value = "API Home", response = EmployeeHomeResponse.class)
    @GetMapping("/home/employee/{companyId}/{username}")
    public BaseResponse home(@ApiParam(value = "[Path] Mã công ty", defaultValue = "VNG", required = true)
                             @PathVariable String companyId,
                             @ApiParam(value = "[Path] Tên đăng nhập", defaultValue = "admin", required = true)
                             @PathVariable String username) {
        BaseResponse<EmployeeHomeResponse> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        try {
            EmployeeHomeResponse data = new EmployeeHomeResponse();
            countUnreadNotifications(data, companyId, username);
            response.setData(data);
        } catch (Exception e) {
            log.error(String.format("home [%s] ex", companyId), e);
            response = new BaseResponse<>(e);
        }
        return response;
    }

    private void countUnreadNotifications(EmployeeHomeResponse data, String companyId, String username){
        int count = 0;
        try {
            BaseResponse response = logService.countUnreadNotificationForMobile(companyId, username);
            if (response != null && response.getReturnCode() == ReturnCodeEnum.SUCCESS.getValue()){
                count = (int) response.getData();
            }
        } catch (Exception e) {
            log.error("[countUnreadNotifications] [{} - {}] ex", companyId, username, e);
        }
        data.setUnreadNotifications(count);
    }
}
