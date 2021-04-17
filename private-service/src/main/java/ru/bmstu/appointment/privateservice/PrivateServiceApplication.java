package ru.bmstu.appointment.privateservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"ru.bmstu.appointment.privateservice.repository",
        "ru.bmstu.appointment.privateservice.dto", "ru.bmstu.appointment.privateservice.service",
        "ru.bmstu.appointment.privateservice.utils", "ru.bmstu.appointment.privateservice.model",
        "ru.bmstu.appointment", "ru.bmstu.appointment.privateservice"})
public class PrivateServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PrivateServiceApplication.class, args);
    }

}
