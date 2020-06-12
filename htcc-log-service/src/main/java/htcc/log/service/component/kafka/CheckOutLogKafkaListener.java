package htcc.log.service.component.kafka;

import htcc.common.entity.checkin.CheckOutLogEntity;
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
@KafkaListener(topics = "#{kafkaFileConfig.buz.checkOutLog.topicName}",
               groupId = "#{kafkaFileConfig.buz.checkOutLog.groupId}")
public class CheckOutLogKafkaListener extends BaseKafkaConsumer<CheckinModel> {

    @Autowired
    private BaseLogDAO repo;

    @Autowired
    private CheckInLogRepository checkInLogRepo;

    @Override
    public void process(CheckinModel model) {
        try {
            CheckOutLogEntity logEnt = new CheckOutLogEntity(model);
            if (repo.insertLog(logEnt) == -1) {
                throw new Exception("BaseLogDAO.insertLog ex");
            }

            int result = checkInLogRepo.updateOppositeId(model);
            if (result != 1) {
                throw new Exception("checkInLogRepo.updateOppositeId return " + result);
            }
        } catch (Exception e) {
            log.error("process {} ex", StringUtil.toJsonString(model), e);
        }
    }
}
