package htcc.log.service.component.kafka;

import htcc.common.component.redis.RedisComplaintService;
import htcc.common.entity.complaint.ComplaintModel;
import htcc.common.entity.complaint.UpdateComplaintStatusModel;
import htcc.common.entity.complaint.ComplaintLogEntity;
import htcc.common.service.ICallback;
import htcc.common.service.kafka.BaseKafkaConsumer;
import htcc.common.util.DateTimeUtil;
import htcc.common.util.StringUtil;
import htcc.log.service.repository.BaseLogDAO;
import htcc.log.service.repository.ComplaintLogRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Log4j2
@ConditionalOnProperty(value = "kafka.enableConsumer", havingValue = "true")
@KafkaListener(topics = "#{kafkaFileConfig.buz.complaintLog.topicName}",
               groupId = "#{kafkaFileConfig.buz.complaintLog.groupId}")
public class ComplaintLogKafkaListener extends BaseKafkaConsumer<ComplaintModel> {

    @Autowired
    private BaseLogDAO repo;

    @Autowired
    private RedisComplaintService redisComplaintService;

    @Autowired
    private ComplaintLogRepository complaintLogRepo;

    @Override
    public void process(ComplaintModel model) {
        try {
            int result = (int) redisComplaintService.unlockWriteLock(StringUtil.valueOf(model.companyId),
                    StringUtil.valueOf(model.username),
                    DateTimeUtil.parseTimestampToString(model.clientTime, "yyyyMM"),
                    new ICallback() {
                        @Override
                        public Object callback() {
                            ComplaintLogEntity logEnt = new ComplaintLogEntity(model);
                            return repo.insertLog(logEnt);
                        }
            });

            if (result == 1) {
                // if insert succeed, then increase pending counter
                UpdateComplaintStatusModel counter = new UpdateComplaintStatusModel();
                counter.setYyyyMM(DateTimeUtil.parseTimestampToString(model.clientTime, "yyyyMM"));
                counter.setComplaintId(model.getComplaintId());
                complaintLogRepo.increasePendingComplaintCounter(counter);
            }

        } catch (Exception e) {
            log.error("process {} ex", StringUtil.toJsonString(model), e);
        }
    }

}
