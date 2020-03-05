package htcc.gateway.service.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@ApiModel(description = "Request để đăng nhập")
public class LoginRequest {

    private static final long serialVersionUID = 5926468583005150707L;

    @ApiModelProperty(notes = "Mã định danh cho mỗi hệ thống khác nhau (mobile, web...) |" +
                                "1 - Mobile | 2 - Web quản lý" +
                                " công ty | 3 - Web quản trị hệ thống",
                      example = "1 - Mobile | 2 - Web quản lý công ty | " +
                              "3 - Web quản trị hệ thống")
    @Min(1)
    public int clientId;

    @ApiModelProperty(notes = "Mã công ty (null đối với web admin)",
                      example = "VNG")
    public String companyId;

    @ApiModelProperty(notes = "Tên đăng nhập",
                      example = "admin")
    @NotEmpty
    public String username;

    @ApiModelProperty(notes = "Mật khẩu",
                      example = "123")
    @NotEmpty
    public String password;

}
