package htcc.gateway.service.component;

import htcc.gateway.service.util.StringUtil;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

@Component
public class CustomExceptionHandler extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
        Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, includeStackTrace);

        String error = StringUtil.valueOf(errorAttributes.get("error"));
        errorAttributes.clear();
        errorAttributes.put("returnCode", 0);
        errorAttributes.put("returnMessage", "EXCEPTION");
        errorAttributes.put("data", error);
        return errorAttributes;
    }

}
