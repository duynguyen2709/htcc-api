package htcc.gateway.service.entity.request;

import lombok.Data;

@Data
public class LoginRequest {
    public int clientId;
    public String sig;

    public String username;
    public String password;
    public String companyId;
}
