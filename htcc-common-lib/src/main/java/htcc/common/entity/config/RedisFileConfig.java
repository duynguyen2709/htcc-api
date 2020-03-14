package htcc.common.entity.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

@Data
@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
@ConfigurationProperties(prefix = "redis")
public class RedisFileConfig {
    private boolean useRedis                        = false;
    private String  password                        = "";
    private boolean useMaster                       = false;
    private String  masterAddress                   = "";
    private String  nodeAddresses                   = "127.0.0.1:6379";
    private String  delimiterAddress                = ";";
    private int     scanInterval                    = 5000;
    private int     slaveConnectionMinimumIdleSize  = 10;
    private int     slaveConnectionPoolSize         = 100;
    private int     masterConnectionMinimumIdleSize = 10;
    private int     masterConnectionPoolSize        = 100;
    private int     idleConnectionTimeout           = 10000;
    private int     connectTimeout                  = 10000;
    private int     responseTimeout                 = 10000;
    private int     retryAttempts                   = 3;
    private int     retryInterval                   = 1000;
    private String  readMode                        = "SLAVE";
    private boolean kryoCodec                       = true;
}
