package htcc.common.component.kafka;

import htcc.common.entity.config.KafkaBuzConfig;
import htcc.common.util.StringUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Log4j2
@Import({KafkaFileConfig.class})
public class KafkaProducerService {

    private KafkaTemplate kafkaTemplate = null;

    @Autowired
    private KafkaFileConfig config;

    @Bean
    @ConditionalOnProperty(value = "kafka.enableProducer", havingValue = "true")
    private ProducerFactory<String, String> producerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, config.bootstrapAddress);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.ACKS_CONFIG, "1");
        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean
    @ConditionalOnProperty(value = "kafka.enableProducer", havingValue = "true")
    private KafkaTemplate<String, String> kafkaTemplate() {
        kafkaTemplate = new KafkaTemplate<>(producerFactory());
        return kafkaTemplate;
    }

    public void sendMessage(String topic, Object message) {
        if (kafkaTemplate == null) {
            return;
        }

        String msg = "";

        if (message instanceof String) {
            msg = (String)message;
        } else {
            msg = StringUtil.toJsonString(message);
        }
        kafkaTemplate.send(topic, msg);
    }

    public KafkaBuzConfig getBuzConfig(){
        return config.getBuz();
    }
}
