package ru.bmstu.privateservice.utils;

import org.springframework.stereotype.Service;
import ru.bmstu.privateservice.dto.DoctorResponse;
import ru.bmstu.privateservice.dto.PacientResponse;
import ru.bmstu.privateservice.dto.ScheduleResponse;
import ru.bmstu.privateservice.model.Pacient;
import ru.bmstu.privateservice.model.Schedule;

@Service
public class ScheduleMapping {
    // Из Entity в Dto
    public ScheduleResponse mapToSchedule(Schedule schedule){
        ScheduleResponse dto = new ScheduleResponse();
        dto.setId(schedule.getId());
        dto.setDoctor(new DoctorMapping().mapToDoctor(schedule.getDoctor()));
        dto.setDate(schedule.getDate());
        dto.setStartTime(schedule.getStartTime());
        dto.setEndTime(schedule.getEndTime());
        return dto;
    }
}
