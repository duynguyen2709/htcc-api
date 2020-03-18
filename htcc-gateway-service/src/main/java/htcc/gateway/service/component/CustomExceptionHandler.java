package htcc.gateway.service.component;

import htcc.common.constant.ReturnCodeEnum;
import htcc.common.util.ErrorHandler;
import htcc.common.util.StringUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

import static javax.servlet.http.HttpServletResponse.SC_OK;

@Component
@Log4j2
public class CustomExceptionHandler extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
        Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, includeStackTrace);
        int status = 0;
        try {
            status = Integer.parseInt(StringUtil.valueOf(errorAttributes.getOrDefault("status", 0)));
        } catch (Exception ignored) {}

        return ErrorHandler.buildErrorResponse(errorAttributes, status);
    }
}
