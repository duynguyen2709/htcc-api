package htcc.log.service.controller;

import htcc.common.component.LoggingConfiguration;
import htcc.common.component.kafka.KafkaProducerService;
import htcc.common.constant.*;
import htcc.common.entity.base.BaseResponse;
import htcc.common.entity.complaint.ComplaintLogEntity;
import htcc.common.entity.complaint.ComplaintModel;
import htcc.common.entity.complaint.ResubmitComplaintModel;
import htcc.common.entity.complaint.UpdateComplaintStatusModel;
import htcc.common.entity.icon.NotificationIconConfig;
import htcc.common.entity.notification.NotificationModel;
import htcc.common.util.StringUtil;
import htcc.log.service.entity.jpa.LogCounter;
import htcc.log.service.repository.ComplaintLogRepository;
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
public class ComplaintLogController {

    @Autowired
    private ComplaintLogRepository complaintRepo;

    @Autowired
    private LogCounterRepository logCounterRepo;

    @Autowired
    private IconService iconService;

    @Autowired
    private KafkaProducerService kafka;


    @GetMapping("/complaint/{companyId}/{username}/{yyyyMM}")
    public BaseResponse getComplaintLog(@PathVariable String companyId,
                                        @PathVariable String username,
                                        @PathVariable String yyyyMM){
        BaseResponse response = new BaseResponse(ReturnCodeEnum.SUCCESS);
        try {
            List<ComplaintLogEntity> data = complaintRepo.getComplaintLog(companyId, username, yyyyMM);
            if (data == null) {
                return new BaseResponse(ReturnCodeEnum.LOG_NOT_FOUND);
            }

            List<ComplaintModel> listResponse = data.stream().map(ComplaintModel::new).collect(Collectors.toList());

            response.data = listResponse;

        } catch (Exception e) {
            log.error(String.format("[getComplaintLog] [%s-%s-%s] ex", companyId, username, yyyyMM), e);
            return new BaseResponse(e);
        }
        return response;
    }



    // get complaint log by receiverType, call by web to handle complaint
    @GetMapping("/complaint/{receiverType}/{yyyyMM}")
    public BaseResponse getComplaintLogByReceiverType(@PathVariable int receiverType,
                                                    @PathVariable String yyyyMM,
                                                      @RequestParam(required = false) String companyId){
        BaseResponse response = new BaseResponse(ReturnCodeEnum.SUCCESS);
        try {
            List<ComplaintLogEntity> data = complaintRepo.getComplaintLogByReceiverType(receiverType, yyyyMM);
            if (data == null) {
                return new BaseResponse(ReturnCodeEnum.LOG_NOT_FOUND);
            }

            List<ComplaintModel> listResponse = data.stream().map(ComplaintModel::new).collect(Collectors.toList());

            if (!StringUtil.valueOf(companyId).isEmpty()) {
                listResponse = listResponse.stream()
                        .filter(c -> c.getCompanyId().equals(companyId))
                        .collect(Collectors.toList());
            }

            response.data = listResponse;

        } catch (Exception e) {
            log.error(String.format("[getComplaintLogByReceiverType] [%s-%s] ex", receiverType, yyyyMM), e);
            return new BaseResponse(e);
        }
        return response;
    }




    @GetMapping("/complaint/count/{receiverType}")
    public BaseResponse countPendingComplaintLogByReceiverType(@PathVariable int receiverType,
                                                      @RequestParam(required = false) String companyId){
        BaseResponse response = new BaseResponse(ReturnCodeEnum.SUCCESS);
        int count = 0;
        try {
            final String logType = "ComplaintLog";
            String params = "1";

            if (receiverType == 2) {
                params = "2-" + StringUtil.valueOf(companyId);
            }

            List<LogCounter> list = logCounterRepo.findByLogTypeAndParams(logType, params);
            if (!list.isEmpty()) {
                for (LogCounter counter : list) {
                    count += counter.count;
                }
            }

            response.data = count;
        } catch (Exception e) {
            log.error(String.format("[countPendingComplaintLogByReceiverType] [%s-%s] ex", receiverType, StringUtil.valueOf(companyId)), e);
            return new BaseResponse(e);
        }
        return response;
    }




    // update complaint status, call by web
    @PostMapping("/complaint/status")
    public BaseResponse updateComplaintStatus(@RequestBody UpdateComplaintStatusModel request){
        BaseResponse response = new BaseResponse(ReturnCodeEnum.SUCCESS);
        response.setReturnMessage("Xử lý khiếu nại thành công");
        String companyId = "";
        String username = "";
        boolean needSendNoti = true;
        try {
            ComplaintLogEntity oldEnt = complaintRepo.getComplaint(request);
            if (oldEnt == null) {
                log.warn("[complaintRepo.getComplaint] {} return null", StringUtil.toJsonString(request));
                response = new BaseResponse<>(ReturnCodeEnum.LOG_NOT_FOUND);
                return response;
            }

            if (oldEnt.getStatus() == ComplaintStatusEnum.DONE.getValue() ||
                    oldEnt.getStatus() == ComplaintStatusEnum.REJECTED.getValue()) {
                log.warn("[complaintRepo.getComplaint] oldEntity {}: status = [{}]", oldEnt.getComplaintId(), oldEnt.getStatus());
                response = new BaseResponse<>(ReturnCodeEnum.PARAM_DATA_INVALID);
                response.setReturnMessage("Khiếu nại đã được xử lý trước đó");
                return response;
            }
            companyId = oldEnt.getCompanyId();
            username = oldEnt.getUsername();
            needSendNoti = oldEnt.getIsAnonymous() == 0;

            List<String> oldResponses = StringUtil.json2Collection(oldEnt.response, StringUtil.LIST_STRING_TYPE);
            oldResponses.add(request.getResponse());

            request.setResponse(StringUtil.toJsonString(oldResponses));

            complaintRepo.updateComplaintLogStatus(request);
        } catch (Exception e){
            log.error(String.format("[updateLeavingRequestStatus] [%s] ex", StringUtil.toJsonString(request)), e);
            response = new BaseResponse(e);
        } finally {
            if (response.getReturnCode() == ReturnCodeEnum.SUCCESS.getValue()) {
                try {
                    if (needSendNoti) {
                        NotificationModel model = new NotificationModel();
                        model.setRequestId(LoggingConfiguration.getTraceId());
                        model.setSourceClientId(0);
                        model.setTargetClientId(ClientSystemEnum.MOBILE.getValue());
                        model.setReceiverType(2);
                        model.setSender("Hệ thống");
                        model.setCompanyId(companyId);
                        model.setUsername(username);
                        model.setSendTime(System.currentTimeMillis());

                        int screenId = ScreenEnum.COMPLAINT.getValue();
                        model.setScreenId(screenId);
                        NotificationIconConfig icon = iconService.getIcon(screenId);
                        if (icon != null) {
                            model.setIconId(icon.getIconId());
                            model.setIconUrl(icon.getIconURL());
                        }

                        model.setStatus(NotificationStatusEnum.INIT.getValue());
                        model.setHasRead(false);
                        model.setTitle("Trạng thái đơn khiếu nại");
                        model.setContent("Đơn khiếu nại của bạn đã được xử lý. Vào xem ngay thôi");

                        kafka.sendMessage(kafka.getBuzConfig().getEventPushNotification().getTopicName(), model);
                    }
                } catch (Exception e) {
                    log.error(String.format("[updateLeavingRequestStatus] [%s] ex", StringUtil.toJsonString(request)), e);
                }
            }
        }

        return response;
    }




    // resubmit complaint, call by mobile
    @PostMapping("/complaint/resubmit")
    public BaseResponse resubmitComplaint(@RequestBody ResubmitComplaintModel request){
        BaseResponse response = new BaseResponse(ReturnCodeEnum.SUCCESS);
        response.setReturnMessage("Cập nhật khiếu nại thành công");

        try {
            UpdateComplaintStatusModel temp = new UpdateComplaintStatusModel();
            temp.setComplaintId(request.getComplaintId());
            temp.setYyyyMM(request.getYyyyMM());

            ComplaintLogEntity logEntity = complaintRepo.getComplaint(temp);
            if (logEntity == null) {
                log.warn("[complaintRepo.getComplaint] {} return null", StringUtil.toJsonString(request));
                response = new BaseResponse<>(ReturnCodeEnum.LOG_NOT_FOUND);
                return response;
            }

            if (logEntity.getStatus() == ComplaintStatusEnum.PROCESSING.getValue()) {
                log.warn("[complaintRepo.getComplaint] oldEntity {}: status = [{}]", logEntity.getComplaintId(), logEntity.getStatus());
                response = new BaseResponse<>(ReturnCodeEnum.PARAM_DATA_INVALID);
                response.setReturnMessage("Khiếu nại chưa được xử lý");
                return response;
            }

            ComplaintModel model = new ComplaintModel(logEntity);
            model.getContent().add(request.getContent());
            model.setStatus(ComplaintStatusEnum.PROCESSING.getValue());

            complaintRepo.resubmitComplaint(request.getYyyyMM(), request.getComplaintId(),
                    StringUtil.toJsonString(model.getContent()));

        } catch (Exception e){
            log.error(String.format("[resubmitComplaint] [%s] ex", StringUtil.toJsonString(request)), e);
            return new BaseResponse(e);
        }

        return response;
    }
}
