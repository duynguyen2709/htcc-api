package htcc.employee.service.controller.home;

import htcc.common.constant.ReturnCodeEnum;
import htcc.common.entity.base.BaseResponse;
import htcc.common.entity.home.HomeResponse;
import htcc.common.util.DateTimeUtil;
import htcc.employee.service.service.ComplaintService;
import htcc.employee.service.service.LeavingRequestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "API của quản lý",
     description = "API ở màn hình chính")
@RestController
@Log4j2
public class HomeController {

    @Autowired
    private ComplaintService complaintService;

    @Autowired
    private LeavingRequestService leavingRequestService;


    @ApiOperation(value = "API Home", response = HomeResponse.class)
    @GetMapping("/home/{companyId}")
    public BaseResponse home(@ApiParam(value = "[Path] Mã công ty", required = true)
                                                  @PathVariable String companyId) {
        BaseResponse response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        try {
            String yyyyMM = DateTimeUtil.parseTimestampToString(System.currentTimeMillis(), "yyyyMM");

            HomeResponse data = new HomeResponse();
            countPendingComplaint(data, companyId);
            countPendingLeavingRequest(data, companyId);
            response.data = data;

        } catch (Exception e) {
            log.error(String.format("home [%s] ex", companyId), e);
            response = new BaseResponse<>(e);
        }
        return response;
    }



    private void countPendingComplaint(HomeResponse data, String companyId){
        int count = 0;
        try {
            BaseResponse response = complaintService.countPendingComplaint(companyId);
            if (response != null && response.getReturnCode() == ReturnCodeEnum.SUCCESS.getValue()){
                count = (int) response.getData();
            }
        } catch (Exception e) {
            log.error("[countPendingComplaint] {} ex", companyId, e);
        }
        data.setPendingComplaint(count);
    }



    private void countPendingLeavingRequest(HomeResponse data, String companyId){
        int count = 0;
        try {
            BaseResponse response = leavingRequestService.countPendingLeavingRequest(companyId);
            if (response != null && response.getReturnCode() == ReturnCodeEnum.SUCCESS.getValue()){
                count = (int) response.getData();
            }
        } catch (Exception e) {
            log.error("[countPendingLeavingRequest] {} ex", companyId, e);
        }
        data.setPendingLeavingRequest(count);
    }
}
