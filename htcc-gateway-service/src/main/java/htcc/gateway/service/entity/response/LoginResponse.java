package htcc.gateway.service.entity.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class LoginResponse implements Serializable {
    private static final long serialVersionUID = -8091879091924046844L;

    public String token;
}
