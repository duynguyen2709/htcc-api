package htcc.common.entity.checkin;

import htcc.common.constant.ComplaintStatusEnum;
import htcc.common.entity.jpa.BaseJPAEntity;
import htcc.common.util.DateTimeUtil;
import htcc.common.util.StringUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
@ApiModel(description = "Request để cập nhật trạng thái checkin")
public class UpdateCheckInStatusModel extends BaseJPAEntity implements Serializable {

    private static final long serialVersionUID = 59125755850708L;

    @ApiModelProperty(notes = "Tháng (yyyyMM)",
                      example = "202004")
    @NotEmpty
    public String yyyyMM = "";

    @ApiModelProperty(notes = "ID checkin",
                      example = "#VNG-abcd123")
    @NotEmpty
    public String checkInId = "";

    @ApiModelProperty(notes = "Trạng thái mới (0: Từ chối/ 1: Chấp thuận)",
                      example = "1")
    public int status = 1;

    @ApiModelProperty(notes = "Người phê duyệt (username của quản lý đang đăng nhập)",
                      example = "admin")
    @NotEmpty
    public String approver = "";

    @Override
    public String isValid() {
        if (StringUtil.isEmpty(approver)) {
            return "Người phê duyệt không được rỗng";
        }

        if (StringUtil.isEmpty(checkInId)) {
            return "ID đơn nghỉ phép không được rỗng";
        }

        if (status != ComplaintStatusEnum.DONE.getValue() &&
                status != ComplaintStatusEnum.REJECTED.getValue()) {
            return String.format("Trạng thái %s không hợp lệ", status);
        }

        if (!DateTimeUtil.isRightFormat(yyyyMM, "yyyyMM")) {
            return String.format("Tháng %s không phù hợp định dạng yyyyMM", yyyyMM);
        }

        return StringUtil.EMPTY;
    }
}
