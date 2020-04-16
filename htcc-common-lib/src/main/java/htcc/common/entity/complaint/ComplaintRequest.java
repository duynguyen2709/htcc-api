package htcc.common.entity.complaint;

import com.fasterxml.jackson.annotation.JsonIgnore;
import htcc.common.entity.jpa.BaseJPAEntity;
import htcc.common.util.StringUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.io.File;
import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@ApiModel(description = "Request để gửi phản hồi/ khiếu nại")
public class ComplaintRequest extends BaseJPAEntity {

    private static final long serialVersionUID = 5912345583005150708L;

    @ApiModelProperty(notes = "Đối tượng nhận (1: Hệ thống / 2: Công ty)",
                      example = "1")
    public int receiverType;

    @ApiModelProperty(notes = "Gửi ẩn danh hay không (0/1)",
                      example = "0")
    public int isAnonymous;

    @ApiModelProperty(notes = "Mã công ty",
                      example = "HCMUS")
    public String companyId;

    @ApiModelProperty(notes = "Tên đăng nhập",
                      example = "duytv")
    public String username;

    @ApiModelProperty(notes = "Thời gian client gửi request: System.currentTimeMillis()",
                      example = "123")
    public long clientTime;

    @ApiModelProperty(notes = "Loại phản hồi/ khiếu nại",
                      example = "Phản hồi về phiếu lương")
    public String category;

    @ApiModelProperty(notes = "Nội dung phản hồi/ khiếu nại",
                      example = "abc")
    public String content;

    @ApiModelProperty(notes = "Hình ảnh mô tả (tối đa 3 ảnh)")
    public List<MultipartFile> images;

    @Override
    public String isValid() {
        if (receiverType < 1 || receiverType > 2){
            return "Đối tượng nhận phản hồi không hợp lệ";
        }

        if (isAnonymous < 0 || isAnonymous > 1){
            return "Loại ẩn danh không hợp lệ";
        }

        if (category.isEmpty()){
            return "Loại phản hồi không được rỗng";
        }

        if (content.isEmpty()){
            return "Nội dung phản hồi không được rỗng";
        }

        return StringUtil.EMPTY;
    }
}
