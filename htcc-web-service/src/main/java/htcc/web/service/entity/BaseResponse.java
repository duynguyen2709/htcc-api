package htcc.web.service.entity;

import htcc.web.service.enums.ReturnCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class BaseResponse<T> implements Serializable {
    public int returnCode;
    public String returnMessage;
    public T data;

    public static final BaseResponse SUCCESS   = new BaseResponse(ReturnCodeEnum.SUCCESS);
    public static final BaseResponse EXCEPTION = new BaseResponse(ReturnCodeEnum.EXCEPTION);

    public BaseResponse(ReturnCodeEnum e) {
        this.returnCode = e.getValue();
        this.returnMessage = e.getMessage();
    }

    public BaseResponse(ReturnCodeEnum e, Object data) {
        this(e);
        this.data = (T)data;
    }

    public BaseResponse(Exception e){
        this(ReturnCodeEnum.EXCEPTION);
        this.data = (T) e.getMessage();
    }
}
