package ru.bmstu.sendnotificationsservice.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationsListener {
    Logger LOG = LoggerFactory.getLogger(NotificationsListener.class);

    @KafkaListener(topics = "notifications-1")
    void listener(String data) {
        LOG.info(data);
    }

    @KafkaListener(
            topics = "notifications-1, notifications-2")//,
            //groupId = "${spring.kafka.consumer.group-id}")
    void commonListenerForMultipleTopics(String message) {
        LOG.info("MultipleTopicListener - {}", message);
    }
}
