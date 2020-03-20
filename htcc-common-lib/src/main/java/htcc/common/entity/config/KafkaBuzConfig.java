package htcc.common.entity.config;

import lombok.Data;

@Data
public class KafkaBuzConfig {

    public KafkaTopicConfig apiLog;

    @Data
    public static class KafkaTopicConfig {
        public String topicName = "";
        public String groupId   = "";
    }
}
