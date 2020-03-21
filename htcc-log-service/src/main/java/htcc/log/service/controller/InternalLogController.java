package htcc.log.service.controller;

import htcc.common.constant.ReturnCodeEnum;
import htcc.common.entity.base.BaseResponse;
import htcc.common.entity.checkin.CheckinModel;
import htcc.common.entity.log.CheckInLogEntity;
import htcc.common.entity.log.CheckOutLogEntity;
import htcc.common.util.StringUtil;
import htcc.log.service.repository.CheckInLogRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
@RequestMapping("/internal/logs")
public class InternalLogController {

    @Autowired
    private CheckInLogRepository repo;

    @GetMapping("/checkin")
    public BaseResponse getCheckInLog(@RequestParam String companyId,
                                      @RequestParam String username,
                                      @RequestParam String yyyyMMdd){
        BaseResponse response = new BaseResponse(ReturnCodeEnum.SUCCESS);
        try {
            CheckInLogEntity data = repo.getCheckInLog(companyId, username, yyyyMMdd);
            if (data == null) {
                return new BaseResponse(ReturnCodeEnum.LOG_NOT_FOUND);
            }

            CheckinModel model = new CheckinModel(data);
            response.data = model;

        } catch (Exception e) {
            log.error(String.format("[getCheckInLog] [%s-%s-%s] ex", companyId, username, yyyyMMdd), e);
            return new BaseResponse(e);
        }
        return response;
    }

    @GetMapping("/checkout")
    public BaseResponse getCheckOutLog(@RequestParam String companyId,
                                      @RequestParam String username,
                                      @RequestParam String yyyyMMdd){
        BaseResponse response = new BaseResponse(ReturnCodeEnum.SUCCESS);
        try {
            CheckOutLogEntity data = repo.getCheckOutLog(companyId, username, yyyyMMdd);
            if (data == null) {
                return new BaseResponse(ReturnCodeEnum.LOG_NOT_FOUND);
            }

            CheckinModel model = new CheckinModel(data);
            response.data = model;

        } catch (Exception e) {
            log.error(String.format("[getCheckOutLog] [%s-%s-%s] ex", companyId, username, yyyyMMdd), e);
            return new BaseResponse(e);
        }
        return response;
    }
}
