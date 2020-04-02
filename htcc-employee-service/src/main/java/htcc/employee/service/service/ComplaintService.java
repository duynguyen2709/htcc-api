package htcc.employee.service.service;

import com.google.gson.reflect.TypeToken;
import htcc.common.component.kafka.KafkaProducerService;
import htcc.common.component.redis.RedisComplaintService;
import htcc.common.constant.ReturnCodeEnum;
import htcc.common.entity.base.BaseResponse;
import htcc.common.entity.complaint.ComplaintModel;
import htcc.common.entity.complaint.ComplaintResponse;
import htcc.common.entity.complaint.UpdateComplaintStatusModel;
import htcc.common.service.ICallback;
import htcc.common.util.DateTimeUtil;
import htcc.common.util.StringUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@Log4j2
public class ComplaintService {

    @Autowired
    private LogService logService;

    @Autowired
    private KafkaProducerService kafka;

    @Autowired
    private GoogleDriveService driveService;

    @Autowired
    private RedisComplaintService redisComplaintService;



    /*
       For Employee to get their personal list complaint in month (yyyyMM)
    */
    public List<ComplaintResponse> getComplaintLog(String companyId, String username, String yyyyMM) {
        List<ComplaintResponse> result = new ArrayList<>();
        try {
            redisComplaintService.readLockForEmployee(companyId, username, yyyyMM,
                    getComplaintLogCallback(companyId, username, yyyyMM, result));

        } catch (Exception e) {
            log.error(String.format("[getComplaintLog] : [%s-%s-%s] ex", companyId, username, yyyyMM), e);
        }

        return result;
    }

    private ICallback getComplaintLogCallback(String companyId, String username, String yyyyMM, List<ComplaintResponse> result) {
        return new ICallback() {

            @Override
            public Object callback() {
                try {
                    BaseResponse response = logService.getComplaintLog(companyId, username, yyyyMM);
                    List<ComplaintModel> models = parseResponse(response);
                    if (models == null) {
                        throw new Exception(String.format("parseResponse %s return null", response));
                    }

                    models.forEach(c -> result.add(new ComplaintResponse(c)));
                } catch (Exception e) {
                    log.error(String.format("[getComplaintLogCallback] : [%s-%s-%s] ex", companyId, username, yyyyMM), e);

                }
                return null;
            }
        };
    }




    /*
        For Company Manager to get list complaint in month (yyyyMM)
     */
    public List<ComplaintResponse> getListComplaintLogByCompany(String companyId, String yyyyMM) {
        List<ComplaintResponse> result = new ArrayList<>();
        try {
            BaseResponse response = logService.getListComplaintLogByCompany(companyId, yyyyMM);
            List<ComplaintModel> models = parseResponse(response);
            if (models == null) {
                throw new Exception(String.format("parseResponse %s return null", response));
            }

            models.forEach(c -> result.add(new ComplaintResponse(c)));
        } catch (Exception e) {
            log.error(String.format("[getListComplaintLogByCompany] : [%s-%s] ex", companyId, yyyyMM), e);
        }

        return result;
    }





    @Async("asyncExecutor")
    public void handleUploadImage(List<MultipartFile> images, ComplaintModel model){
        try {
            redisComplaintService.writeLock(model.companyId, model.username,
                    DateTimeUtil.parseTimestampToString(model.clientTime, "yyyyMM"),
                    uploadImageCallback(images, model)
            );

        } catch (Exception e) {
            log.error(String.format("[handleUploadImage] %s ex", StringUtil.toJsonString(model)), e);
        } finally {
            kafka.sendMessage(kafka.getBuzConfig().complaintLog.topicName, model);
        }
    }

    private ICallback uploadImageCallback(List<MultipartFile> images, ComplaintModel model) {

        return new ICallback() {

            @Override
            public Object callback() {

                if (!images.isEmpty()) {
                    List<CompletableFuture<String>> asyncList = new ArrayList<>();
                    for (int i = 0; i < images.size(); i++) {
                        String fileName = String.format("%s_%s", model.getComplaintId(), i);
                        CompletableFuture<String> task = driveService.uploadComplaintImage(images.get(i), fileName);
                        asyncList.add(task);
                    }

                    CompletableFuture.allOf(asyncList.toArray(new CompletableFuture[asyncList.size()])).join();

                    for (int i = 0; i < images.size(); i++) {
                        try {
                            model.getImages().add(asyncList.get(i).get());
                        } catch (Exception e) {
                            log.error(String.format("[uploadImageCallback] %s ex", StringUtil.toJsonString(model)), e);
                        }
                    }
                }
                return null;
            }
        };
    }




    public BaseResponse updateComplaintStatus(UpdateComplaintStatusModel model) {
        return logService.updateComplaintStatus(model);
    }

    private List<ComplaintModel> parseResponse(BaseResponse res) {
        try {
            if (res == null) {
                return null;
            }

            if (res.getReturnCode() != ReturnCodeEnum.SUCCESS.getValue() ||
                    res.getData() == null) {
                log.warn("parseResponse Failed {}", StringUtil.toJsonString(res));
                return new ArrayList<>();
            }

            String data = StringUtil.toJsonString(res.data);

            return StringUtil.json2Collection(data, new TypeToken<List<ComplaintModel>>(){}.getType());
        } catch (Exception e){
            log.warn("parseResponse {} return null, ex {}", StringUtil.toJsonString(res), e.getMessage());
            return null;
        }
    }
}
