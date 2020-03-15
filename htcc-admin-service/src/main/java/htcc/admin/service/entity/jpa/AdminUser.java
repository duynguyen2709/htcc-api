package htcc.admin.service.entity.jpa;

import htcc.common.constant.AccountStatusEnum;
import htcc.common.entity.jpa.BaseJPAEntity;
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
@ApiModel(description = "Thông tin user")
public class AdminUser extends BaseJPAEntity {

    private static final long serialVersionUID = 1926368583005150807L;

    @Id
    @ApiModelProperty(notes = "Tên đăng nhập",
                      example = "admin")
    public String username = "";

    @Column
    @NotEmpty
    @ApiModelProperty(notes = "Họ tên")
    public String fullName = "";

    @Column
    @Email(message = "Không đúng định dạng email")
    @ApiModelProperty(notes = "email")
    public String email = "";

    @Column
    @Size(min = 10, max = 20, message = "Số điện thoại, ít nhất 10 chữ số")
    @ApiModelProperty(notes = "Số điện thoại, ít nhất 10 chữ số")
    public String phoneNumber = "";

    @Column
    @ApiModelProperty(notes = "Default (không cần set)")
    public String avatar = "";

    @Transient
    @Column
    @ApiModelProperty(notes = "Cấp độ tài khoản (0: SuperAdmin / 1: Admin)")
    public int role = 1;

    @Column
    @ApiModelProperty(notes = "Trạng thái tài khoản (1: Active / 0: Blocked)")
    public int status = 1;

    @Override
    public boolean isValid() {
        return StringUtil.valueOf(phoneNumber).length() >= 10;
    }
}
