package htcc.gateway.service.component.filter;

import component.BaseRequestServlet;
import entity.base.RequestLogEntity;
import lombok.extern.log4j.Log4j2;
import util.StringUtil;

@Log4j2
public class RequestLoggingServlet extends BaseRequestServlet {

    @Override
    protected void processLog(RequestLogEntity logEntity) {
        log.info(StringUtil.toJsonString(logEntity));
    }
}
