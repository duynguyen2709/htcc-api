package htcc.common.entity.home;

import htcc.common.entity.jpa.EmployeeInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@ApiModel(description = "Response API Home")
public class AdminHomeResponse implements Serializable {

    private static final long serialVersionUID = 5912346685015150712L;

    @ApiModelProperty(notes = "Số lượng khiếu nại chưa xử lý (để hiển thị ở sidebar)",
                      example = "10")
    public int pendingComplaint = 0;

    @ApiModelProperty(notes = "Số lượng đơn hàng chưa xử lý (để hiển thị ở sidebar)",
                      example = "10")
    public int pendingOrder = 0;
}

