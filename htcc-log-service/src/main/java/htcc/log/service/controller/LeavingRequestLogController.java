package htcc.log.service.controller;

import htcc.common.component.LoggingConfiguration;
import htcc.common.component.kafka.KafkaProducerService;
import htcc.common.constant.*;
import htcc.common.entity.base.BaseResponse;
import htcc.common.entity.icon.NotificationIconConfig;
import htcc.common.entity.leavingrequest.LeavingRequest;
import htcc.common.entity.leavingrequest.LeavingRequestLogEntity;
import htcc.common.entity.leavingrequest.LeavingRequestModel;
import htcc.common.entity.leavingrequest.UpdateLeavingRequestStatusModel;
import htcc.common.entity.notification.NotificationModel;
import htcc.common.util.StringUtil;
import htcc.log.service.entity.jpa.LogCounter;
import htcc.log.service.repository.LeavingRequestLogRepository;
import htcc.log.service.repository.LogCounterRepository;
import htcc.log.service.service.icon.IconService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@RestController
@Log4j2
@RequestMapping("/internal/logs")
public class LeavingRequestLogController {

    @Autowired
    private LeavingRequestLogRepository repo;

    @Autowired
    private LogCounterRepository logCounterRepo;

    @Autowired
    private KafkaProducerService kafka;

    @Autowired
    private IconService iconService;

    @GetMapping("/leaving")
    public BaseResponse getOneLeavingRequestLog(@RequestParam String leavingRequestId,
                                                @RequestParam String yyyyMM) {
        BaseResponse<LeavingRequestModel> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        try {
            UpdateLeavingRequestStatusModel model = new UpdateLeavingRequestStatusModel();
            model.setYyyyMM(yyyyMM);
            model.setLeavingRequestId("#" + leavingRequestId);

            LeavingRequestLogEntity oldEnt = repo.getOneLeavingRequest(model);
            if (oldEnt == null) {
                log.warn("[repo.getOneLeavingRequest] {} return null", StringUtil.toJsonString(model));
                response = new BaseResponse<>(ReturnCodeEnum.LOG_NOT_FOUND);
                return response;
            }

            response.setData(new LeavingRequestModel(oldEnt));
        } catch (Exception e) {
            log.error(String.format("[getOneLeavingRequestLog] [#%s-%s] ex", leavingRequestId, yyyyMM), e);
            return new BaseResponse<>(e);
        }
        return response;
    }

    @GetMapping("/leaving/date")
    public BaseResponse getLeavingRequestByDate(@RequestParam String companyId,
                                             @RequestParam String username,
                                             @RequestParam String yyyyMMdd){
        BaseResponse<List<LeavingRequestModel>> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        try {
            List<LeavingRequestModel> listModel = repo.getLeavingRequestLogByDate(companyId, username, yyyyMMdd)
                    .stream()
                    .map(LeavingRequestModel::new)
                    .filter(new Predicate<LeavingRequestModel>() {
                        @Override
                        public boolean test(LeavingRequestModel leavingRequestModel) {
                            if (leavingRequestModel.getStatus() == ComplaintStatusEnum.REJECTED.getValue()) {
                                return false;
                            }

                            for (LeavingRequest.LeavingDayDetail detail : leavingRequestModel.getDetail()) {
                                if (detail.getDate().equals(yyyyMMdd)) {
                                    return true;
                                }
                            }
                            return false;
                        }
                    })
                    .collect(Collectors.toList());

            response.setData(listModel);
        } catch (Exception e) {
            log.error(String.format("[getLeavingRequestByDate] [%s-%s-%s] ex", companyId, username, yyyyMMdd), e);
            return new BaseResponse<>(e);
        }
        return response;
    }

    @GetMapping("/leaving/{companyId}/{username}/{yyyy}")
    public BaseResponse getLeavingRequestLog(@PathVariable String companyId,
                                      @PathVariable String username,
                                      @PathVariable String yyyy){
        BaseResponse<List<LeavingRequestModel>> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        try {
            List<LeavingRequestModel> listModel = repo.getLeavingRequestLog(companyId, username, yyyy)
                    .stream()
                    .map(LeavingRequestModel::new)
                    .collect(Collectors.toList());

            response.setData(listModel);
        } catch (Exception e) {
            log.error(String.format("[getLeavingRequestLog] [%s-%s-%s] ex", companyId, username, yyyy), e);
            return new BaseResponse<>(e);
        }
        return response;
    }




    @GetMapping("/leaving/{companyId}/{yyyyMM}")
    public BaseResponse getListLeavingRequestLogByCompany(@PathVariable String yyyyMM,
                                                      @PathVariable String companyId){
        BaseResponse<List<LeavingRequestModel>> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        try {
            List<LeavingRequestLogEntity> data = repo.getListLeavingRequestLogByCompany(companyId, yyyyMM);
            if (data == null) {
                return new BaseResponse(ReturnCodeEnum.LOG_NOT_FOUND);
            }

            List<LeavingRequestModel> listResponse = data.stream().map(LeavingRequestModel::new).collect(Collectors.toList());

            if (!StringUtil.valueOf(companyId).isEmpty()) {
                listResponse = listResponse.stream()
                        .filter(c -> c.getCompanyId().equals(companyId))
                        .collect(Collectors.toList());
            }

            response.setData(listResponse);

        } catch (Exception e) {
            log.error(String.format("[getListLeavingRequestLogByCompany] [%s-%s] ex", companyId, yyyyMM), e);
            return new BaseResponse<>(e);
        }
        return response;
    }




    @GetMapping("/leaving/count/{companyId}")
    public BaseResponse countPendingLeavingRequestLog(@PathVariable String companyId){
        BaseResponse<Integer> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        int count = 0;
        try {
            final String logType = "LeavingRequestLog";

            List<LogCounter> list = logCounterRepo.findByLogTypeAndParams(logType, companyId);
            if (!list.isEmpty()) {
                for (LogCounter counter : list) {
                    count += counter.count;
                }
            }

            response.setData(count);
        } catch (Exception e) {
            log.error(String.format("[countPendingLeavingRequestLog] [%s] ex", companyId), e);
            return new BaseResponse(e);
        }
        return response;
    }




    // update leaving request status for manager
    @PostMapping("/leaving/status")
    public BaseResponse updateLeavingRequestStatus(@RequestBody UpdateLeavingRequestStatusModel request){
        BaseResponse response = new BaseResponse(ReturnCodeEnum.SUCCESS);
        response.setReturnMessage("Xử lý đơn nghỉ phép thành công");
        int result = 0;
        String companyId = "";
        String username = "";
        try {
            LeavingRequestLogEntity oldEnt = repo.getOneLeavingRequest(request);
            if (oldEnt == null) {
                log.error("[repo.getOneLeavingRequest] {} return null", StringUtil.toJsonString(request));
                response = new BaseResponse<>(ReturnCodeEnum.LOG_NOT_FOUND);
                return response;
            }

            if (oldEnt.getStatus() == ComplaintStatusEnum.DONE.getValue() ||
                    oldEnt.getStatus() == ComplaintStatusEnum.REJECTED.getValue()) {
                log.error("[repo.getOneLeavingRequest] oldEntity {}: status = [{}]", oldEnt.getLeavingRequestId(), oldEnt.getStatus());
                response = new BaseResponse(ReturnCodeEnum.PARAM_DATA_INVALID);
                response.setReturnMessage(String.format("Đơn nghỉ phép [%s] đã được xử lý trước đó", oldEnt.getLeavingRequestId()));
                return response;
            }

            companyId = oldEnt.getCompanyId();
            username = oldEnt.getUsername();

            result = repo.updateLeavingRequestLogStatus(request);
        } catch (Exception e){
            log.error(String.format("[updateLeavingRequestStatus] [%s] ex", StringUtil.toJsonString(request)), e);
            response = new BaseResponse(e);
        } finally {
            if (response.getReturnCode() == ReturnCodeEnum.SUCCESS.getValue()) {
                try {
                    NotificationModel model = new NotificationModel();
                    model.setRequestId(LoggingConfiguration.getTraceId());
                    model.setSourceClientId(0);
                    model.setTargetClientId(ClientSystemEnum.MOBILE.getValue());
                    model.setReceiverType(2);
                    model.setSender("Hệ thống");
                    model.setCompanyId(companyId);
                    model.setUsername(username);
                    model.setSendTime(System.currentTimeMillis());
                    int screenId = ScreenEnum.DAY_OFF.getValue();
                    model.setScreenId(screenId);
                    NotificationIconConfig icon = iconService.getIcon(screenId);
                    if (icon != null) {
                        model.setIconId(icon.getIconId());
                        model.setIconUrl(icon.getIconURL());
                    }
                    model.setStatus(NotificationStatusEnum.INIT.getValue());
                    model.setHasRead(false);
                    model.setTitle("Trạng thái đơn nghỉ phép " + request.getLeavingRequestId());
                    model.setContent("Đơn nghỉ phép của bạn đã được xử lý. Vào xem ngay thôi");

                    kafka.sendMessage(kafka.getBuzConfig().getEventPushNotification().getTopicName(), model);
                } catch (Exception e) {
                    log.error(String.format("[updateLeavingRequestStatus] [%s] ex", StringUtil.toJsonString(request)), e);
                }
            }
        }

        return response;
    }
}
