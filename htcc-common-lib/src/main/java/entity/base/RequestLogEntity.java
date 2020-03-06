package entity.base;

import constant.ReturnCodeEnum;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import util.StringUtil;

import java.io.Serializable;

@Data
@Log4j2
@NoArgsConstructor
public class RequestLogEntity implements Serializable {
    public int    serviceId    = 0;
    public String method       = "";
    public String path         = "";
    public String request      = "";
    public String body         = "";
    public long   requestTime  = 0L;
    public long   responseTime = 0L;
    public int    returnCode   = 1;

    public BaseResponse<Object> response;

    public void setResponse(String res) {
        try {
            response = StringUtil.fromJsonString(res, BaseResponse.class);
            returnCode = response.returnCode;
        } catch (Exception e) {
            response = new BaseResponse<Object>(ReturnCodeEnum.SUCCESS);
            response.data = res;
        }
    }

    public void setBody(String str) {
        try {
            Object jsonString = StringUtil.fromJsonString(str, Object.class);
            this.body = StringUtil.toJsonString(jsonString);
        } catch (Exception e){
            this.body = str;
        }
    }
}
