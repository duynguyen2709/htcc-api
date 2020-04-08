package htcc.common.entity.leavingrequest;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@ApiModel(description = "Thông tin ngày phép trong năm")
public class LeavingRequestInfo implements Serializable {

    private static final long serialVersionUID = 5926290783005150708L;

    @ApiModelProperty(notes = "Số ngày nghỉ có tổng cộng", example = "20")
    public float totalDays = 0.0f;

    @ApiModelProperty(notes = "Số ngày nghỉ đã sử dụng", example = "9.5")
    public float usedDays = 0.0f;

    @ApiModelProperty(notes = "Số ngày nghỉ còn lại", example = "10.5")
    public float leftDays = 0.0f;

    @ApiModelProperty(notes = "Số ngày nghỉ không dùng phép", example = "4")
    public float externalDaysOff = 0.0f;

    @ApiModelProperty(notes = "Danh sách loại nghỉ phép được chọn", example = "[\"Nghỉ phép năm\",\"Nghỉ bệnh\",\"Nghỉ thai sản\"]")
    public List<String> categories = new ArrayList<>();

    @ApiModelProperty(notes = "Danh sách đơn nghỉ phép đã đăng kí")
    public List<LeavingRequestResponse> listRequest = new ArrayList<>();
}
