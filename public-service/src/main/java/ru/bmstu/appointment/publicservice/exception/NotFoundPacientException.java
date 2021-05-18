package ru.bmstu.appointment.publicservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Пациент не найден. Убедитесь, что введены корретные данные " +
        "или прикрепитесь к поликлинике")
public class NotFoundPacientException extends RuntimeException {
    public NotFoundPacientException(String message) {
        super(message);
    }
}
