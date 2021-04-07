package ru.bmstu.publicservice.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import ru.bmstu.publicservice.controller.Record;

import static org.apache.kafka.common.requests.FetchMetadata.log;

@Component
public class NotificationKafkaProducer {
    private KafkaTemplate<String, Record> notificationKafkaTemplate;

    private static final String NOTIFICATIONS_TOPIC = "notifications";

    public void sendNotification(Record message) {
        notificationKafkaTemplate.send(NOTIFICATIONS_TOPIC, message);
//        ListenableFuture<SendResult<String, Record>> future = notificationKafkaTemplate.send(NOTIFICATIONS_TOPIC,
//                message);
//        future.addCallback(new ListenableFutureCallback<>() {
//            @Override
//            public void onFailure(Throwable throwable) {
//                log.error("No " + throwable.getMessage());
//            }
//
//            @Override
//            public void onSuccess(SendResult<String, Record> stringStringSendResult) {
//                log.info("Yes");
//            }
//        });
    }
}
