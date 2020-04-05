package htcc.log.service.controller;

import htcc.common.constant.ReturnCodeEnum;
import htcc.common.entity.base.BaseResponse;
import htcc.common.entity.checkin.CheckinModel;
import htcc.common.entity.leavingrequest.LeavingRequestModel;
import htcc.common.entity.log.CheckInLogEntity;
import htcc.common.entity.log.CheckOutLogEntity;
import htcc.common.entity.log.LeavingRequestLogEntity;
import htcc.log.service.repository.CheckInLogRepository;
import htcc.log.service.repository.LeavingRequestLogRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Log4j2
@RequestMapping("/internal/logs")
public class LeavingRequestLogController {

    @Autowired
    private LeavingRequestLogRepository repo;

    @GetMapping("/leaving/{companyId}/{username}/{yyyy}")
    public BaseResponse getLeavingRequestLog(@PathVariable String companyId,
                                      @PathVariable String username,
                                      @PathVariable String yyyy){
        BaseResponse response = new BaseResponse(ReturnCodeEnum.SUCCESS);
        try {
            List<LeavingRequestModel> listModel = repo.getLeavingRequestLog(companyId, username, yyyy)
                    .stream()
                    .map(LeavingRequestModel::new)
                    .collect(Collectors.toList());

            response.setData(listModel);
        } catch (Exception e) {
            log.error(String.format("[getLeavingRequestLog] [%s-%s-%s] ex", companyId, username, yyyy), e);
            return new BaseResponse(e);
        }
        return response;
    }

}
