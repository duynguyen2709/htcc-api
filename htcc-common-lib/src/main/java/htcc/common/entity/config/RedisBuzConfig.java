package htcc.common.entity.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class RedisBuzConfig {
    public String tokenFormat = "TOKEN-%s-%s-%s"; // clientId-companyId-username
    public String checkinFormat = "CHECKIN-%s-%s-%s"; // companyId-username-date
    public String checkoutFormat = "CHECKOUT-%s-%s-%s"; // companyId-username-date
}
