package ru.bmstu.appointment.publicservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Запись уже занята. Выберите другое время.")
public class ActiveAppointmentException extends RuntimeException{
    public ActiveAppointmentException(String message) {
        super(message);
    }
}
