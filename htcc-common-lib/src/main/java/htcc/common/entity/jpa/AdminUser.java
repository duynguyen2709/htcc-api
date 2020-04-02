package htcc.common.entity.jpa;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import htcc.common.constant.AccountStatusEnum;
import htcc.common.entity.jpa.BaseJPAEntity;
import htcc.common.util.NumberUtil;
import htcc.common.util.StringUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.*;

@Entity
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Thông tin user (các field * không cần gửi)")
public class AdminUser extends BaseJPAEntity {

    private static final long serialVersionUID = 1926368583005150807L;

    @Id
    @ApiModelProperty(notes = "(*) Tên đăng nhập",
                      example = "admin")
    public String username = "";

    @Column
    @NotEmpty
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ApiModelProperty(notes = "Mật khẩu (chỉ gửi khi tạo user mới)",
                      example = "abc")
    public String password = "";

    @Column
    @NotEmpty
    @ApiModelProperty(notes = "Họ tên",
                      example = "Nguyễn Anh Duy")
    public String fullName = "";

    @Column
    @Email(message = "Không đúng định dạng email")
    @ApiModelProperty(notes = "email",
                      example = "naduy.hcmus@gmail.com")
    public String email = "";

    @Column
    @Size(min = 10, max = 20, message = "Số điện thoại, ít nhất 10 chữ số")
    @ApiModelProperty(notes = "Số điện thoại, ít nhất 10 chữ số",
                      example = "0948202709")
    public String phoneNumber = "";

    @Column
    @ApiModelProperty(notes = "(*) Default (không cần set)",
                      example = "")
    public String avatar = "";

    @Column
    @ApiModelProperty(notes = "(*) Cấp độ tài khoản (0: SuperAdmin / 1: Admin)",
                      example = "1")
    public int role = 1;

    @Column
    @ApiModelProperty(notes = "(*) Trạng thái tài khoản (1: Active / 0: Blocked)",
                      example = "1")
    public int status = 1;

    @Override
    public String isValid() {
        if (StringUtil.valueOf(phoneNumber).length() < 10 ||
                NumberUtil.getLongValue(phoneNumber) == 0L) {
            return String.format("Số điện thoại %s không phù hợp định dạng", phoneNumber);
        }

        if (StringUtil.isEmpty(fullName)) {
            return "Họ tên không được rỗng";
        }

        if (!StringUtil.isEmail(email)) {
            return String.format("Email %s không phù hợp định dạng email", email);
        }

        if (AccountStatusEnum.fromInt(status) == null) {
            return String.format("Trạng thái %s không hợp lệ", status);
        }

        return StringUtil.EMPTY;
    }
}
