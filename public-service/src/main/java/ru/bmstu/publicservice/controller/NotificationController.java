package ru.bmstu.publicservice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.bmstu.publicservice.producer.NotificationKafkaProducer;

import java.util.UUID;

@Controller
public class NotificationController {

    private final NotificationKafkaProducer notificationKafkaProducer;

    public NotificationController(NotificationKafkaProducer notificationKafkaProducer) {
        this.notificationKafkaProducer = notificationKafkaProducer;
    }

    @GetMapping("/test")
    public void testRequest(){
        Record record = new Record(UUID.randomUUID(), "Test");
        notificationKafkaProducer.sendNotification(record);
    }
}
