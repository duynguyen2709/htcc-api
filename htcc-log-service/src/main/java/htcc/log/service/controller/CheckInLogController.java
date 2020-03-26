package htcc.log.service.controller;

import htcc.common.constant.ReturnCodeEnum;
import htcc.common.entity.base.BaseResponse;
import htcc.common.entity.checkin.CheckinModel;
import htcc.common.entity.complaint.ComplaintModel;
import htcc.common.entity.complaint.ComplaintResponse;
import htcc.common.entity.log.CheckInLogEntity;
import htcc.common.entity.log.CheckOutLogEntity;
import htcc.common.entity.log.ComplaintLogEntity;
import htcc.common.util.StringUtil;
import htcc.log.service.repository.CheckInLogRepository;
import htcc.log.service.repository.ComplaintLogRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Log4j2
@RequestMapping("/internal/logs")
public class CheckInLogController {

    @Autowired
    private CheckInLogRepository repo;

    @GetMapping("/checkin/{companyId}/{username}/{yyyyMMdd}")
    public BaseResponse getCheckInLog(@PathVariable String companyId,
                                      @PathVariable String username,
                                      @PathVariable String yyyyMMdd){
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

    @GetMapping("/checkout/{companyId}/{username}/{yyyyMMdd}")
    public BaseResponse getCheckOutLog(@PathVariable String companyId,
                                      @PathVariable String username,
                                      @PathVariable String yyyyMMdd){
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
