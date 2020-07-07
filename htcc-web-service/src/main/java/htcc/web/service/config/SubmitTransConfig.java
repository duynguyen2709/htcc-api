package htcc.web.service.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "submittrans")
public class SubmitTransConfig {
    private String hashKey    = "";
    private String baseUrl    = "";
    private String methodName = "";
}
