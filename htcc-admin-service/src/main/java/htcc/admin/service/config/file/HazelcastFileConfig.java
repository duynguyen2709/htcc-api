package htcc.admin.service.config.file;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "service.hazelcast")
public class HazelcastFileConfig {

    public boolean useHazelcast;
    public boolean enableRest;
    public String  monitorLevel;
    public String  address;
}
