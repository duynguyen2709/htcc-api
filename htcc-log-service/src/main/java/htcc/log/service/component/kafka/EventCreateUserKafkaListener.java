package htcc.log.service.component.kafka;

import htcc.common.entity.base.BaseUser;
import htcc.common.service.kafka.BaseKafkaConsumer;
import htcc.common.util.StringUtil;
import htcc.log.service.config.MailBuzConfig;
import htcc.log.service.service.mail.MailService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Log4j2
@ConditionalOnProperty(value = "kafka.enableConsumer", havingValue = "true")
@KafkaListener(topics = "#{kafkaFileConfig.buz.eventCreateUser.topicName}",
               groupId = "#{kafkaFileConfig.buz.eventCreateUser.groupId}")
public class EventCreateUserKafkaListener extends BaseKafkaConsumer<BaseUser> {

    @Autowired
    private MailBuzConfig mailBuzConfig;

    @Autowired
    private MailService mailService;

    @Override
    public void process(BaseUser user) {
        try {
            final String template = mailBuzConfig.mailCreateNewUserTemplate;
            final String username = "##username##";
            final String password = "##password##";
            final String companyId = "##companyId##";

            String companyIdValue = "";
            if (!StringUtil.isEmpty(user.companyId)){
                companyIdValue = String.format("<br></br>COMPANY ID: <b>%s</b><br></br>", user.companyId);
            }

            String content = template.replaceAll(username, user.username)
                    .replace(password, user.password)
                    .replace(companyId, companyIdValue);

            mailService.sendMail(user.email, mailBuzConfig.mailCreateNewUserSubject, content);

        } catch (Exception e) {
            log.error("[process] [{}] ex", StringUtil.toJsonString(user), e);
        }
    }
}
