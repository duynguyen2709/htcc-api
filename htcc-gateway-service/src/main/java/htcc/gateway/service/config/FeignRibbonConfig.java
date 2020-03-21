package htcc.gateway.service.config;

import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignRibbonConfig {



    @Bean
    public Retryer retryer() {
        return new Retryer.Default(1000, 20000, 2);
    }
}
