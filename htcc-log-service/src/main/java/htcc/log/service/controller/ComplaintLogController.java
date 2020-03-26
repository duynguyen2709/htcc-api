package htcc.log.service.controller;

import htcc.common.constant.ComplaintStatusEnum;
import htcc.common.constant.ReturnCodeEnum;
import htcc.common.entity.base.BaseResponse;
import htcc.common.entity.complaint.ComplaintModel;
import htcc.common.entity.complaint.UpdateComplaintStatusModel;
import htcc.common.entity.log.ComplaintLogEntity;
import htcc.common.util.StringUtil;
import htcc.log.service.repository.ComplaintLogRepository;
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





    @PostMapping("/complaint/status")
    public BaseResponse updateComplaintStatus(@RequestBody UpdateComplaintStatusModel request){
        BaseResponse response = new BaseResponse(ReturnCodeEnum.SUCCESS);
        response.setReturnMessage("Xử lý khiếu nại thành công");

        try {
            ComplaintLogEntity oldEnt = complaintRepo.getComplaint(request);
            if (oldEnt == null) {
                log.warn("[complaintRepo.getComplaint] {} return null", StringUtil.toJsonString(request));
                return new BaseResponse<>(ReturnCodeEnum.LOG_NOT_FOUND);
            }

            if (oldEnt.getStatus() == ComplaintStatusEnum.DONE.getValue() ||
            oldEnt.getStatus() == ComplaintStatusEnum.REJECTED.getValue()) {
                log.warn("[complaintRepo.getComplaint] oldEntity {}: status = [{}]", oldEnt.getComplaintId(), oldEnt.getStatus());
                return new BaseResponse<>(ReturnCodeEnum.PARAM_DATA_INVALID, "Khiếu nại đã được xử lý trước đó");
            }

            complaintRepo.updateComplaintLogStatus(request);
        } catch (Exception e){
            log.error(String.format("[updateComplaintStatus] [%s] ex", StringUtil.toJsonString(request)), e);
            return new BaseResponse(e);
        }

        return response;
    }
}
