package htcc.common.entity.leavingrequest;

import htcc.common.comparator.DateComparator;
import htcc.common.util.DateTimeUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@ApiModel(description = "Thông tin chi tiết đơn nghỉ phép")
public class LeavingRequestResponse implements Serializable {

    private static final long serialVersionUID = 5956786683005150708L;

    @ApiModelProperty(notes = "Mã đơn nghỉ phép", example = "#TICKET-001")
    public String leavingRequestId = "";

    @ApiModelProperty(notes = "Người gửi", example = "duyna")
    public String sender = "";

    @ApiModelProperty(notes = "Ngày bắt đầu nghỉ (yyyy-MM-dd)", example = "2020-04-05")
    public String dateFrom = "";

    @ApiModelProperty(notes = "Ngày kết thúc nghỉ (yyyy-MM-dd)", example = "2020-04-07")
    public String dateTo = "";

    @ApiModelProperty(notes = "Loại nghỉ phép", example = "Nghỉ phép năm")
    public String category = "";

    @ApiModelProperty(notes = "lý do nghỉ", example = "Nghỉ bệnh")
    public String reason = "";

    @ApiModelProperty(notes = "Chi tiết ngày nghỉ (buổi nào)")
    public List<LeavingRequest.LeavingDayDetail> detail = new ArrayList<>();

    @ApiModelProperty(notes = "Trạng thái đơn xin nghỉ phép (0: Bị từ chối/ 1: Đã chấp nhận/ 2: Đang chờ xử lý)",
                      example = "2")
    public int status = 2;

    @ApiModelProperty(notes = "Phản hồi từ công ty (có thể empty)", example = "Đã giải quyết yêu cầu")
    public String response = "";

    @ApiModelProperty(notes = "Người phê duyệt (user/tên quản lý)", example = "admin")
    public String approver = "";

    public LeavingRequestResponse(LeavingRequestModel model) {
        this.leavingRequestId = model.leavingRequestId;
        this.sender = model.username;
        this.category = model.category;
        this.reason = model.reason;
        this.detail = model.detail;
        this.status = model.status;
        this.response = model.response;
        this.approver = model.approver;

        if (this.detail.size() > 0) {
            this.detail.sort(new DateComparator());
            this.dateFrom = DateTimeUtil.convertToOtherFormat(this.detail.get(0).date, "yyyyMMdd", "yyyy-MM-dd");
            this.dateTo = DateTimeUtil.convertToOtherFormat(this.detail.get(this.detail.size() - 1).date, "yyyyMMdd", "yyyy-MM-dd");
        }
    }
}
