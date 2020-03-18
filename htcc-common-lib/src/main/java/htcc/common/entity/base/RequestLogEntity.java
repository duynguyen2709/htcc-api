package htcc.common.entity.base;

import htcc.common.constant.ReturnCodeEnum;
import htcc.common.util.StringUtil;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

@Data
@ToString
@Log4j2
@NoArgsConstructor
public class RequestLogEntity implements Serializable, LogEntity {

    public RequestLogEntity(Map<String, Object> requestLogHashMap) {
        this.createTime = (long) requestLogHashMap.get("createTime");
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

    private String tableName = "RequestLog";

    public long createTime = new Date().getTime();

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

    @Override
    public Map<String, Object> getHashMap() {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("serviceId", this.getServiceId());
        parameters.put("requestURL", this.getServiceId());
        parameters.put("method", this.getMethod());
        parameters.put("path", this.getPath());
        parameters.put("params", this.getParams());
        parameters.put("body", this.getBody());
        parameters.put("response", this.getResponse());
        parameters.put("returnCode", this.getReturnCode());
        parameters.put("requestTime", this.getRequestTime());
        parameters.put("responseTime", this.getResponseTime());
        return parameters;
    }

    @Override
    public String getSQLCreateTableSchemaScript() {
        return    "`serviceId` int NOT NULL DEFAULT '0',"
                + "`requestURL` varchar(256) NOT NULL DEFAULT '',"
                + "`method` varchar(8) NOT NULL DEFAULT '',"
                + "`path` varchar(256) NOT NULL DEFAULT '',"
                + "`params` varchar(256) NOT NULL DEFAULT '',"
                + "`body` varchar(4096) NOT NULL DEFAULT '',"
                + "`response` text NOT NULL,"
                + "`returnCode` int NOT NULL DEFAULT '1',"
                + "`requestTime` bigint NOT NULL DEFAULT '0',"
                + "`responseTime` bigint NOT NULL DEFAULT '0',"
                + "`userIP` varchar(32) DEFAULT '',"
                + "`updDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,";
    }
    
    public void setIp(HttpServletRequest request){
        this.ip = request.getHeader("X-FORWARDED-FOR");
        if (StringUtil.valueOf(this.ip).isEmpty()) {
            this.ip = request.getRemoteAddr();
        } else if (this.ip.contains(",")){
            this.ip = this.ip.split(",")[0];
        }
    }
}
