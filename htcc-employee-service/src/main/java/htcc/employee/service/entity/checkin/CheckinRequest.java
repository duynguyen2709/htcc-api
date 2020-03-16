package htcc.employee.service.entity.checkin;

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

    @ApiModelProperty(notes = "Mã công ty",
                      example = "VNG")
    @NotEmpty
    public String companyId;

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
                      example = "108.682762")
    @NotEmpty
    @Min(0)
    public float longitude;
//
//    @ApiModelProperty(notes = "Tọa độ cho phép điểm danh (lấy từ api GetCheckinInfo)",
//                      example = "10.762462")
//    @NotEmpty
//    @Min(0)
//    public float validLatitude;
//
//    @ApiModelProperty(notes = "Tọa độ cho phép điểm danh (lấy từ api GetCheckinInfo)",
//                      example = "108.682762")
//    @NotEmpty
//    @Min(0)
//    public float validLongitude;
//
//    @ApiModelProperty(notes = "Khoảng cách cho phép thực hiện điểm danh (lấy từ api GetCheckinInfo)",
//                      example = "10")
//    @NotEmpty
//    @Min(0)
//    public int maxAllowDistance = 10;

    @ApiModelProperty(notes = "Điểm danh bằng mạng 3G hoặc wifi",
                      example = "false")
    public boolean usedWifi = false;

    @ApiModelProperty(notes = "IP đã thực hiện điểm danh",
                      example = "127.0.0.1")
    public String ip = "";
}
