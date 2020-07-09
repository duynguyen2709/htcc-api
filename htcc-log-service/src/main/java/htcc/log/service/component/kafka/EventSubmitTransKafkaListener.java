package htcc.log.service.component.kafka;

import htcc.common.entity.order.DetailOrderModel;
import htcc.common.entity.order.OrderLogEntity;
import htcc.common.entity.order.UpdateOrderStatusModel;
import htcc.common.service.kafka.BaseKafkaConsumer;
import htcc.common.util.StringUtil;
import htcc.log.service.repository.BaseLogDAO;
import htcc.log.service.repository.OrderLogRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Log4j2
@ConditionalOnProperty(value = "kafka.enableConsumer", havingValue = "true")
@KafkaListener(topics = "#{kafkaFileConfig.buz.eventSubmitTrans.topicName}",
               groupId = "#{kafkaFileConfig.buz.eventSubmitTrans.groupId}")
public class EventSubmitTransKafkaListener extends BaseKafkaConsumer<DetailOrderModel> {

    @Autowired
    private BaseLogDAO repo;

    @Autowired
    private OrderLogRepository orderLogRepository;

    @Override
    public void process(DetailOrderModel model) {
        try {
            OrderLogEntity orderLogEntity = new OrderLogEntity(model);
            long result = repo.insertLog(orderLogEntity);

            if (result != -1) {
                UpdateOrderStatusModel orderStatusModel = new UpdateOrderStatusModel();
                orderStatusModel.setOrderId(model.getOrderId());
                orderStatusModel.setStatus(model.getOrderStatus());
                orderLogRepository.increasePendingOrderCounter(orderStatusModel);
            }
        } catch (Exception e) {
            log.error("process {} ex", StringUtil.toJsonString(model), e);
        }
    }
}
