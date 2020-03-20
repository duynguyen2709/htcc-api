package htcc.common.component.kafka;

import htcc.common.entity.config.KafkaBuzConfig;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "kafka")
public class KafkaFileConfig {
    public boolean        enableProducer   = false;
    public boolean        enableConsumer   = false;
    public String         bootstrapAddress = "";
    public KafkaBuzConfig buz;
}
