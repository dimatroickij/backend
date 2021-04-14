package ru.bmstu.publicservice.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import ru.bmstu.publicservice.config.KafkaTopicConfig;

@Component
public class NotificationProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    Logger LOG = LoggerFactory.getLogger(NotificationProducer.class);

    @Autowired
    NotificationProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessageWithCallback(String message) {
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(KafkaTopicConfig.NOTIFICATIONS_TOPIC,
                message);
        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onSuccess(SendResult<String, String> result) {
                LOG.info("Message [{}] delivered with offset {}", message, result.getRecordMetadata().offset());
            }

            @Override
            public void onFailure(Throwable ex) {
                LOG.warn("Unable to deliver message [{}]. {}", message, ex.getMessage());
            }
        });
    }
}
