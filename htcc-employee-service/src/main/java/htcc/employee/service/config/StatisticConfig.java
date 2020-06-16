package htcc.employee.service.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "statistic")
public class StatisticConfig {
    private int maxDayDiff = 0;
    private int distanceFromToday = 0;
}
