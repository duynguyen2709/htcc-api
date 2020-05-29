package htcc.common.entity.leavingrequest;

import htcc.common.util.DateTimeUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Data
@ApiModel(description = "Thông tin chi tiết đơn nghỉ phép")
public class LeavingRequestResponse implements Serializable {

    private static final long serialVersionUID = 5956786683005150708L;

    @ApiModelProperty(notes = "Mã đơn nghỉ phép", example = "#TICKET-001")
    public String leavingRequestId = "";

    @ApiModelProperty(notes = "Người gửi", example = "duyna")
    public String sender = "";

    @ApiModelProperty(notes = "Ngày submit đơn (yyyy-MM-dd)", example = "2020-04-05")
    public String dateSubmit = "";

    @ApiModelProperty(notes = "Ngày bắt đầu nghỉ (yyyy-MM-dd)", example = "2020-04-05")
    public String dateFrom = "";

    @ApiModelProperty(notes = "Ngày kết thúc nghỉ (yyyy-MM-dd)", example = "2020-04-07")
    public String dateTo = "";

    @ApiModelProperty(notes = "Có sử dụng ngày phép hay nghỉ không phép", example = "true")
    public boolean useDayOff = true;

    @ApiModelProperty(notes = "Loại nghỉ phép", example = "Nghỉ phép năm")
    public String category = "";

    @ApiModelProperty(notes = "Lý do nghỉ", example = "Nghỉ bệnh")
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
        this.useDayOff = model.useDayOff;
        this.category = model.category;
        this.reason = model.reason;
        this.detail = model.detail;
        this.status = model.status;
        this.response = model.response;
        this.approver = model.approver;
        this.dateSubmit = DateTimeUtil.parseTimestampToString(model.clientTime, "yyyy-MM-dd");

        if (this.detail.size() > 0) {
            this.detail.sort(LeavingRequest.LeavingDayDetail.getComparator());
            this.dateFrom = DateTimeUtil.convertToOtherFormat(this.detail.get(0).date, "yyyyMMdd", "yyyy-MM-dd");
            this.dateTo = DateTimeUtil.convertToOtherFormat(this.detail.get(this.detail.size() - 1).date, "yyyyMMdd", "yyyy-MM-dd");
        }
    }

    public static Comparator<LeavingRequestResponse> getComparator() {
        return new Comparator<LeavingRequestResponse>() {
            @Override
            public int compare(LeavingRequestResponse o1, LeavingRequestResponse o2) {
                String yyyyMMdd1 = DateTimeUtil.convertToOtherFormat(o1.getDateFrom(), "yyyy-MM-dd", "yyyyMMdd");
                String yyyyMMdd2 = DateTimeUtil.convertToOtherFormat(o2.getDateFrom(), "yyyy-MM-dd", "yyyyMMdd");

                long dateFrom1 = Long.parseLong(yyyyMMdd1);
                long dateFrom2 = Long.parseLong(yyyyMMdd2);

                return Long.compare(dateFrom1, dateFrom2);
            }
        };
    }
}
