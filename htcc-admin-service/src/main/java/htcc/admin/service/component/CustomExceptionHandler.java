package htcc.admin.service.component;

import htcc.common.constant.ReturnCodeEnum;
import htcc.common.util.StringUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

@Component
@Log4j2
public class CustomExceptionHandler extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
        Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, includeStackTrace);

        int returnCode = 0;
        String error = StringUtil.valueOf(errorAttributes.get("error"));
        errorAttributes.clear();
        errorAttributes.put("returnCode", returnCode);
        errorAttributes.put("returnMessage", ReturnCodeEnum.fromInt(returnCode).getMessage());
        errorAttributes.put("data", error);
        return errorAttributes;
    }

}
