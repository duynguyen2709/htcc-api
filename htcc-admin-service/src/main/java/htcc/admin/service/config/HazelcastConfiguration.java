package htcc.admin.service.config;

import com.hazelcast.config.Config;
import com.hazelcast.eureka.one.EurekaOneDiscoveryStrategyFactory;
import com.netflix.discovery.EurekaClient;
import htcc.admin.service.config.file.HazelcastFileConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(
        value="service.hazelcast.useHazelcast",
        havingValue = "true",
        matchIfMissing = false)
public class HazelcastConfiguration {

    @Autowired
    private HazelcastFileConfig conf;

    @Bean
    public Config hazelcastConfig(EurekaClient eurekaClient) {
        EurekaOneDiscoveryStrategyFactory.setEurekaClient(eurekaClient);
        Config config = new Config();

        config.getNetworkConfig().setPublicAddress(conf.address);

        config.setProperty("hazelcast.rest.enabled", conf.enableRest + "");
        config.setProperty("hazelcast.health.monitoring.level", conf.monitorLevel);

        config.getNetworkConfig().getJoin().getMulticastConfig().setEnabled(false);
        config.getNetworkConfig().getJoin().getEurekaConfig()
                .setEnabled(true)
                .setProperty("self-registration", "true")
                .setProperty("namespace", "hazelcast")
                .setProperty("use-metadata-for-host-and-port", "true")
                .setUsePublicIp(true);

        return config;
    }
}
