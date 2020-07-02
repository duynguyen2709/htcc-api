package htcc.web.service.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordRequest implements Serializable {

    private static final long serialVersionUID = 59264005150807L;

    private int    clientId;
    private String companyId;
    private String username;
    private String password;
    private String token;

    public String isValid() {
        if (clientId < 1 || clientId > 3) {
            return "Mã hệ thống không hợp lệ";
        }

        if ((clientId == 1 || clientId == 2) && (companyId == null || companyId.isEmpty())) {
            return "Mã công ty không hợp lệ";
        }

        if (username == null || username.isEmpty()) {
            return "Tên người dùng không hợp lệ";
        }

        if (token == null || token.isEmpty()) {
            return "Dữ liệu không hợp lệ";
        }

        return "";
    }
}
