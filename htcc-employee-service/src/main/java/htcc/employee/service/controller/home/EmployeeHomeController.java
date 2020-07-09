package htcc.employee.service.controller.home;

import com.google.gson.reflect.TypeToken;
import htcc.common.constant.ClientSystemEnum;
import htcc.common.constant.ReturnCodeEnum;
import htcc.common.entity.base.BaseResponse;
import htcc.common.entity.home.EmployeeHomeResponse;
import htcc.common.entity.jpa.Company;
import htcc.common.util.StringUtil;
import htcc.employee.service.service.LogService;
import htcc.employee.service.service.jpa.CompanyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Api(tags = "API ở màn hình chính của nhân viên")
@RestController
@Log4j2
public class EmployeeHomeController {

    @Autowired
    private LogService logService;

    @Autowired
    private CompanyService companyService;

    @ApiOperation(value = "API Home", response = EmployeeHomeResponse.class)
    @GetMapping("/home/employee/{companyId}/{username}")
    public BaseResponse home(@ApiParam(value = "[Path] Mã công ty", defaultValue = "VNG", required = true)
                             @PathVariable String companyId,
                             @ApiParam(value = "[Path] Tên đăng nhập", defaultValue = "admin", required = true)
                             @PathVariable String username,
                             @RequestParam(required = false) String screens) {
        BaseResponse<EmployeeHomeResponse> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        try {
            EmployeeHomeResponse data = new EmployeeHomeResponse();
            countUnreadNotifications(data, companyId, username);
            selectDisplayScreens(data, companyId, username, screens);
            response.setData(data);
        } catch (Exception e) {
            log.error(String.format("home [%s] ex", companyId), e);
            response = new BaseResponse<>(e);
        }
        return response;
    }

    private void selectDisplayScreens(EmployeeHomeResponse data, String companyId, String username, String screens) throws Exception {
        Company company = companyService.findById(companyId);
        if (company == null) {
            throw new Exception("companyService.findById return null");
        }

        Set<Integer> displayScreens = new HashSet<>(StringUtil.json2Collection(company.getSupportedScreens(),
                new TypeToken<Set<Integer>>() {}.getType()));
        displayScreens.add(1);
        data.setDisplayScreens(new ArrayList<>(displayScreens));
    }

    private void countUnreadNotifications(EmployeeHomeResponse data, String companyId, String username){
        int count = 0;
        try {
            BaseResponse response = logService.countUnreadNotification(ClientSystemEnum.MOBILE.getValue(), companyId, username);
            if (response != null && response.getReturnCode() == ReturnCodeEnum.SUCCESS.getValue()){
                count = (int) response.getData();
            }
        } catch (Exception e) {
            log.error("[countUnreadNotifications] [{} - {}] ex", companyId, username, e);
        }
        data.setUnreadNotifications(count);
    }
}
