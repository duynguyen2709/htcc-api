package htcc.employee.service.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "security")
public class SecurityConfig {

    public InMemoryUser user;
    public boolean enableQrGenSecurity = false;

    @Data
    public static class InMemoryUser {
        public String name;
        public String password;
    }
}
