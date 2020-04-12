package htcc.gateway.service.component.filter;

import htcc.common.component.BaseRequestServlet;
import htcc.common.component.kafka.KafkaProducerService;
import htcc.common.constant.Constant;
import htcc.common.entity.log.RequestLogEntity;
import htcc.common.util.StringUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;

@Log4j2
public class RequestLoggingServlet extends BaseRequestServlet {

    @Autowired
    private KafkaProducerService kafka;

    @Override
    protected void processLog(RequestLogEntity logEntity) {
        log.info(String.format("%s , Total Time : %sms\n",
                StringUtil.toJsonString(logEntity), (logEntity.responseTime - logEntity.requestTime)));

        kafka.sendMessage(kafka.getBuzConfig().apiLog.topicName, logEntity);
    }

    @Override
    protected boolean shouldNotProcessLog(String uri) {
        return (!uri.startsWith(Constant.API_PATH) &&
                !uri.startsWith(Constant.INTERNAL_API_PATH));
    }
}
