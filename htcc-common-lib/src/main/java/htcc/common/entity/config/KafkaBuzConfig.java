package htcc.common.entity.config;

import lombok.Data;

@Data
public class KafkaBuzConfig {

    public KafkaTopicConfig apiLog;
    public KafkaTopicConfig checkInLog;
    public KafkaTopicConfig checkOutLog;
    public KafkaTopicConfig complaintLog;
    public KafkaTopicConfig leavingRequestLog;
    public KafkaTopicConfig eventUpdateCompanyUser;
    public KafkaTopicConfig eventUpdateEmployeeInfo;

    @Data
    public static class KafkaTopicConfig {
        public String topicName = "";
        public String groupId   = "";
    }
}
