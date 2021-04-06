package ru.bmstu.sendnotificationsservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {
    public static final String NOTIFICATIONS_TOPIC = "notifications";

    @Bean
    public NewTopic notifications() {
        return TopicBuilder.name(NOTIFICATIONS_TOPIC)
                .partitions(2)
                .replicas(2)
                .config(TopicConfig.MIN_IN_SYNC_REPLICAS_CONFIG, "2")
                .build();
    }
}
