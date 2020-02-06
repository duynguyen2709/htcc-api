package vn.edu.hcmus.htccemployeeservice.config;

import brave.sampler.Sampler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoggingConfiguration {

    @Bean
    public Sampler sleuthSampler(){
        return Sampler.ALWAYS_SAMPLE;
    }
}
