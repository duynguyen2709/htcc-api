package htcc.gateway.service.component.filter;

import htcc.gateway.service.constant.Constant;
import htcc.gateway.service.entity.base.RequestLogEntity;
import htcc.gateway.service.entity.base.RequestWrapper;
import htcc.gateway.service.util.NumberUtil;
import htcc.gateway.service.util.StringUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Log4j2
public class RequestLoggingServlet extends DispatcherServlet {

    @Override
    protected void doDispatch(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        String uri = request.getRequestURI();
        if (!uri.startsWith(Constant.API_PATH)) {
            super.doDispatch(request, response);
            return;
        }

        if (!(request instanceof ContentCachingRequestWrapper)) {
            request = new ContentCachingRequestWrapper(request);
        }
        if (!(response instanceof ContentCachingResponseWrapper)) {
            response = new ContentCachingResponseWrapper(response);
        }

        RequestWrapper wrapper = new RequestWrapper(request);

        try {
            super.doDispatch(wrapper, response);
        } finally {
            processLog(wrapper, response);
            updateResponse(response);
        }
    }

    private void processLog(RequestWrapper request, HttpServletResponse responseToCache) {
        RequestLogEntity logEnt = new RequestLogEntity();
        try {
            logEnt.method = request.getMethod();
            logEnt.path = request.getRequestURI();
            logEnt.request = UriComponentsBuilder.fromHttpRequest(new ServletServerHttpRequest(request)).build().toUriString();
            if (logEnt.method.equalsIgnoreCase("POST") ||
                    logEnt.method.equalsIgnoreCase("PUT")) {
                if (request.getBody() != null && !request.getBody().isEmpty())
                    logEnt.body = StringUtil.fromJsonString(request.getBody(), Object.class).toString();
            } else {
                logEnt.body = null;
            }
            logEnt.requestTime = NumberUtil.getLongValue(request.getAttribute(Constant.REQUEST_TIME));
            logEnt.responseTime = System.currentTimeMillis();

            logEnt.setResponse(getResponsePayload(responseToCache));
        } catch (Exception e) {
            log.error("log ex {}", e.getMessage());
        } finally {
            sendLog(logEnt);
        }
    }

    private String getResponsePayload(HttpServletResponse response) {
        ContentCachingResponseWrapper wrapper =
                WebUtils.getNativeResponse(response, ContentCachingResponseWrapper.class);
        String result = null;
        if (wrapper != null) {
            byte[] buf = wrapper.getContentAsByteArray();
            if (buf.length > 0) {
                int length = Math.min(buf.length, 40960);
                try {
                    result = new String(buf, 0, length, wrapper.getCharacterEncoding());
                } catch (Exception e) {
                    log.error("getResponsePayload ex", e);
                }
            }
        }
        return result;
    }

    private void updateResponse(HttpServletResponse response) {
        try {
            ContentCachingResponseWrapper responseWrapper =
                    WebUtils.getNativeResponse(response, ContentCachingResponseWrapper.class);
            if (responseWrapper != null) {
                responseWrapper.copyBodyToResponse();
            }
        } catch (Exception e) {
            log.error("updateResponse ex",e);
        }
    }

    private void sendLog(RequestLogEntity logEntity){

        //handle send log here
        log.info(StringUtil.toJsonString(logEntity));
    }

}
