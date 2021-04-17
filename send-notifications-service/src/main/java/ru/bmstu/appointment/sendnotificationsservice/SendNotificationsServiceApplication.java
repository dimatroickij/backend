package ru.bmstu.appointment.sendnotificationsservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class SendNotificationsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SendNotificationsServiceApplication.class, args);
    }

}
