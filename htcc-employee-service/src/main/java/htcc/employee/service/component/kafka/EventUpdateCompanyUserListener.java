package htcc.employee.service.component.kafka;

import htcc.common.entity.companyuser.CompanyUserModel;
import htcc.common.entity.jpa.EmployeeInfo;
import htcc.common.service.kafka.BaseKafkaConsumer;
import htcc.common.util.StringUtil;
import htcc.employee.service.service.jpa.EmployeeInfoService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Log4j2
@ConditionalOnProperty(value = "kafka.enableConsumer", havingValue = "true")
@KafkaListener(topics = "#{kafkaFileConfig.buz.eventUpdateCompanyUser.topicName}",
               groupId = "#{kafkaFileConfig.buz.eventUpdateCompanyUser.groupId}")
public class EventUpdateCompanyUserListener extends BaseKafkaConsumer<CompanyUserModel> {

    @Autowired
    private EmployeeInfoService service;

    @Override
    public void process(CompanyUserModel model) {
        try {
            EmployeeInfo oldUser = service.findById(new EmployeeInfo.Key(model.getCompanyId(), model.getUsername()));
            if (oldUser != null) {
                oldUser.setEmail(model.getEmail());
                oldUser.setPhoneNumber(model.getPhoneNumber());
                service.update(oldUser);
            }
        } catch (Exception e){
            log.error("[process] [{}] ex", StringUtil.toJsonString(model), e);
        }
    }
}
