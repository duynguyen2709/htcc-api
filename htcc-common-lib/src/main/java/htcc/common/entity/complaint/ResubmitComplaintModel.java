package htcc.common.entity.complaint;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
@ApiModel(description = "Request để submit lại khiếu nại")
public class ResubmitComplaintModel implements Serializable {

    private static final long serialVersionUID = 5912575538005150708L;

    @ApiModelProperty(notes = "Tháng của khiếu nại (yyyyMM)",
                      example = "202003")
    @NotEmpty
    public String yyyyMM = "";

    @ApiModelProperty(notes = "ID khiếu nại",
                      example = "#VNG-abcd123")
    @NotEmpty
    public String complaintId = "";

    @ApiModelProperty(notes = "Nội dung MỚI phản hồi/ khiếu nại ",
                      example = "Nội dung MỚI")
    @NotEmpty
    public String content = "";
}
