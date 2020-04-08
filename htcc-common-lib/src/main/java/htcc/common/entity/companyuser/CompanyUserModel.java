package htcc.common.entity.companyuser;

import htcc.common.util.NumberUtil;
import htcc.common.util.StringUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.beans.Transient;

@Data
@ApiModel(description = "Thông tin user")
public class CompanyUserModel {

    private static final long serialVersionUID = 1327000583005150807L;

    @ApiModelProperty(notes = "Mã công ty",
                      example = "VNG")
    public String companyId = "";

    @ApiModelProperty(notes = "Tên đăng nhập",
                      example = "admin")
    public String username = "";

    @ApiModelProperty(notes = "Mật khẩu (chỉ gửi khi tạo user mới)",
                      example = "123")
    public String password = "";

    @ApiModelProperty(notes = "email",
                      example = "naduy.hcmus@gmail.com")
    public String email = "";

    @ApiModelProperty(notes = "Sđt, ít nhất 10 chữ số",
                      example = "0948202709")
    public String phoneNumber = "";

    @ApiModelProperty(notes = "Trạng thái (1: Active / 0: Blocked)",
                      example = "1")
    public int status = 1;


    @Transient
    public String isValid(){
        if (StringUtil.isEmpty(companyId)) {
            return "Mã công ty không được rỗng";
        }

        if (StringUtil.isEmpty(username)) {
            return "Tên đăng nhập không được rỗng";
        }

        if (StringUtil.isEmpty(password)) {
            return "Mật khẩu không được rỗng";
        }

        if (StringUtil.valueOf(phoneNumber).length() < 10 ||
                NumberUtil.getLongValue(phoneNumber) == 0L) {
            return String.format("Số điện thoại %s không phù hợp định dạng", phoneNumber);
        }

        if (!StringUtil.isEmail(email)) {
            return String.format("Email %s không phù hợp định dạng email", email);
        }

        return StringUtil.EMPTY;
    }
}
