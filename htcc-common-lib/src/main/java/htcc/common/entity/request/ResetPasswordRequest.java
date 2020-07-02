package htcc.common.entity.request;

import htcc.common.constant.ClientSystemEnum;
import htcc.common.util.StringUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Request để đổi mật khẩu")
public class ResetPasswordRequest implements Serializable {

    private static final long serialVersionUID = 59264005150807L;

    @ApiModelProperty(notes = "Mã định danh cho mỗi hệ thống khác nhau (mobile, web...) |" +
                                "1 - Mobile | 2 - Web quản lý" +
                                " công ty | 3 - Web quản trị hệ thống",
                      example = "1")
    @Min(1)
    public int clientId;

    @ApiModelProperty(notes = "Mã công ty (null đối với web admin)",
                      example = "VNG")
    public String companyId;

    @ApiModelProperty(notes = "Tên đăng nhập",
                      example = "admin")
    @NotEmpty
    public String username;

    public String token;
    public String email;

    public boolean same(ResetPasswordRequest request){
        if (this.clientId != request.clientId) {
            return false;
        }

        if (this.clientId == ClientSystemEnum.MOBILE.getValue() ||
                this.clientId == ClientSystemEnum.MANAGER_WEB.getValue()) {
            if (!this.companyId.equals(request.companyId)) {
                return false;
            }
        }

        if (!this.username.equals(request.username)) {
            return false;
        }
        return true;
    }

    public String isValid() {
        if (ClientSystemEnum.fromInt(clientId) == null) {
            return "Mã hệ thống không hợp lệ";
        }

        if ((clientId == 1 || clientId == 2) && StringUtil.isEmpty(companyId)) {
            return "Mã công ty không hợp lệ";
        }

        if (StringUtil.isEmpty(username)) {
            return "Tên người dùng không hợp lệ";
        }
//
//        if (token == null || token.isEmpty()) {
//            return "Dữ liệu không hợp lệ";
//        }

        return "";
    }
}
