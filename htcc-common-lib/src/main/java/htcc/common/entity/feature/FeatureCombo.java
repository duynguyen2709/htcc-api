package htcc.common.entity.feature;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.reflect.TypeToken;
import htcc.common.entity.jpa.BaseJPAEntity;
import htcc.common.util.StringUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Thông tin gói tính năng")
public class FeatureCombo extends BaseJPAEntity {

    private static final long serialVersionUID = 130807L;

    @Id
    @NotEmpty
    @ApiModelProperty(notes = "Mã gói", example = "CB1")
    public String comboId = "";

    @Column
    @NotEmpty
    @ApiModelProperty(notes = "Tên gói combo", example = "Combo 1")
    public String comboName = "";

    @Column
    @ApiModelProperty(notes = "Phần trăm giảm giá (số float)", example = "10.5")
    public float discountPercentage = 0.0f;

    @Column
    public String comboDetail = "";

    @Transient
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public long totalPrice = 0L;

    public Map<String, Object> getComboDetail() {
        return StringUtil.json2Collection(this.comboDetail,
                new TypeToken<Map<String, Object>>(){}.getType());
    }

    public void setComboDetail(Map<String, Object> comboDetail) {
        this.comboDetail = StringUtil.toJsonString(comboDetail);
    }

    @Override
    public String isValid() {
        if (StringUtil.isEmpty(comboId)) {
            return "Mã gói không được rỗng";
        }

        if (StringUtil.isEmpty(comboName)) {
            return "Tên gói không được rỗng";
        }

        if (discountPercentage < 0) {
            return "Giá trị giảm giá phải lớn hơn 0";
        }

        if (discountPercentage >= 100) {
            return "Giá trị giảm giá phải nhỏ hơn 100";
        }

        if (comboDetail == null || comboDetail.isEmpty()) {
            return "Danh sách tính năng không được rỗng";
        }

        return StringUtil.EMPTY;
    }
}
