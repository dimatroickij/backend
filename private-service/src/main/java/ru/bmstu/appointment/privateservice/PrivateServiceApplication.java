package ru.bmstu.appointment.privateservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages = "ru.bmstu.appointment.commonmodel.model")
@EnableJpaRepositories(basePackages = {"ru.bmstu.appointment.commonmodel", "ru.bmstu.appointment.privateservice"})
@SpringBootApplication(scanBasePackages = {"ru.bmstu.appointment.commonmodel", "ru.bmstu.appointment.privateservice"})
public class PrivateServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PrivateServiceApplication.class, args);
    }

}
