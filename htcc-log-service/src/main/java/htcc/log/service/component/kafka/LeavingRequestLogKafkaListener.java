package htcc.log.service.component.kafka;

import htcc.common.entity.leavingrequest.LeavingRequestLogEntity;
import htcc.common.entity.leavingrequest.LeavingRequestModel;
import htcc.common.entity.leavingrequest.UpdateLeavingRequestStatusModel;
import htcc.common.service.kafka.BaseKafkaConsumer;
import htcc.common.util.DateTimeUtil;
import htcc.common.util.StringUtil;
import htcc.log.service.repository.BaseLogDAO;
import htcc.log.service.repository.LeavingRequestLogRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Log4j2
@ConditionalOnProperty(value = "kafka.enableConsumer", havingValue = "true")
@KafkaListener(topics = "#{kafkaFileConfig.buz.leavingRequestLog.topicName}",
               groupId = "#{kafkaFileConfig.buz.leavingRequestLog.groupId}")
public class LeavingRequestLogKafkaListener extends BaseKafkaConsumer<LeavingRequestModel> {

    @Autowired
    private BaseLogDAO repo;

    @Autowired
    private LeavingRequestLogRepository leavingRequestRepo;

    @Override
    public void process(LeavingRequestModel model) {
        try {
            LeavingRequestLogEntity logEnt = new LeavingRequestLogEntity(model);
            long result = repo.insertLog(logEnt);

            if (result != -1) {
                // if insert succeed, then increase pending counter
                UpdateLeavingRequestStatusModel counter = new UpdateLeavingRequestStatusModel();
                counter.setYyyyMM(DateTimeUtil.parseTimestampToString(model.getClientTime(), "yyyyMM"));
                counter.setLeavingRequestId(model.getLeavingRequestId());
                leavingRequestRepo.increasePendingLeavingRequestCounter(counter, model.getCompanyId());
            }
        } catch (Exception e) {
            log.error("process {} ex", StringUtil.toJsonString(model), e);
        }
    }
}
