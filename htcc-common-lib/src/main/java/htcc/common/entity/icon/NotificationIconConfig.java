package htcc.common.entity.icon;

import htcc.common.constant.AccountStatusEnum;
import htcc.common.constant.ScreenEnum;
import htcc.common.entity.jpa.BaseJPAEntity;
import htcc.common.util.NumberUtil;
import htcc.common.util.StringUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

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

    @Override
    public String isValid() {
        if (StringUtil.isEmpty(iconId)) {
            return "Mã icon không được rỗng";
        }

        if (StringUtil.isEmpty(iconURL)) {
            return "URL icon không được rỗng";
        }

        if (ScreenEnum.fromInt(screenId) == null) {
            return String.format("Màn hình %s không hợp lệ", screenId);
        }

        return StringUtil.EMPTY;
    }
}
