package htcc.gateway.service.config;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.*;
import feign.Logger;
import feign.Retryer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignRibbonConfig {

    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    public Retryer retryer() {
        return new Retryer.Default(1000, 20000, 2);
    }
}
