package ru.bmstu.privateservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.bmstu.privateservice.dto.ScheduleRequest;
import ru.bmstu.privateservice.dto.ScheduleResponse;
import ru.bmstu.privateservice.model.Schedule;
import ru.bmstu.privateservice.repository.DoctorRepository;
import ru.bmstu.privateservice.repository.ScheduleRepository;
import ru.bmstu.privateservice.utils.ScheduleMapping;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/schedule")
@Tag(name = "Schedule", description = "Работа с расписанием")
public class ScheduleController {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private ScheduleMapping scheduleMapping;

    @SecurityRequirements
    @Operation(summary = "Расписание врачей в зависимости от заданных фильтров")
    @GetMapping("/all")
    public List<ScheduleResponse> all() {
        return scheduleRepository.findAll().stream().map(scheduleMapping::mapToSchedule).collect(Collectors.toList());
    }

    @SecurityRequirements
    @Operation(summary = "Просмотр конкретной записи")
    @GetMapping("/{id}")
    public ScheduleResponse get(@PathVariable UUID id) {
        if (scheduleRepository.existsById(id)) {
            Schedule schedule = scheduleRepository.getOne(id);
            return scheduleMapping.mapToSchedule(schedule);
        }
        return null;
    }

    @Operation(summary = "Добавление записи в расписание", description = "Доступно для пользователей с ролью DOCTOR")
    @PostMapping("/add")
    public ScheduleResponse add(@RequestBody @Valid ScheduleRequest request) {
        Schedule schedule = new Schedule();
        schedule.setDoctor(doctorRepository.getOne(request.getDoctor()));
        schedule.setDate(request.getDate());
        schedule.setStartTime(request.getStartTime());
        schedule.setEndTime(request.getEndTime());
        scheduleRepository.save(schedule);
        return scheduleMapping.mapToSchedule(schedule);
    }

    @Operation(summary = "Изменение записи", description = "Доступно для пользователей с ролью DOCTOR")
    @PostMapping("/update/{id}")
    public ScheduleResponse update(@RequestBody @Valid ScheduleRequest request, @PathVariable UUID id) {
        if (scheduleRepository.existsById(id)) {
            Schedule schedule = scheduleRepository.getOne(id);
            schedule.setDate(request.getDate());
            schedule.setStartTime(request.getStartTime());
            schedule.setEndTime(request.getEndTime());
            scheduleRepository.save(schedule);
            return scheduleMapping.mapToSchedule(schedule);
        }
        return null;
    }


    @Operation(summary = "Удаление записи", description = "Доступно для пользователей с ролью DOCTOR")
    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable UUID id) {
        if (scheduleRepository.existsById(id)) {
            scheduleRepository.deleteById(id);
            return "OK";
        }
        return null;
    }
}
