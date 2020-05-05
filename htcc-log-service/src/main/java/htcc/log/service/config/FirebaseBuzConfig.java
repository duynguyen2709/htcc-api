package htcc.log.service.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

@Data
@Configuration
@ConfigurationProperties(prefix = "firebase")
@Order(Ordered.HIGHEST_PRECEDENCE)
public class FirebaseBuzConfig {

    public String serviceAccountFile;
    public String databaseURL;
}
