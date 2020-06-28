package htcc.common.entity.checkin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
@AllArgsConstructor
@ApiModel(description = "Request để điểm danh (cho QUẢN LÝ)")
public class ManagerCheckinRequest implements Serializable {

    private static final long serialVersionUID = 59265150708L;

    @ApiModelProperty(notes = "Loại điểm danh (1: Vào / 2: Ra)",
                      example = "1")
    @Min(1)
    @Max(2)
    public int type = 0;

    @ApiModelProperty(notes = "Lý do dùng form điểm danh",
                      example = "Quên điểm danh vào")
    public String reason = "";

    @ApiModelProperty(notes = "Mã công ty",
                      example = "VNG")
    @NotEmpty
    public String companyId = "";

    @ApiModelProperty(notes = "Mã chi nhánh đã chọn",
                      example = "CAMPUS")
    @NotEmpty
    public String officeId = "";

    @ApiModelProperty(notes = "Tên nhân viên được điểm danh",
                      example = "admin")
    @NotEmpty
    public String username = "";

    @ApiModelProperty(notes = "Thời gian điểm danh (timestamp)",
                      example = "123")
    @NotEmpty
    @Min(0)
    public long clientTime = 0L;
}
