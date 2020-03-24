package htcc.log.service.component.kafka;

import htcc.common.entity.checkin.CheckinModel;
import htcc.common.entity.complaint.ComplaintModel;
import htcc.common.entity.complaint.ComplaintRequest;
import htcc.common.entity.log.CheckInLogEntity;
import htcc.common.entity.log.ComplaintLogEntity;
import htcc.common.service.kafka.BaseKafkaConsumer;
import htcc.common.util.StringUtil;
import htcc.log.service.repository.BaseLogDAO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Log4j2
@KafkaListener(topics = "#{kafkaFileConfig.buz.complaintLog.topicName}",
               groupId = "#{kafkaFileConfig.buz.complaintLog.groupId}")
public class ComplaintLogKafkaListener extends BaseKafkaConsumer<ComplaintModel> {

    @Autowired
    private BaseLogDAO repo;

    @Override
    public void process(ComplaintModel model) {
        try {
            ComplaintLogEntity logEnt = new ComplaintLogEntity(model);
            repo.insertLog(logEnt);
        } catch (Exception e) {
            log.error("process {} ex", StringUtil.toJsonString(model), e);
        }
    }
}
