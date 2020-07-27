package htcc.log.service.controller;

import htcc.common.component.LoggingConfiguration;
import htcc.common.component.kafka.KafkaProducerService;
import htcc.common.constant.*;
import htcc.common.entity.base.BaseResponse;
import htcc.common.entity.checkin.CheckInLogEntity;
import htcc.common.entity.checkin.CheckOutLogEntity;
import htcc.common.entity.checkin.CheckinModel;
import htcc.common.entity.checkin.UpdateCheckInStatusModel;
import htcc.common.entity.icon.NotificationIconConfig;
import htcc.common.entity.notification.NotificationModel;
import htcc.common.util.StringUtil;
import htcc.log.service.entity.jpa.LogCounter;
import htcc.log.service.repository.CheckInLogRepository;
import htcc.log.service.repository.LogCounterRepository;
import htcc.log.service.service.icon.IconService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Log4j2
@RequestMapping("/internal/logs")
public class CheckInLogController {

    @Autowired
    private CheckInLogRepository repo;

    @Autowired
    private LogCounterRepository logCounterRepo;

    @Autowired
    private IconService iconService;

    @Autowired
    private KafkaProducerService kafka;


    @GetMapping("/checkin/{companyId}/{username}/{yyyyMMdd}")
    public BaseResponse getCheckInLog(@PathVariable String companyId,
                                      @PathVariable String username,
                                      @PathVariable String yyyyMMdd){
        BaseResponse<List<CheckinModel>> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        try {
            List<CheckInLogEntity> data = repo.getCheckInLog(companyId, username, yyyyMMdd);
            if (data == null) {
                return new BaseResponse(ReturnCodeEnum.LOG_NOT_FOUND);
            }

            List<CheckinModel> dataResponse = data.stream()
                    .map(CheckinModel::new)
                    .collect(Collectors.toList());

            response.setData(dataResponse);
        } catch (Exception e) {
            log.error(String.format("[getCheckInLog] [%s-%s-%s] ex", companyId, username, yyyyMMdd), e);
            return new BaseResponse<>(e);
        }
        return response;
    }

    @GetMapping("/checkout/{companyId}/{username}/{yyyyMMdd}")
    public BaseResponse getCheckOutLog(@PathVariable String companyId,
                                      @PathVariable String username,
                                      @PathVariable String yyyyMMdd){
        BaseResponse<List<CheckinModel>> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        try {
            List<CheckOutLogEntity> data = repo.getCheckOutLog(companyId, username, yyyyMMdd);
            if (data == null) {
                return new BaseResponse(ReturnCodeEnum.LOG_NOT_FOUND);
            }

            List<CheckinModel> dataResponse = data.stream()
                    .map(CheckinModel::new)
                    .collect(Collectors.toList());

            response.setData(dataResponse);

        } catch (Exception e) {
            log.error(String.format("[getCheckOutLog] [%s-%s-%s] ex", companyId, username, yyyyMMdd), e);
            return new BaseResponse<>(e);
        }
        return response;
    }

    @GetMapping("/checkin/pending")
    public BaseResponse getPendingCheckInLog(@RequestParam String companyId,
                                       @RequestParam String yyyyMM){
        BaseResponse<List<CheckinModel>> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        try {
            List<CheckinModel> dataResponse = repo.getPendingLog(companyId, yyyyMM);
            if (dataResponse == null) {
                throw new Exception("repo.getPendingLog return null");
            }

            response.setData(dataResponse);

        } catch (Exception e) {
            log.error(String.format("[getPendingCheckInLog] [%s-%s] ex", companyId, yyyyMM), e);
            return new BaseResponse<>(e);
        }
        return response;
    }

    @GetMapping("/checkin/pending/count")
    public BaseResponse countPendingCheckIn(@RequestParam String companyId){
        BaseResponse<Integer> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        int count = 0;
        try {
            final String logType = "CheckInLog";

            List<LogCounter> list = logCounterRepo.findByLogTypeAndParams(logType, companyId);
            if (!list.isEmpty()) {
                for (LogCounter counter : list) {
                    count += counter.count;
                }
            }

            response.setData(count);
        } catch (Exception e) {
            log.error(String.format("[countPendingCheckIn] [%s] ex", companyId), e);
            return new BaseResponse(e);
        }
        return response;
    }

    // update leaving request status for manager
    @PostMapping("/checkin/status")
    public BaseResponse updateCheckInStatus(@RequestBody UpdateCheckInStatusModel request){
        BaseResponse response = new BaseResponse(ReturnCodeEnum.SUCCESS);
        response.setReturnMessage("Phê duyệt yêu cầu thành công");
        int result = 0;
        String companyId = "";
        String username = "";
        try {
            CheckinModel oldEnt = repo.getOneCheckInLog(request);
            if (oldEnt == null) {
                log.error("[repo.getOneCheckInLog] {} return null", StringUtil.toJsonString(request));
                response = new BaseResponse<>(ReturnCodeEnum.LOG_NOT_FOUND);
                return response;
            }

            if (oldEnt.getStatus() == ComplaintStatusEnum.DONE.getValue() ||
                    oldEnt.getStatus() == ComplaintStatusEnum.REJECTED.getValue()) {
                log.error("[repo.getOneLeavingRequest] oldEntity {}: status = [{}]", oldEnt.getCheckInId(), oldEnt.getStatus());
                response = new BaseResponse(ReturnCodeEnum.PARAM_DATA_INVALID);
                response.setReturnMessage("Yêu cầu phê duyệt đã được xử lý trước đó");
                return response;
            }
            companyId = oldEnt.getCompanyId();
            username = oldEnt.getUsername();

            result = repo.updateCheckInStatus(request, oldEnt.getCompanyId());
            if (result != 1) {
                throw new Exception("updateCheckInStatus return " + result);
            }
        } catch (Exception e){
            log.error(String.format("[updateLeavingRequestStatus] [%s] ex", StringUtil.toJsonString(request)), e);
            response = new BaseResponse<>(e);
        } finally {
            if (response.getReturnCode() == ReturnCodeEnum.SUCCESS.getValue()) {
                NotificationModel model = new NotificationModel();
                model.setRequestId(LoggingConfiguration.getTraceId());
                model.setSourceClientId(0);
                model.setTargetClientId(ClientSystemEnum.MOBILE.getValue());
                model.setReceiverType(2);
                model.setSender("Hệ thống");
                model.setCompanyId(companyId);
                model.setUsername(username);
                model.setSendTime(System.currentTimeMillis());
                int screenId = ScreenEnum.CHECKIN.getValue();
                model.setScreenId(screenId);
                NotificationIconConfig icon = iconService.getIcon(screenId);
                if (icon != null) {
                    model.setIconId(icon.getIconId());
                    model.setIconUrl(icon.getIconURL());
                }
                model.setStatus(NotificationStatusEnum.INIT.getValue());
                model.setHasRead(false);
                model.setTitle("Trạng thái yêu cầu điểm danh");
                model.setContent("Yêu cầu điểm danh của bạn đã được xử lý. Vào xem ngay thôi");

                kafka.sendMessage(kafka.getBuzConfig().getEventPushNotification().getTopicName(), model);
            }
        }

        return response;
    }
}
