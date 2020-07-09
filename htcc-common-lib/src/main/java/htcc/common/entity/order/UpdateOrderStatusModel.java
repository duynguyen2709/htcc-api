package htcc.common.entity.order;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
@ApiModel(description = "Request để cập nhật trạng thái đơn hàng")
public class UpdateOrderStatusModel implements Serializable {

    private static final long serialVersionUID = 59125755708L;

    @ApiModelProperty(notes = "ID đơn hàng",
                      example = "2007060001")
    @NotEmpty
    public String orderId = "";

    @ApiModelProperty(notes = "Trạng thái mới (0: Từ chối/ 1: Đã xử lý)",
                      example = "1")
    public int status = 1;
}
