package htcc.gateway.service.component;

import constant.ReturnCodeEnum;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;
import util.StringUtil;

import java.util.Map;

@Component
@Log4j2
public class CustomExceptionHandler extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
        Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, includeStackTrace);

        int returnCode = 0;
        Object playerId;
        int status = Integer.parseInt(StringUtil.valueOf(errorAttributes.getOrDefault("status", 0)));
        if (status == ReturnCodeEnum.UNAUTHORIZE.getValue()) {
            returnCode = ReturnCodeEnum.UNAUTHORIZE.getValue();
        }

        String error = StringUtil.valueOf(errorAttributes.get("error"));
        errorAttributes.clear();
        errorAttributes.put("returnCode", returnCode);
        errorAttributes.put("returnMessage", ReturnCodeEnum.fromInt(returnCode).toString());
        errorAttributes.put("data", error);
        return errorAttributes;
    }

}
