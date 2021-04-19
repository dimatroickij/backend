package ru.bmstu.appointment.commonmodel.utils;

import org.springframework.stereotype.Service;
import ru.bmstu.appointment.commonmodel.dto.AppointmentResponse;
import ru.bmstu.appointment.commonmodel.model.Appointment;

@Service
public class AppointmentMapping {
    // Из Entity в Dto
    public AppointmentResponse mapToAppointment(Appointment appointment){
        AppointmentResponse dto = new AppointmentResponse();
        dto.setId(appointment.getId());
//        dto.setPacient(appointment.getPacient());
//        dto.setSchedule(appointment.getSchedule());
        dto.setDateRecord(appointment.getDateRecord());
        return dto;
    }
}
