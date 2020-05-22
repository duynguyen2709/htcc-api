package htcc.common.config;

import com.hazelcast.config.Config;
import htcc.common.entity.config.HazelcastFileConfig;

public class BaseHazelcastConfig {

    public static Config init(HazelcastFileConfig conf) {
        Config config = new Config();

        config.getNetworkConfig().setPublicAddress(conf.address);
        config.getNetworkConfig().setPort(conf.port);

        config.setProperty("hazelcast.rest.enabled", "false");
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
