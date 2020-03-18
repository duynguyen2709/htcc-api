package htcc.common.entity.base;

import htcc.common.constant.ReturnCodeEnum;
import htcc.common.util.StringUtil;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;

import java.io.Serializable;
import java.util.Map;

@Data
@ToString
@Log4j2
@NoArgsConstructor
public class RequestLogEntity implements Serializable {

    public RequestLogEntity(Map<String, Object> requestLogHashMap) {
        this.serviceId = (int) requestLogHashMap.get("serviceId");
        this.method = (String) requestLogHashMap.get("method");
        this.path = (String) requestLogHashMap.get("path");
        this.request = (String) requestLogHashMap.get("request");
        this.params = requestLogHashMap.get("params");
        this.body = requestLogHashMap.get("body");
        this.requestTime = (long) requestLogHashMap.get("responseTime");
        this.responseTime = (long) requestLogHashMap.get("responseTime");
        this.returnCode = (int) requestLogHashMap.get("returnCode");
        this.setResponse(requestLogHashMap.get("response").toString());
    }

    public RequestLogEntity(long dateTime, int serviceId, String method, String path, String request, Object params, Object body, long requestTime, long responseTime, int returnCode, BaseResponse<Object> response) {
        this.serviceId = serviceId;
        this.method = method;
        this.path = path;
        this.request = request;
        this.params = params;
        this.body = body;
        this.requestTime = requestTime;
        this.responseTime = responseTime;
        this.returnCode = returnCode;
        this.response = response;
    }

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



    public void setResponse(String res) {
        try {
            response = StringUtil.fromJsonString(res, BaseResponse.class);
        } catch (Exception e) {
            response = new BaseResponse<>(ReturnCodeEnum.EXCEPTION);
            response.data = res;
        }
        returnCode = response.returnCode;
    }

    public void setBody(String str) {
        try {
            Object obj = StringUtil.fromJsonString(str, Object.class);
            this.body = (obj != null) ? obj : "";
        } catch (Exception e) {
            this.body = str;
        }
    }
}
