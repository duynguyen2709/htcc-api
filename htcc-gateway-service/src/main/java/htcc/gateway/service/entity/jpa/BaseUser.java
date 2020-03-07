package htcc.gateway.service.entity.jpa;

import entity.jpa.BaseJPAEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
public class BaseUser {
    public String username;
    public String password;
    public int status;
}
