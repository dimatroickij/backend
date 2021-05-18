package ru.bmstu.appointment.publicservice.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.bmstu.appointment.commonmodel.dto.ExceptionResponse;
import ru.bmstu.appointment.publicservice.exception.ActiveAppointmentException;
import ru.bmstu.appointment.publicservice.exception.InvalidAppointmentException;
import ru.bmstu.appointment.publicservice.exception.NotFoundPacientException;

import javax.persistence.EntityNotFoundException;
import java.text.ParseException;

@RestControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(ActiveAppointmentException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ExceptionResponse> handleActiveAppointmentException(RuntimeException ex){
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setMessage(ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NotFoundPacientException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ExceptionResponse> handleNotFoundPacientException(RuntimeException ex){
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setMessage(ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ExceptionResponse> handleEntityNotFoundException(RuntimeException ex){
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setMessage(ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ParseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ExceptionResponse> handleParseException(ParseException ex){
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setMessage(ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidAppointmentException.class)
    @ResponseStatus(HttpStatus.NOT_MODIFIED)
    public ResponseEntity<ExceptionResponse> handleParseInvalidAppointmentException(InvalidAppointmentException ex){
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setMessage(ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_MODIFIED);
    }
}
