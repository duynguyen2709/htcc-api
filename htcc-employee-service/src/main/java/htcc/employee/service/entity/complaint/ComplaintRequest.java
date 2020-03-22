package htcc.employee.service.entity.complaint;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
@AllArgsConstructor
@ApiModel(description = "Request để gửi phản hồi/ khiếu nại")
public class ComplaintRequest implements Serializable {

    private static final long serialVersionUID = 5912345583005150708L;

    @ApiModelProperty(notes = "Đối tượng nhận (1: Hệ thống / 2: Công ty)",
                      example = "1")
    @Min(1)
    @Max(2)
    public int receiverType;

    @ApiModelProperty(notes = "Gửi ẩn danh hay không (0/1)",
                      example = "0")
    @Min(0)
    @Max(1)
    public int isAnonymous;

    @ApiModelProperty(notes = "Mã công ty",
                      example = "HCMUS")
    @NotEmpty
    public String companyId;

    @ApiModelProperty(notes = "Tên đăng nhập",
                      example = "duytv")
    @NotEmpty
    public String username;

    @ApiModelProperty(notes = "Thời gian client gửi request: System.currentTimeMillis()",
                      example = "123")
    @NotEmpty
    @Min(0)
    public long clientTime;

    @ApiModelProperty(notes = "Loại phản hồi/ khiếu nại",
                      example = "Phản hồi về phiếu lương")
    @NotEmpty
    public String category;

    @ApiModelProperty(notes = "Tiêu đề",
                      example = "Gửi bộ phận HR...")
    @NotEmpty
    public String title;

    @ApiModelProperty(notes = "Nội dung phản hồi/ khiếu nại",
                      example = "abc")
    @NotEmpty
    public String content;

    @JsonIgnore
    @ApiModelProperty(notes = "Hình ảnh mô tả (tối đa 3 ảnh)")
    public MultipartFile[] images;
}
