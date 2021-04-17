package ru.bmstu.appointment.publicservice.utils;

import org.springframework.stereotype.Service;
import ru.bmstu.appointment.publicservice.model.Appointment;
import ru.bmstu.appointment.publicservice.dto.AppointmentResponse;

@Service
public class AppointmentMapping {
    // Из Entity в Dto
    public AppointmentResponse mapToAppointment(Appointment appointment){
        AppointmentResponse dto = new AppointmentResponse();
        dto.setId(appointment.getId());
        dto.setPacient(appointment.getPacient());
        dto.setSchedule(appointment.getSchedule());
        dto.setDateRecord(appointment.getDateRecord());
        return dto;
    }
}
