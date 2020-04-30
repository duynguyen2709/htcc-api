package htcc.log.service.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "service.notification")
public class NotificationConfig {
    public boolean allowRetryOnFail = true;
    public int retryDelaySecond = 3600;
    public int maxRetries = 3;
}
