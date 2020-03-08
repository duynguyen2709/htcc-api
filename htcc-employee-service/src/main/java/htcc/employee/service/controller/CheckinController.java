package htcc.employee.service.controller;

import constant.ReturnCodeEnum;
import entity.base.BaseResponse;
import htcc.employee.service.entity.checkin.CheckinResponse;
import io.swagger.annotations.Api;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Employee APIs",
     description = "API điểm danh của nhân viên")
@RestController
@RequestMapping("/public")
@Log4j2
public class CheckinController {

    @GetMapping("/checkin")
    public BaseResponse<CheckinResponse> getCheckinInfo(@RequestParam(required = true) String companyId,
                                                        @RequestParam(required = true) String username) {
        BaseResponse<CheckinResponse> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        try {
            response.data = new CheckinResponse();
        } catch (Exception e){
            log.error(String.format("getCheckinInfo [%s - %s] ex", companyId, username), e);
            response = new BaseResponse<>(e);
        }
        return response;
    }
}
