package ru.bmstu.appointment.publicservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages = "ru.bmstu.appointment.commonmodel.model")
@EnableJpaRepositories(basePackages = {"ru.bmstu.appointment.commonmodel"})
@SpringBootApplication(scanBasePackages = {"ru.bmstu.appointment.commonmodel.utils",
        "ru.bmstu.appointment.publicservice"})
public class PublicServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PublicServiceApplication.class, args);
    }

}
