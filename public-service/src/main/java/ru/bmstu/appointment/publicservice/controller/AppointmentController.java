package ru.bmstu.appointment.publicservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bmstu.appointment.commonmodel.model.Appointment;
import ru.bmstu.appointment.commonmodel.dto.AppointmentRequest;
import ru.bmstu.appointment.commonmodel.dto.AppointmentResponse;
import ru.bmstu.appointment.commonmodel.model.Pacient;
import ru.bmstu.appointment.commonmodel.repository.AppointmentRepository;
import ru.bmstu.appointment.commonmodel.repository.PacientRepository;
import ru.bmstu.appointment.commonmodel.repository.ScheduleRepository;
import ru.bmstu.appointment.commonmodel.utils.AppointmentMapping;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/appointment")
@Tag(name = "Appointment", description = "Операции с записями пациентов к врачу")
public class AppointmentController {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private PacientRepository pacientRepository;

    @Autowired
    private AppointmentMapping appointmentMapping;

    @Operation(summary = "Создание записи на приём к врачу")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Запись создана",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = AppointmentResponse.class))})})
    @PostMapping("/add")
    public AppointmentResponse add(@RequestBody @Valid AppointmentRequest request) throws ParseException {
        Appointment appointment = new Appointment();
        appointment.setSchedule(scheduleRepository.getOne(request.getSchedule()));
        appointment.setDateRecord(new Date());


        if (pacientRepository.existsBySurNameAndNameAndMiddleNameAndBirthDayAndPolicy(request.getSurName(),
                request.getName(), request.getMiddleName(),
                new SimpleDateFormat("dd.MM.yyyy").parse(request.getBirthDay()), request.getPolicy())){

            Pacient pacient = pacientRepository.findBySurNameAndNameAndMiddleNameAndBirthDayAndPolicy(request.getSurName(),
                    request.getName(), request.getMiddleName(),
                    new SimpleDateFormat("dd.MM.yyyy").parse(request.getBirthDay()),
                    request.getPolicy());
            appointment.setPacient(pacient);
            appointment.setIsActive(true);
            appointmentRepository.save(appointment);
            return appointmentMapping.mapToAppointment(appointment);
        }
        return null;
    }
}
