package htcc.gateway.service.entity.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Response từ api đăng nhập")
public class LoginResponse implements Serializable {
    private static final long serialVersionUID = -8091879091924046844L;

    @ApiModelProperty(notes = "Token trả về",
                      example = "abc")
    public String token;

    @ApiModelProperty(notes = "Thông tin user hiện tại (chi tiết coi trong api GetUserInfo của từng hệ thống)")
    public Object user;
}
