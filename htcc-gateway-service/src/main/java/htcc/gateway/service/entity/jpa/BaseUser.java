package htcc.gateway.service.entity.jpa;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BaseUser {
    public String username;
    public String password;
    public int status;
}
