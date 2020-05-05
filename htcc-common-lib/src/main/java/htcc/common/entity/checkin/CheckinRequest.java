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
@ApiModel(description = "Request để điểm danh")
public class CheckinRequest implements Serializable {

    private static final long serialVersionUID = 5926468583005150708L;

    @ApiModelProperty(notes = "Loại điểm danh (1: Vào / 2: Ra)",
                      example = "1")
    @Min(1)
    @Max(2)
    public int type;
//
//    @ApiModelProperty(notes = "Cách thức điểm danh (1: Tọa độ/ 2: Hình ảnh/ 3: QR/ 4: Form)",
//                      example = "1")
//    @Min(1)
//    @Max(4)
//    public int subType;

    @ApiModelProperty(notes = "Mã công ty",
                      example = "VNG")
    @NotEmpty
    public String companyId;

    @ApiModelProperty(notes = "Mã chi nhánh đã chọn",
                      example = "CAMPUS")
    @NotEmpty
    public String officeId;

    @ApiModelProperty(notes = "Tên đăng nhập",
                      example = "admin")
    @NotEmpty
    public String username;

    @ApiModelProperty(notes = "Thời gian client gửi request: System.currentTimeMillis()",
                      example = "123")
    @NotEmpty
    @Min(0)
    public long clientTime;

    @ApiModelProperty(notes = "Tọa độ khi thực hiện điểm danh",
                      example = "10.762462")
    @NotEmpty
    @Min(0)
    public float latitude;

    @ApiModelProperty(notes = "Tọa độ khi thực hiện điểm danh",
                      example = "106.682752")
    @NotEmpty
    @Min(0)
    public float longitude;

    @ApiModelProperty(notes = "Điểm danh bằng mạng 3G hoặc wifi",
                      example = "false")
    public boolean usedWifi = false;

    @ApiModelProperty(notes = "IP đã thực hiện điểm danh",
                      example = "127.0.0.1")
    public String ip = "";
}
