package htcc.common.entity.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "service.hazelcast")
public class HazelcastFileConfig {
    public boolean useHazelcast = false;
    public boolean enableRest = false;
    public String  monitorLevel = "OFF";
    public String  address = "127.0.0.1";
    public int     port = 5701;
}
