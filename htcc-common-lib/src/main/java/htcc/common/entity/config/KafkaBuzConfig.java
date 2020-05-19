package htcc.common.entity.config;

import lombok.Data;

@Data
public class KafkaBuzConfig {

    public KafkaTopicConfig apiLog;
    public KafkaTopicConfig checkInLog;
    public KafkaTopicConfig checkOutLog;
    public KafkaTopicConfig complaintLog;
    public KafkaTopicConfig leavingRequestLog;
    public KafkaTopicConfig eventCreateUser;
    public KafkaTopicConfig eventUpdateCompanyUser;
    public KafkaTopicConfig eventUpdateEmployeeInfo;
    public KafkaTopicConfig eventReadNotification;
    public KafkaTopicConfig eventChangeLogInStatus;
    public KafkaTopicConfig eventPushNotification;
    public KafkaTopicConfig eventAdminSendNotification;

    @Data
    public static class KafkaTopicConfig {
        public String topicName = "";
        public String groupId   = "";
    }
}
