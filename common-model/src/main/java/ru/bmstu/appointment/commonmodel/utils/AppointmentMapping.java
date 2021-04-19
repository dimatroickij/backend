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
        dto.setPacient(new PacientMapping().mapToPacient(appointment.getPacient()));
        dto.setSchedule(new ScheduleMapping().mapToSchedule(appointment.getSchedule()));
        dto.setDateRecord(appointment.getDateRecord());
//        dto.setEmail(appointment.getEmail());
        return dto;
    }
}
