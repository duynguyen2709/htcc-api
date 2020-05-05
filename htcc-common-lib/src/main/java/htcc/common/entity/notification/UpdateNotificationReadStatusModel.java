package htcc.common.entity.notification;

import htcc.common.constant.ClientSystemEnum;
import htcc.common.entity.jpa.BaseJPAEntity;
import htcc.common.util.StringUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Request cập nhật trạng thái đã đọc của noti")
public class UpdateNotificationReadStatusModel extends BaseJPAEntity implements Serializable {

    private static final long serialVersionUID = 6270983005150708L;

    public int clientId = ClientSystemEnum.MOBILE.getValue();

    @ApiModelProperty(notes = "Mã công ty",
                      example = "VNG")
    public String companyId = "";

    @ApiModelProperty(notes = "Tên đăng nhập",
                      example = "admin")
    public String username = "";

    @ApiModelProperty(notes = "Loại cập nhật (0 : tất cả noti/ 1 : 1 noti xác định)",
                      example = "1")
    public int    type = 0; // 0 = ALL, 1 = SINGLE

    @ApiModelProperty(notes = "Mã noti (trường hợp type = 1)",
                      example = "0001")
    public String notiId = "";

    @Override
    public String isValid() {
        if (StringUtil.isEmpty(companyId)) {
            return "Mã công ty không được rỗng";
        }

        if (StringUtil.isEmpty(username)) {
            return "Tên người dùng không được rỗng";
        }

        if (type != 0 && type != 1){
            return String.format("Loại %s không hợp lệ", type);
        }

        if (type == 1 && notiId.isEmpty()){
            return "Id của notification không được rỗng";
        }

        return StringUtil.EMPTY;
    }
}
