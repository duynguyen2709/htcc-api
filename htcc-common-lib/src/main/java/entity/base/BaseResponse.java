package entity.base;

import constant.ReturnCodeEnum;
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

    @ApiModelProperty(notes = "Câu mô tả lỗi xảy ra",
                      example = "SUCCESS")
    public String returnMessage;

    @ApiModelProperty(notes = "Dữ liệu trả về",
                      example = "{\"token\":\"adasdasd\"}")
    public T data;

    public static final BaseResponse SUCCESS   = new BaseResponse(ReturnCodeEnum.SUCCESS);
    public static final BaseResponse EXCEPTION = new BaseResponse(ReturnCodeEnum.EXCEPTION);

    public BaseResponse(ReturnCodeEnum e) {
        this.returnCode = e.getValue();
        this.returnMessage = e.toString();
    }

    public BaseResponse(Exception e){
        this.returnCode = 0;
        this.returnMessage = "EXCEPTION";
    }

}
