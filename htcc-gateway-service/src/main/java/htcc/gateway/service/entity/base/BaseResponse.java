package htcc.gateway.service.entity.base;

import htcc.gateway.service.constant.ReturnCodeEnum;
import htcc.gateway.service.util.StringUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class BaseResponse {

    public int    returnCode;
    public String returnMessage;
    public Object data;

    public static final BaseResponse SUCCESS   = new BaseResponse(ReturnCodeEnum.SUCCESS);
    public static final BaseResponse EXCEPTION = new BaseResponse(ReturnCodeEnum.EXCEPTION);

    public BaseResponse(ReturnCodeEnum e) {
        this.returnCode = e.getValue();
        this.returnMessage = e.toString();
        this.data = "";
    }

    public BaseResponse(Exception e){
        this.returnCode = 0;
        this.returnMessage = "EXCEPTION";
        this.data = e.getMessage();
    }

}
