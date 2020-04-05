package htcc.gateway.service.component.kafka;

import htcc.common.entity.jpa.EmployeeInfo;
import htcc.common.service.kafka.BaseKafkaConsumer;
import htcc.common.util.StringUtil;
import htcc.gateway.service.entity.jpa.company.CompanyUser;
import htcc.gateway.service.service.jpa.CompanyUserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Log4j2
@ConditionalOnProperty(value = "kafka.enableConsumer", havingValue = "true")
@KafkaListener(topics = "#{kafkaFileConfig.buz.eventUpdateEmployeeInfo.topicName}",
               groupId = "#{kafkaFileConfig.buz.eventUpdateEmployeeInfo.groupId}")
public class EventUpdateEmployeeInfoListener extends BaseKafkaConsumer<EmployeeInfo> {

    @Autowired
    private CompanyUserService service;

    @Override
    public void process(EmployeeInfo model) {
        try {
            CompanyUser oldUser = service.findById(new CompanyUser.Key(model.companyId, model.username));
            if (oldUser != null) {
                oldUser.setPhoneNumber(model.getPhoneNumber());
                oldUser.setEmail(model.getEmail());
                service.update(oldUser);
            }
        } catch (Exception e){
            log.error("[process] [{}] ex", StringUtil.toJsonString(model), e);
        }
    }
}
