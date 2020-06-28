package htcc.log.service.component.kafka;

import htcc.common.constant.ComplaintStatusEnum;
import htcc.common.entity.checkin.CheckInLogEntity;
import htcc.common.entity.checkin.CheckinModel;
import htcc.common.service.kafka.BaseKafkaConsumer;
import htcc.common.util.StringUtil;
import htcc.log.service.repository.BaseLogDAO;
import htcc.log.service.repository.CheckInLogRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Log4j2
@ConditionalOnProperty(value = "kafka.enableConsumer", havingValue = "true")
@KafkaListener(topics = "#{kafkaFileConfig.buz.checkInLog.topicName}",
               groupId = "#{kafkaFileConfig.buz.checkInLog.groupId}")
public class CheckInLogKafkaListener extends BaseKafkaConsumer<CheckinModel> {

    @Autowired
    private BaseLogDAO repo;

    @Autowired
    private CheckInLogRepository checkInLogRepository;

    @Override
    public void process(CheckinModel model) {
        try {
            CheckInLogEntity logEnt = new CheckInLogEntity(model);
            if (repo.insertLog(logEnt) == -1) {
                throw new Exception("BaseLogDAO.insertLog ex");
            }
            if (model.getStatus() == ComplaintStatusEnum.PROCESSING.getValue()) {
                checkInLogRepository.increasePendingCheckInCounter(model);
            }
        } catch (Exception e) {
            log.error("process {} ex", StringUtil.toJsonString(model), e);
        }
    }
}
