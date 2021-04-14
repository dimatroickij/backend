package ru.bmstu.publicservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.bmstu.publicservice.component.NotificationProducer;

@Controller
public class NotificationController {

    @Autowired
    private NotificationProducer notificationProducer;

    @GetMapping("/send/{email}")
    public void sendEmail(@PathVariable String email) {
        notificationProducer.sendMessageWithCallback(email);
    }
}
