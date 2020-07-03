package htcc.log.service.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "web-service")
public class WebServiceConfig {
    private String baseUrl;
    private String params;
}
