package htcc.gateway.service.component.filter;

import htcc.common.component.BaseRequestServlet;
import htcc.common.constant.Constant;
import htcc.common.entity.base.RequestLogEntity;
import htcc.common.util.StringUtil;
import htcc.gateway.service.component.kafka.MessageProducer;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;

@Log4j2
public class RequestLoggingServlet extends BaseRequestServlet {

    @Value(value = "${kafka.enabled}")
    private boolean KafkaEnabled;

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    protected void processLog(RequestLogEntity logEntity) {
        if (KafkaEnabled) {
            MessageProducer producer = applicationContext.getBean(MessageProducer.class);
            producer.sendRequestLogMessage(logEntity);
        }
        else {
            log.info(String.format("%s , Total Time : %sms\n",
                StringUtil.toJsonString(logEntity), (logEntity.responseTime - logEntity.requestTime)));
        }
    }

    @Override
    protected boolean shouldNotProcessLog(String uri) {
        return (!uri.startsWith(Constant.API_PATH));
    }
}
