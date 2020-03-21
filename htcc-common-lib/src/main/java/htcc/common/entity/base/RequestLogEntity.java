package htcc.common.entity.base;

import htcc.common.constant.ReturnCodeEnum;
import htcc.common.util.StringUtil;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
@Log4j2
@NoArgsConstructor
public class RequestLogEntity extends BaseLogEntity {

    private static final String TABLE_LOG_NAME = "ApiLog";

    public int          serviceId    = 0;
    public String       method       = "";
    public String       path         = "";
    public String       request      = "";
    public Object       params;
    public Object       body;
    public long         requestTime  = 0L;
    public long         responseTime = 0L;
    public int          returnCode   = 1;
    public BaseResponse response;
    public String       ip           = "";

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

    public void setIp(HttpServletRequest request) {
        this.ip = request.getHeader("X-FORWARDED-FOR");
        if (StringUtil.valueOf(this.ip).isEmpty()) {
            this.ip = request.getRemoteAddr();
        }
        else if (this.ip.contains(",")) {
            this.ip = this.ip.split(",")[0];
        }
    }

    @Override
    public Map<String, Object> getParamsMap() {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("requestId", this.requestId);
        parameters.put("serviceId", this.getServiceId());
        parameters.put("requestURL", this.getRequest());
        parameters.put("method", this.getMethod());
        parameters.put("path", this.getPath());
        parameters.put("params", StringUtil.toJsonString(this.getParams()));
        parameters.put("body", StringUtil.valueOf(this.getBody()).isEmpty() ? "" : StringUtil.toJsonString(this.getBody()));
        parameters.put("returnCode", this.getReturnCode());
        parameters.put("requestTime", this.getRequestTime());
        parameters.put("responseTime", this.getResponseTime());
        parameters.put("userIP", this.getIp());
        return parameters;
    }

    @Override
    public long getCreateTime() {
        return this.requestTime;
    }

    @Override
    public String retrieveTableName() {
        return TABLE_LOG_NAME;
    }
}
