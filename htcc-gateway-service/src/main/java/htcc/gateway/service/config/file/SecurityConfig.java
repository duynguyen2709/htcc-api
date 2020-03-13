package htcc.gateway.service.config.file;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "security")
public class SecurityConfig {

    public JWTConfig jwt;
    public InMemoryUser user;
    public boolean bypassJWT = false;

    @Data
    public static class JWTConfig {
        public String key;
        public long expireSecond;
    }

    @Data
    public static class InMemoryUser {
        public String name;
        public String password;
    }
}
