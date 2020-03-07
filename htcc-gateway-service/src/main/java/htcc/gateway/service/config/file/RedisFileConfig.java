package htcc.gateway.service.config.file;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

@Data
@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
@ConfigurationProperties(prefix="redis")
public class RedisFileConfig {
    private boolean useRedis;
    private String password;
    private boolean cluster;
    private String nodeAddresses;
    private String delimiterAddress;
    private int scanInterval;
    private int slaveConnectionMinimumIdleSize;
    private int slaveConnectionPoolSize;
    private int masterConnectionMinimumIdleSize;
    private int masterConnectionPoolSize;
    private int idleConnectionTimeout;
    private int connectTimeout;
    private int responseTimeout;
    private int retryAttempts;
    private int retryInterval;
    private String readMode;
    private boolean kryoCodec;
}
