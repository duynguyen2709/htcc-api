package htcc.common.entity.complaint;

import htcc.common.component.LoggingConfiguration;
import htcc.common.constant.ComplaintStatusEnum;
import htcc.common.entity.log.ComplaintLogEntity;
import htcc.common.util.StringUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@ApiModel(description = "Request để cập nhật trạng thái khiếu nại")
public class UpdateComplaintStatusModel implements Serializable {

    private static final long serialVersionUID = 5912575583005150708L;

    @ApiModelProperty(notes = "Tháng của khiếu nại (yyyyMM)",
                      example = "202003")
    @NotEmpty
    public String yyyyMM = "";

    @ApiModelProperty(notes = "ID khiếu nại",
                      example = "#VNG-abcd123")
    @NotEmpty
    public String complaintId = "";

    @ApiModelProperty(notes = "Nội dung phản hồi từ công ty/ hệ thống",
                      example = "Đã xử lý khiếu nại")
    @NotEmpty
    public String response    = "";

    @ApiModelProperty(notes = "Trạng thái mới (0: Từ chối/ 1: Đã xử lý)",
                      example = "1")
    public int status = 1;
}
