package htcc.common.entity.config;

import lombok.Data;

@Data
public class KafkaBuzConfig {

    public KafkaTopicConfig apiLog;
    public KafkaTopicConfig checkInLog;
    public KafkaTopicConfig checkOutLog;
    public KafkaTopicConfig complaintLog;

    @Data
    public static class KafkaTopicConfig {
        public String topicName = "";
        public String groupId   = "";
    }
}
