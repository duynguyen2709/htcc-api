package htcc.common.component;

import htcc.common.constant.Constant;
import htcc.common.constant.ServiceSystemEnum;
import htcc.common.entity.base.RequestLogEntity;
import htcc.common.entity.base.RequestWrapper;
import htcc.common.util.NumberUtil;
import htcc.common.util.StringUtil;
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
public abstract class BaseRequestServlet extends DispatcherServlet {

    @Override
    protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String uri = request.getRequestURI();

        if (!uri.startsWith(Constant.API_PATH) || uri.endsWith(Constant.SWAGGER_DOCS_PATH)) {
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
        } catch (Exception e) {
            log.warn(e.getMessage());
        } finally {
            setLogData(wrapper, response);
            updateResponse(response);
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

    private boolean hasBody(String method) {
        return (method.equalsIgnoreCase("POST") ||
                method.equalsIgnoreCase("PUT"));
    }

    private void setLogData(RequestWrapper request, HttpServletResponse responseToCache) {
        RequestLogEntity logEnt = new RequestLogEntity();
        try {
            logEnt.setMethod(request.getMethod());
            logEnt.setPath(request.getRequestURI());
            logEnt.setParams(request.getParameterMap());
            logEnt.setRequest(UriComponentsBuilder.fromHttpRequest(new ServletServerHttpRequest(request)).build().toUriString());
            logEnt.setRequestTime(NumberUtil.getLongValue(request.getAttribute(Constant.REQUEST_TIME)));
            logEnt.setResponseTime(System.currentTimeMillis());
            logEnt.setServiceId(ServiceSystemEnum.getServiceFromUri(logEnt.path));
            logEnt.setBody((hasBody(logEnt.method)) ? StringUtil.valueOf(request.getBody()) : "");
            logEnt.setResponse(getResponsePayload(responseToCache));
        } catch (Exception e) {
            log.warn("setLogData ex {}", e.getMessage(), e);
        } finally {
            processLog(logEnt);
        }
    }

    private void updateResponse(HttpServletResponse response) {
        try {
            ContentCachingResponseWrapper responseWrapper =
                    WebUtils.getNativeResponse(response, ContentCachingResponseWrapper.class);
            if (responseWrapper != null) {
                responseWrapper.copyBodyToResponse();
            }
        } catch (Exception e) {
            log.error("updateResponse ex", e);
        }
    }

    // each service implement its way to handle log
    protected abstract void processLog(RequestLogEntity logEntity);

}
