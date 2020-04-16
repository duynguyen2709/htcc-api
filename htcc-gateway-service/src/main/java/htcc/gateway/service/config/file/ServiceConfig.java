package htcc.gateway.service.config.file;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

@Data
@Configuration
@ConfigurationProperties(prefix = "service")
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ServiceConfig {

    public boolean debugMode;
    public long maxFileSize = 10000000;
    public String internalServerIp = "127.0.0.1";
}
