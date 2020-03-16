package htcc.gateway.service.config.file;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;


// Khởi tạo các Topic một cách tự động - Automatically add topics for all beans of type NewTopic:
@Configuration
public class KafkaTopicConfig {
    @Value(value= "${kafka.bootstrapAddress}")
    private String bootstrapAddress;

    @Value(value = "${kafka.topic.requestLog.name}")
    private String requestLogTopicName;

    @Value(value = "#{new Integer('${kafka.topic.requestLog.partition}')}")
    private Integer requestLogPartition;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic logTopic() {
        return new NewTopic(requestLogTopicName, requestLogPartition, (short) 1);
    }
}
