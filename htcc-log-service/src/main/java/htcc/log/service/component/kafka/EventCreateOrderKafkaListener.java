package htcc.log.service.component.kafka;

import htcc.common.entity.order.CreateOrderRequest;
import htcc.common.service.kafka.BaseKafkaConsumer;
import htcc.common.util.StringUtil;
import htcc.log.service.config.CreateOrderConfig;
import htcc.log.service.config.MailBuzConfig;
import htcc.log.service.service.mail.MailService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
@Log4j2
@ConditionalOnProperty(value = "kafka.enableConsumer", havingValue = "true")
@KafkaListener(topics = "#{kafkaFileConfig.buz.eventCreateOrder.topicName}",
               groupId = "#{kafkaFileConfig.buz.eventCreateOrder.groupId}")
public class EventCreateOrderKafkaListener extends BaseKafkaConsumer<CreateOrderRequest> {

    @Autowired
    private MailBuzConfig mailBuzConfig;

    @Autowired
    private MailService mailService;

    @Autowired
    private CreateOrderConfig createOrderConfig;

    @Value("${security.hashKey}")
    private String hashKey;

    @Override
    public void process(CreateOrderRequest request) {
        try {
            final String template = mailBuzConfig.mailCreateOrderTemplate;
            final String companyId = "##companyId##";
            final String createOrderLink = "##create-order-link##";
            final String params = String.format(createOrderConfig.getParams(),
                    URLEncoder.encode(StringUtil.toJsonString(request), StandardCharsets.UTF_8.toString()));
            final String url = createOrderConfig.getBaseUrl() + params;

            String content = template.replaceAll(companyId, request.getCompanyId())
                    .replace(createOrderLink, url);
            mailService.sendMail(request.getEmail(), mailBuzConfig.mailCreateOrderSubject, content);
        } catch (Exception e) {
            log.error("process {} ex", StringUtil.toJsonString(request), e);
        }
    }
}
