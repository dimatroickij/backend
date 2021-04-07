package ru.bmstu.sendnotificationsservice.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationListener {
    Logger LOG = LoggerFactory.getLogger(NotificationListener.class);

    @KafkaListener(topics = "notifications")
    void listener(String data) {
        LOG.info(data);
    }

    @KafkaListener(topics = "notifications")
    void commonListenerForMultipleTopics(String message) {
        LOG.info("MultipleTopicListener - {}", message);
    }
}
