package htcc.common.entity.base;

import htcc.common.constant.ReturnCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@ApiModel(description = "Response cho tất cả API")
public class BaseResponse<T> implements Serializable {

    @ApiModelProperty(notes = "Kết quả gọi api (1 = thành công)",
                      example = "1")
    public int returnCode;

    @ApiModelProperty(notes = "Câu mô tả lỗi (hiện khi xảy ra lỗi)",
                      example = "Thành công")
    public String returnMessage;

    @ApiModelProperty(notes = "Dữ liệu trả về")
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
