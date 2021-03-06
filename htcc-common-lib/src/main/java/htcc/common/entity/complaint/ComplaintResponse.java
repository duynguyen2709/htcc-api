package htcc.common.entity.complaint;

import com.fasterxml.jackson.annotation.JsonIgnore;
import htcc.common.util.DateTimeUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Data
@ApiModel(description = "Thông tin phản hồi/ khiếu nại")
public class ComplaintResponse implements Serializable {

    private static final long serialVersionUID = 5912346683005150708L;

    @ApiModelProperty(notes = "Mã khiếu nại",
                      example = "#TICKET-001")
    @NotEmpty
    public String complaintId = "";

    @ApiModelProperty(notes = "Thứ + Ngày (T2 dd/MM/yyyy)",
                      example = "T2 22/03/2020")
    @NotEmpty
    public String date = "";

    @ApiModelProperty(notes = "Giờ (HH:mm)",
                      example = "09:15")
    @NotEmpty
    public String time = "";

    @ApiModelProperty(notes = "Đối tượng nhận (1: Hệ thống / 2: Công ty)",
                      example = "1")
    @Min(1)
    @Max(2)
    public int receiverType = 2;

    @ApiModelProperty(notes = "Gửi ẩn danh hay không (0/1)",
                      example = "0")
    @Min(0)
    @Max(1)
    public int isAnonymous = 0;

    @ApiModelProperty(notes = "Người gửi",
                      example = "ẨN DANH")
    public String sender = "";

    public transient String companyId = "";
    public transient String username  = "";

    @ApiModelProperty(notes = "Loại phản hồi/ khiếu nại",
                      example = "Phản hồi về phiếu lương")
    @NotEmpty
    public String category = "";

    @ApiModelProperty(notes = "Nội dung phản hồi/ khiếu nại",
                      example = "abc")
    public List<String> content = new ArrayList<>();

    @ApiModelProperty(notes = "Array URL Hình ảnh mô tả (tối đa 3 ảnh, có thể empty)")
    public List<String> images = new ArrayList<>();

    @ApiModelProperty(notes = "Trạng thái khiếu nại (0: Bị từ chối/ 1: Đã xử lý/ 2: Đang chờ xử lý)",
                      example = "2")
    @Min(0)
    @Max(2)
    public int status = 2;

    @ApiModelProperty(notes = "Phản hồi từ công ty (có thể empty)",
                      example = "Đã giải quyết yêu cầu")
    public List<String> response = new ArrayList<>();

    public ComplaintResponse(ComplaintModel model) {
        this.complaintId = model.complaintId;
        this.receiverType = model.receiverType;
        this.isAnonymous = model.isAnonymous;
        this.category = model.category;
        this.content = model.content;
        this.images = model.images;
        this.status = model.status;
        this.response = model.response;
        this.date = DateTimeUtil.parseTimestampToString(model.clientTime, "EEE dd/MM/yyyy");
        this.time = DateTimeUtil.parseTimestampToString(model.clientTime, "HH:mm");
        this.images = model.images;

        if (this.isAnonymous == 1) {
            this.sender = "ẨN DANH";
        } else {
            this.sender = String.format("%s - %s", model.companyId, model.username);
        }

        this.companyId = model.companyId;
        this.username = model.username;
    }
}
