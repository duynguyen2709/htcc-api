package htcc.common.entity.feature;

import com.fasterxml.jackson.annotation.JsonProperty;
import htcc.common.constant.ScreenEnum;
import htcc.common.entity.jpa.BaseJPAEntity;
import htcc.common.util.StringUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;

@Entity
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Thông tin đơn giá tính năng")
public class FeaturePrice extends BaseJPAEntity {

    private static final long serialVersionUID = 130807L;

    @Id
    @NotEmpty
    @ApiModelProperty(notes = "Mã tính năng",
                      example = "checkin")
    public String featureId = "";

    @Column
    @NotEmpty
    @ApiModelProperty(notes = "Tên tính năng", example = "Bảng lương")
    public String featureName = "";

    @Column
    @ApiModelProperty(notes = "Đơn giá", example = "100000")
    public long unitPrice = 0L;

    @Column
    @ApiModelProperty(notes = "Tính giá trên từng nhân viên (0/1)", example = "1")
    public int calcByEachEmployee = 0;

    @Column
    @ApiModelProperty(notes = "Màn hình navigate tương ứng",
                      example = "0")
    public int linkedScreen = 0;

    @Transient
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public String displayScreen = "";

    @Override
    public String isValid() {
        if (StringUtil.isEmpty(featureId)) {
            return "Mã icon không được rỗng";
        }

        if (StringUtil.isEmpty(featureName)) {
            return "URL icon không được rỗng";
        }

        if (ScreenEnum.fromInt(linkedScreen) == null) {
            return String.format("Không tìm thấy màn hình liên kết có id %s", linkedScreen);
        }

        if (unitPrice < 0) {
            return "Đơn giá phải là số dương";
        }

        if (calcByEachEmployee != 0 && calcByEachEmployee != 1) {
            return "Giá trị Tính giá trên từng nhân viên không hợp lệ : " + calcByEachEmployee;
        }

        return StringUtil.EMPTY;
    }
}
