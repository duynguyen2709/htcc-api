package htcc.admin.service.controller;

import htcc.admin.service.service.ComplaintService;
import htcc.common.constant.ReturnCodeEnum;
import htcc.common.entity.base.BaseResponse;
import htcc.common.entity.home.AdminHomeResponse;
import htcc.common.util.DateTimeUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "API để xử lý phản hồi/ khiếu nại")
@RestController
@Log4j2
public class HomeController {

    @Autowired
    private ComplaintService complaintService;

    @ApiOperation(value = "API ở màn hình Home", response = AdminHomeResponse.class)
    @GetMapping("/home")
    public BaseResponse home() {
        BaseResponse<AdminHomeResponse> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        try {
            String yyyyMM = DateTimeUtil.parseTimestampToString(System.currentTimeMillis(), "yyyyMM");

            AdminHomeResponse data = new AdminHomeResponse();
            countPendingComplaint(data);
            response.setData(data);

        } catch (Exception e) {
            log.error("[home] ex", e);
            response = new BaseResponse<>(e);
        }
        return response;
    }

    private void countPendingComplaint(AdminHomeResponse data){
        int count = 0;
        try {
            BaseResponse response = complaintService.countPendingComplaint();
            if (response != null && response.getReturnCode() == ReturnCodeEnum.SUCCESS.getValue()){
                count = (int) response.getData();
            }
        } catch (Exception e) {
            log.error("[countPendingComplaint] ex", e);
        }
        data.setPendingComplaint(count);
    }
}
