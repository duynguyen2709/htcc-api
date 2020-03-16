package htcc.gateway.service.component.filter;

import htcc.common.component.BaseRequestServlet;
import htcc.common.entity.base.RequestLogEntity;
import htcc.common.util.StringUtil;
import htcc.gateway.service.component.kafka.MessageProducer;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

@Log4j2
public class RequestLoggingServlet extends BaseRequestServlet {

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    protected void processLog(RequestLogEntity logEntity) {
        MessageProducer producer = applicationContext.getBean(MessageProducer.class);
        producer.sendRequestLogMessage(logEntity);
        log.info(StringUtil.toJsonString(logEntity));
    }
}
