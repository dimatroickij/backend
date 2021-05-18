package ru.bmstu.appointment.publicservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_MODIFIED, reason = "Запись неактивна")
public class InvalidAppointmentException extends Exception{
    public InvalidAppointmentException(String message) {
        super(message);
    }
}
