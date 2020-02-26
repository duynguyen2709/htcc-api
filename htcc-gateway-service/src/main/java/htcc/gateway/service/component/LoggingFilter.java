package htcc.gateway.service.component;

import com.google.common.io.CharStreams;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import htcc.gateway.service.entity.base.RequestLogEntity;
import htcc.gateway.service.entity.base.RequestWrapper;
import htcc.gateway.service.util.NumberUtil;
import htcc.gateway.service.util.StringUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.POST_TYPE;

@Component
@Log4j2
public class LoggingFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return POST_TYPE;
    }

    @Override
    public int filterOrder() {
        return 10;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext     ctx       = RequestContext.getCurrentContext();
        RequestLogEntity   logEnt = new RequestLogEntity();

        try (final InputStream responseDataStream = ctx.getResponseDataStream()) {
            String responseData = CharStreams.toString(new InputStreamReader(responseDataStream, StandardCharsets.UTF_8));
            logEnt.setResponse(responseData);

            setLogData(ctx, logEnt);

            ctx.setResponseBody(responseData);
        } catch (Exception e) {
            log.error("Error reading request/response", e);
        } finally {
            processLog(logEnt);
        }
        return null;
    }

    private void setLogData(RequestContext ctx, RequestLogEntity logEnt) throws IOException {
        RequestWrapper request = new RequestWrapper(ctx.getRequest());
        logEnt.method = request.getMethod();
        logEnt.path = request.getRequestURI();
        logEnt.request = UriComponentsBuilder.fromHttpRequest(new ServletServerHttpRequest(request)).build().toUriString();
        if (logEnt.method.equalsIgnoreCase("POST") ||
                logEnt.method.equalsIgnoreCase("PUT")) {
            if (request.getBody() != null && !request.getBody().isEmpty())
                logEnt.body = StringUtil.toJsonString(StringUtil.fromJsonString(request.getBody(), Object.class));
        } else {
            logEnt.body = null;
        }
        logEnt.requestTime = NumberUtil.getLongValue(request.getAttribute("requestTime"));
        logEnt.responseTime = System.currentTimeMillis();
    }

    private void processLog(RequestLogEntity logEntity){
        log.info(StringUtil.toJsonString(logEntity));
    }
}
