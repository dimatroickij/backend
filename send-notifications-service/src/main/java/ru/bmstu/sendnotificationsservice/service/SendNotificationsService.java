package ru.bmstu.sendnotificationsservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.messaging.MessagingException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.mail.internet.MimeMessage;

@Service
public class SendNotificationsService {
    private final String username;
    private final String sendTo;
    private final int resendDelayPerErrorInMinute;
    private final int resendDelayLimit;
    private final JavaMailSender mailSender;

    public SendNotificationsService(@Value("${spring.mail.username}") String username,
                                    @Value("${mail-sender.send-to}") String sendTo,
                                    @Value("${mail-sender.resend-delay-per-error-in-minute:1}") int resendDelayPerErrorInMinute,
                                    @Value("${mail-sender.resend-delay-limit:240}") int resendDelayLimit,
                                    JavaMailSender mailSender) {
        this.username = username;
        this.sendTo = sendTo;
        this.resendDelayPerErrorInMinute = resendDelayPerErrorInMinute;
        this.resendDelayLimit = resendDelayLimit;
        this.mailSender = mailSender;
    }

    @Scheduled(fixedDelayString = "${mail-sender.send-rate}")
    public void sendMail() throws MessagingException, javax.mail.MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        //helper.setFrom(.getEmail());
        helper.setTo(sendTo);
        //helper.setSubject(.getTopic());
        helper.setText("");
        try {
            mailSender.send(message);
        } catch (Exception e) {
            //log.error("Письмо не отправлено.  id : " + .getId(), e);
            //.setSendTime(LocalDateTime.now(ZoneOffset.UTC));
            //.setSendAttemptCount(.getSendAttemptCount() + 1);
            //Repository.save();
        }

    }
}
