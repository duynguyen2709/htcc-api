package htcc.admin.service.config;

import com.hazelcast.config.Config;
import com.hazelcast.eureka.one.EurekaOneDiscoveryStrategyFactory;
import com.netflix.discovery.EurekaClient;
import htcc.common.config.BaseHazelcastConfig;
import htcc.common.entity.config.HazelcastFileConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ConditionalOnProperty( value="service.hazelcast.useHazelcast",
        havingValue = "true")
@Import({HazelcastFileConfig.class})
public class HazelcastConfiguration {

    @Autowired
    private HazelcastFileConfig conf;

    @Bean
    public Config hazelcastConfig(EurekaClient eurekaClient) {
        EurekaOneDiscoveryStrategyFactory.setEurekaClient(eurekaClient);
        return BaseHazelcastConfig.init(conf);
    }
}
