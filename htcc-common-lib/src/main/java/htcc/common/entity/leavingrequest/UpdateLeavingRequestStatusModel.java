package htcc.common.entity.leavingrequest;

import htcc.common.constant.ComplaintStatusEnum;
import htcc.common.constant.ReturnCodeEnum;
import htcc.common.entity.base.BaseResponse;
import htcc.common.entity.jpa.BaseJPAEntity;
import htcc.common.util.DateTimeUtil;
import htcc.common.util.StringUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
@ApiModel(description = "Request để cập nhật trạng thái đơn nghỉ phép")
public class UpdateLeavingRequestStatusModel extends BaseJPAEntity implements Serializable {

    private static final long serialVersionUID = 5912575583225150708L;

    @ApiModelProperty(notes = "Tháng (yyyyMM)",
                      example = "202004")
    @NotEmpty
    public String yyyyMM = "";

    @ApiModelProperty(notes = "ID đơn nghỉ phép",
                      example = "#VNG-abcd123")
    @NotEmpty
    public String leavingRequestId = "";

    @ApiModelProperty(notes = "Nội dung phản hồi từ công ty/ hệ thống",
                      example = "Đã xử lý khiếu nại")
    @NotEmpty
    public String response    = "";

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

        if (StringUtil.isEmpty(leavingRequestId)) {
            return "Username không được rỗng";
        }

        if (status != ComplaintStatusEnum.DONE.getValue() &&
                status != ComplaintStatusEnum.REJECTED.getValue()) {
            return String.format("Trạng thái %s không hợp lệ", status);
        }

        if (DateTimeUtil.isRightFormat(yyyyMM, "yyyyMM") == false) {
            return String.format("Tháng %s không phù hợp định dạng yyyyMM", yyyyMM);
        }

        return StringUtil.EMPTY;
    }
}
