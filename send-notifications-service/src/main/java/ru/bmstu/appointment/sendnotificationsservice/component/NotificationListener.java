package ru.bmstu.appointment.sendnotificationsservice.component;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Component;
import ru.bmstu.appointment.sendnotificationsservice.service.EmailService;

@Component
@RequiredArgsConstructor
public class NotificationListener {

    private final EmailService emailService;

    Logger LOG = LoggerFactory.getLogger(NotificationListener.class);

    @KafkaListener(topics = "notifications")
    public void listener(String data) {
        LOG.info(data);

        try {
            String subject = "Запись к врачу";
            String text = "Вы записаны на приём к врачу. Номер записи: " + data.split("###")[1];
            System.out.println(data.split("###")[0]);
            emailService.sendSimpleMessage(data.split("###")[0], subject, text);
        } catch (MailException e) {
            LOG.error("Could not send e-mail", e);
        }
    }
}
