package ru.bmstu.appointment.privateservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.bmstu.appointment.commonmodel.dto.ScheduleRequest;
import ru.bmstu.appointment.commonmodel.dto.ScheduleResponse;
import ru.bmstu.appointment.commonmodel.model.Doctor;
import ru.bmstu.appointment.commonmodel.model.Schedule;
import ru.bmstu.appointment.commonmodel.model.User;
import ru.bmstu.appointment.commonmodel.repository.DoctorRepository;
import ru.bmstu.appointment.commonmodel.repository.ScheduleRepository;
import ru.bmstu.appointment.commonmodel.repository.UserRepository;
import ru.bmstu.appointment.commonmodel.utils.ScheduleMapping;
import ru.bmstu.appointment.privateservice.config.jwt.JwtProvider;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    private UserRepository userRepository;

    @Autowired
    private ScheduleMapping scheduleMapping;

    @Autowired
    private JwtProvider jwtProvider;

    @SecurityRequirements
    @Operation(summary = "Расписание врачей в зависимости от заданных фильтров")
    @GetMapping("/all")
    public List<ScheduleResponse> all() {
        return scheduleRepository.findAll().stream().map(scheduleMapping::mapToSchedule).collect(Collectors.toList());
    }

    @SecurityRequirements
    @Operation(summary = "Просмотр конкретной записи")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ScheduleResponse.class))})})
    @GetMapping("/{id}")
    public ScheduleResponse get(@PathVariable UUID id) {
        if (scheduleRepository.existsById(id)) {
            Schedule schedule = scheduleRepository.getOne(id);
            return scheduleMapping.mapToSchedule(schedule);
        }
        return null;
    }

    @Operation(summary = "Добавление записи в расписание", description = "Доступно для пользователей с ролью DOCTOR")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Запись добавлена",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ScheduleResponse.class))})})
    @PostMapping("/add")
    public ScheduleResponse add(@RequestBody @Valid ScheduleRequest scheduleRequest, HttpServletRequest request)
            throws ParseException {
        String username = jwtProvider.getLoginFromToken(request.getHeader("Authorization").substring(7));
        User user = userRepository.findByUsername(username);
        Schedule schedule = new Schedule();
        if (user.getRole().getName().equals("ROLE_DOCTOR")) {
            Doctor doctor = doctorRepository.getDoctorByUser(userRepository.findByUsername(username));
            schedule.setDoctor(doctor);
        } else {
            schedule.setDoctor(doctorRepository.getOne(scheduleRequest.getDoctor()));
        }
        return getScheduleResponse(scheduleRequest, schedule);
    }

    private ScheduleResponse getScheduleResponse(@RequestBody @Valid ScheduleRequest request, Schedule schedule) throws ParseException {
        schedule.setDate(new SimpleDateFormat("dd.MM.yyyy").parse(request.getDate()));
        schedule.setStartTime(new SimpleDateFormat("HH:mm").parse(request.getStartTime()));
        schedule.setEndTime(new SimpleDateFormat("HH:mm").parse(request.getEndTime()));
        scheduleRepository.save(schedule);
        return scheduleMapping.mapToSchedule(schedule);
    }

    @Operation(summary = "Изменение записи", description = "Доступно для пользователей с ролью DOCTOR")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Расписание изменено",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ScheduleResponse.class))})})
    @PutMapping("/update/{id}")
    public ScheduleResponse update(@RequestBody @Valid ScheduleRequest scheduleRequest, HttpServletRequest request,
                                   @PathVariable UUID id) throws ParseException {
        if (scheduleRepository.existsById(id)) {
            Schedule schedule = scheduleRepository.getOne(id);
            String username = jwtProvider.getLoginFromToken(request.getHeader("Authorization").substring(7));
            User user = userRepository.findByUsername(username);
            if (schedule.getDoctor().getUser().getUsername().equals(username) ||
                    user.getRole().getName().equals("ROLE_ADMIN")) {
                return getScheduleResponse(scheduleRequest, schedule);
            }
        }
        return null;
    }


    @Operation(summary = "Удаление записи", description = "Доступно для пользователей с ролью DOCTOR")
    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable UUID id, HttpServletRequest request) {
        if (scheduleRepository.existsById(id)) {
            String username = jwtProvider.getLoginFromToken(request.getHeader("Authorization").substring(7));
            User user = userRepository.findByUsername(username);
            if (scheduleRepository.getOne(id).getDoctor().getUser().getUsername().equals(username) ||
                    user.getRole().getName().equals("ROLE_ADMIN")) {
                scheduleRepository.deleteById(id);
                return "OK";
            }
        }
        return null;
    }
}
