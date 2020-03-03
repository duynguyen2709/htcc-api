package htcc.gateway.service.config.file;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "service")
public class ServiceConfig {

    public boolean debugMode;
    public JWTConfig jwt;

    public static final String PUBLIC_API_PATH = "/public/";
    public static final String BASE_API_PATH = "/api/gateway";

    @Data
    public static class JWTConfig {
        public String key;
        public long expireSecond;
    }
}
