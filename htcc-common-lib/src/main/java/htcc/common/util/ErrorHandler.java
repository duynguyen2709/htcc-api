package htcc.common.util;

import htcc.common.constant.ReturnCodeEnum;

import java.util.Map;

public class ErrorHandler {

    public static Map<String, Object> buildErrorResponse(Map<String, Object> errorAttributes, int status) {
        int returnCode = getReturnCode(status);

        String error = StringUtil.valueOf(errorAttributes.get("error"));
        errorAttributes.clear();
        errorAttributes.put("returnCode", returnCode);
        errorAttributes.put("returnMessage", ReturnCodeEnum.fromInt(returnCode).getMessage());
        errorAttributes.put("data", error);
        return errorAttributes;
    }

    private static int getReturnCode(int status) {
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
