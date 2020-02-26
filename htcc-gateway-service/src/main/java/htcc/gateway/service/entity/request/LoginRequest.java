package htcc.gateway.service.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@ApiModel(description = "Request để đăng nhập")
public class LoginRequest {

    @ApiModelProperty(notes = "Mã định danh cho mỗi hệ thống khác nhau (mobile, web...)",
                      example = "1")
    @Min(1)
    public int clientId;

    @ApiModelProperty(notes = "Giá trị hash SHA256 của request, tính bằng công thức " +
                                "SHA256(clientId|username|hashKey)",
                      example = "SHA256(1|duyna5|abc123)")
    @NotEmpty
    public String sig;

    @ApiModelProperty(notes = "Tên đăng nhập",
                      example = "duyna5")
    @NotEmpty
    public String username;

    @ApiModelProperty(notes = "Mật khẩu",
                      example = "123456")
    @NotEmpty
    public String password;

    @ApiModelProperty(notes = "Mã công ty (null đối với web admin)",
                      example = "VNG")
    public String companyId;
}
