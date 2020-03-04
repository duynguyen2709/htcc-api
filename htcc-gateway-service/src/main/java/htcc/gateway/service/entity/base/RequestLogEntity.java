package htcc.gateway.service.entity.base;

import htcc.gateway.service.constant.ReturnCodeEnum;
import htcc.gateway.service.util.StringUtil;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.io.Serializable;

@Data
@Log4j2
@NoArgsConstructor
public class RequestLogEntity implements Serializable {
    public String method       = "";
    public String path         = "";
    public String request      = "";
    public String body         = "";
    public long   requestTime  = 0L;
    public long   responseTime = 0L;

    public BaseResponse<Object> response;

    public void setResponse(String res) {
        try {
            response = StringUtil.fromJsonString(res, BaseResponse.class);
        } catch (Exception e) {
            response = new BaseResponse<Object>(ReturnCodeEnum.SUCCESS);
            response.data = res;
        }
    }
}
