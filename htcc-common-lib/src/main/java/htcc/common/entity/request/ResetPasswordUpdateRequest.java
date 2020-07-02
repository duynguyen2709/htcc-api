package htcc.common.entity.request;

import htcc.common.util.StringUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResetPasswordUpdateRequest extends ResetPasswordRequest {
    private String password = "";

    @Override
    public String isValid() {
        String val = super.isValid();
        if (!val.isEmpty()) {
            return val;
        }

        if (StringUtil.valueOf(password).length() < 6) {
            return "Mật khẩu phải dài ít nhất 6 kí tự";
        }
        return "";
    }
}
