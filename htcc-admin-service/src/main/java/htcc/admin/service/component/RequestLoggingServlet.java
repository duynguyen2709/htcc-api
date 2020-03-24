package htcc.admin.service.component;

import htcc.common.component.BaseRequestServlet;
import htcc.common.entity.log.RequestLogEntity;
import htcc.common.util.StringUtil;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class RequestLoggingServlet extends BaseRequestServlet {

    @Override
    protected void processLog(RequestLogEntity logEntity) {
        log.info(String.format("%s , Total Time : %sms\n",
                StringUtil.toJsonString(logEntity), (logEntity.responseTime - logEntity.requestTime)));
    }

    @Override
    protected boolean shouldNotProcessLog(String uri) {
        return false;
    }
}
