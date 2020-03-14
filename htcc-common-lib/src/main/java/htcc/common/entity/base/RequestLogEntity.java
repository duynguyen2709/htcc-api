package htcc.common.entity.base;

import htcc.common.constant.ReturnCodeEnum;
import htcc.common.util.StringUtil;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

@Data
@Log4j2
@NoArgsConstructor
public class RequestLogEntity implements Serializable {
    // Service Identity
    public int serviceId = 0;

    // GET, POST, PUT, DELETE
    public String method = "";

    // URI
    public String path = "";

    // request URL
    public String request = "";

    // Query String ?key=value
    public Object params;

    // Body in POST, PUT
    public Object body;

    // time server receive request
    public long requestTime = 0L;

    // time server response
    public long responseTime = 0L;

    // result
    public int returnCode = 1;

    public BaseResponse<Object> response;

    public String ip = "";

    public void setResponse(String res) {
        try {
            response = StringUtil.fromJsonString(res, BaseResponse.class);
            returnCode = response.returnCode;
        } catch (Exception e) {
            response = new BaseResponse<>(ReturnCodeEnum.EXCEPTION);
            response.data = res;
            returnCode = response.returnCode;
        }
    }

    public void setBody(String str) {
        try {
            Object obj = StringUtil.fromJsonString(str, Object.class);
            this.body = (obj != null) ? obj : "";
        } catch (Exception e) {
            this.body = str;
        }
    }

    public void setIp(HttpServletRequest request){
        this.ip = request.getHeader("X-FORWARDED-FOR");
        if (StringUtil.valueOf(this.ip).isEmpty()) {
            this.ip = request.getRemoteAddr();
        }
    }
}
