package ru.bmstu.appointment.publicservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"ru.bmstu.appointment.privateservice.repository",
"ru.bmstu.appointment.privateservice.dto", "ru.bmstu.appointment.privateservice.service",
"ru.bmstu.appointment.privateservice.utils", "ru.bmstu.appointment.privateservice.model",
"ru.bmstu.appointment", "ru.bmstu.appointment.publicservice"})
@EnableJpaRepositories(basePackages = {"ru.bmstu.appointment.privateservice.service", "ru.bmstu.appointment.publicservice"})
@EntityScan(basePackages = "ru.bmstu")
//@EnableJpaRepositories("ru.bmstu.appointment.privateservice.repository")
public class PublicServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PublicServiceApplication.class, args);
    }

}
