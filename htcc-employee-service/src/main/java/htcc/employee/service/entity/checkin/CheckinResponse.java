package htcc.employee.service.entity.checkin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Response từ các api điểm danh")
public class CheckinResponse implements Serializable {

    private static final long serialVersionUID = 5926468583005150709L;

    @ApiModelProperty(notes = "Có thể điểm danh hôm nay không (trừ ngày nghỉ)")
    public boolean canCheckin = true;

    @ApiModelProperty(notes = "Giờ cho phép thực hiện điểm danh vào " +
                                " (sau thời gian này là vào trễ)",
                      example = "08:30")
    public String validCheckinTime = "";

    @ApiModelProperty(notes = "Giờ cho phép thực hiện điểm danh ra " +
                                "(trước thời gian này là về trễ)",
                      example = "17:30")
    public String validCheckoutTime = "";

    @ApiModelProperty(notes = "Tọa độ cho phép thực hiện điểm danh",
                      example = "10.762462")
    public float validLatitude = 10.762462f;

    @ApiModelProperty(notes = "Tọa độ cho phép thực hiện điểm danh",
                      example = "108.682762")
    public float validLongitude = 108.682762f;

    @ApiModelProperty(notes = "Đã điểm danh vào hay chưa")
    public boolean hasCheckedIn = false;

    @ApiModelProperty(notes = "Giờ thực hiện điểm danh vào " +
                                "(empty nếu chưa điểm danh vào)",
                      example = "08:05")
    public String checkinTime = "";

    @ApiModelProperty(notes = "Đã điểm danh ra hay chưa")
    public boolean hasCheckedOut = false;

    @ApiModelProperty(notes = "Giờ thực hiện điểm danh ra " +
                                "(empty nếu chưa điểm danh ra)",
                      example = "17:35")
    public String checkoutTime = "";

}
