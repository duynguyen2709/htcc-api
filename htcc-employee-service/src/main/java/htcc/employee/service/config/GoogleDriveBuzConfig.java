package htcc.employee.service.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

@Data
@Configuration
@ConfigurationProperties(prefix = "drive")
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GoogleDriveBuzConfig {
    public String serviceAccountId;
    public String p12File;
    public BuzConfig buz;

    @Data
    public static class BuzConfig {
        public String errorImage;
        public String complaintImageFolder;
    }
}
