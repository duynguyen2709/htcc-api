package htcc.gateway.service.entity.base;

import htcc.gateway.service.constant.ReturnCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data @AllArgsConstructor @RequiredArgsConstructor
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

}
