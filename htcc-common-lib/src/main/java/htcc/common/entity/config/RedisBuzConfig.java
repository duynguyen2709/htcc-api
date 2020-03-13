package htcc.common.entity.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix="redis-buz")
public class RedisBuzConfig {
    public String tokenFormat = "TOKEN-%s-%s-%s"; // clientId-companyId-username
}
