package htcc.log.service.component.kafka;

import htcc.common.entity.request.ResetPasswordRequest;
import htcc.common.service.kafka.BaseKafkaConsumer;
import htcc.common.util.StringUtil;
import htcc.log.service.config.MailBuzConfig;
import htcc.log.service.config.ResetPasswordConfig;
import htcc.log.service.service.mail.MailService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Log4j2
@ConditionalOnProperty(value = "kafka.enableConsumer", havingValue = "true")
@KafkaListener(topics = "#{kafkaFileConfig.buz.eventResetPassword.topicName}",
               groupId = "#{kafkaFileConfig.buz.eventResetPassword.groupId}")
public class EventResetPasswordKafkaListener extends BaseKafkaConsumer<ResetPasswordRequest> {

    @Autowired
    private MailBuzConfig mailBuzConfig;

    @Autowired
    private MailService mailService;

    @Autowired
    private ResetPasswordConfig resetPasswordConfig;

    @Override
    public void process(ResetPasswordRequest request) {
        try {
            final String template = mailBuzConfig.mailResetPasswordTemplate;
            final String username = "##username##";
            final String resetPasswordLink = "##reset-password-link##";
            final String params = String.format(resetPasswordConfig.getParams(), request.getClientId(),
                    StringUtil.valueOf(request.getCompanyId()), request.getUsername(), request.getToken());
            final String url = resetPasswordConfig.getBaseUrl() + params;

            String content = template.replaceAll(username, request.getUsername())
                    .replace(resetPasswordLink, url);

            mailService.sendMail(request.getEmail(), mailBuzConfig.mailResetPasswordSubject, content);
        } catch (Exception e) {
            log.error("process {} ex", StringUtil.toJsonString(request), e);
        }
    }
}
