package htcc.gateway.service.component;

import htcc.common.constant.ReturnCodeEnum;
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

        int returnCode = getReturnCode(status);

        String error = StringUtil.valueOf(errorAttributes.get("error"));
        errorAttributes.clear();
        errorAttributes.put("returnCode", returnCode);
        errorAttributes.put("returnMessage", ReturnCodeEnum.fromInt(returnCode).getMessage());
        errorAttributes.put("data", error);
        return errorAttributes;
    }

    private int getReturnCode(int status) {
        ReturnCodeEnum e = ReturnCodeEnum.fromInt(status);
        switch (e) {
            case TOKEN_EXPIRED:
                return ReturnCodeEnum.TOKEN_EXPIRED.getValue();
            case UNAUTHORIZE:
                return ReturnCodeEnum.UNAUTHORIZE.getValue();
            default:
                return ReturnCodeEnum.EXCEPTION.getValue();
        }
    }
}
