package htcc.gateway.service.config.file;

import com.esotericsoftware.kryo.serializers.JavaSerializer;
import htcc.common.entity.base.RequestLogEntity;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    @Value(value= "${kafka.bootstrapAddress}")
    private String bootstrapAddress;

    @ConditionalOnProperty(value= "kafka.enabled")
    @Bean
    public ProducerFactory<String, RequestLogEntity> requestLogProducerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                bootstrapAddress);
        configProps.put(
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class);
        configProps.put(
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @ConditionalOnProperty(value= "kafka.enabled")
    @Bean
    public KafkaTemplate<String, RequestLogEntity> apiLogKafkaTemplate() {
        return new KafkaTemplate<>(requestLogProducerFactory());
    }
}

