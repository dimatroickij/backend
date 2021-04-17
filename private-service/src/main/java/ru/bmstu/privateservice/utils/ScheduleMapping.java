package ru.bmstu.privateservice.utils;

import org.springframework.stereotype.Service;
import ru.bmstu.privateservice.dto.DoctorResponse;
import ru.bmstu.privateservice.dto.PacientResponse;
import ru.bmstu.privateservice.dto.ScheduleResponse;
import ru.bmstu.privateservice.model.Pacient;
import ru.bmstu.privateservice.model.Schedule;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

@Service
public class ScheduleMapping {
    // Из Entity в Dto
    public ScheduleResponse mapToSchedule(Schedule schedule){
        ScheduleResponse dto = new ScheduleResponse();
        DateFormat dfTime = new SimpleDateFormat("HH:mm");
        DateFormat dfDate = new SimpleDateFormat("dd.MM.yyyy");
        dto.setId(schedule.getId());
        dto.setDoctor(new DoctorMapping().mapToDoctor(schedule.getDoctor()));
        dto.setDate(dfDate.format(schedule.getDate()));
        dto.setStartTime(dfTime.format(schedule.getStartTime()));
        dto.setEndTime(dfTime.format(schedule.getEndTime()));
        return dto;
    }
}
