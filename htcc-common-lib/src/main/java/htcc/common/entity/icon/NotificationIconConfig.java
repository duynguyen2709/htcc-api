package htcc.common.entity.icon;

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
@ApiModel(description = "Thông tin icon")
public class NotificationIconConfig extends BaseJPAEntity {

    private static final long serialVersionUID = 1326965150807L;

    @Id
    @ApiModelProperty(notes = "Mã icon",
                      example = "checkin")
    public String iconId = "";

    @Column
    @NotEmpty
    @ApiModelProperty(notes = "URL icon mới cần tạo")
    public String iconURL = "";

    @Column
    @ApiModelProperty(notes = "Màn hình navigate tương ứng",
                      example = "0")
    public int screenId = 0;

    @Transient
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public String screenDescription = "";

    @Override
    public String isValid() {
        if (StringUtil.isEmpty(iconId)) {
            return "Mã icon không được rỗng";
        }

        if (StringUtil.isEmpty(iconURL)) {
            return "URL icon không được rỗng";
        }

        if (ScreenEnum.fromInt(screenId) == null) {
            return String.format("Không tìm thấy màn hình liên kết có id %s", screenId);
        }

        return StringUtil.EMPTY;
    }
}
