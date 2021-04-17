package ru.bmstu.privateservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.bmstu.privateservice.dto.PacientRequest;
import ru.bmstu.privateservice.dto.PacientResponse;
import ru.bmstu.privateservice.model.Pacient;
import ru.bmstu.privateservice.repository.PacientRepository;
import ru.bmstu.privateservice.utils.PacientMapping;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pacient")
@Tag(name = "Pacient", description = "Работа с данными пациентов")
public class PacientController {
    @Autowired
    private PacientRepository pacientRepository;

//    @Autowired
//    private UserRepository userRepository;

    @Autowired
    private PacientMapping pacientMapping;

    @Operation(summary = "Список пациентов", description = "Доступно для пользователей с ролью ADMIN")
    @GetMapping("/all")
    public List<PacientResponse> all() {
        return pacientRepository.findAll().stream().map(pacientMapping::mapToPacient).collect(Collectors.toList());
    }

    @Operation(summary = "Просмотр данных конкретного пациента",
            description = "Доступно для пользователей с ролью ADMIN")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = PacientResponse.class))})})
    @GetMapping("/{id}")
    public PacientResponse get(@PathVariable UUID id) {
        if (pacientRepository.existsById(id)) {
            Pacient pacient = pacientRepository.getOne(id);
            return pacientMapping.mapToPacient(pacient);
        }
        return null;
    }

    @Operation(summary = "Добавление пациента", description = "Доступно для пользователей с ролью ADMIN")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Пациент добавлен",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = PacientResponse.class))})})
    @PostMapping("/add")
    public PacientResponse add(@RequestBody @Valid PacientRequest request) throws ParseException {
        Pacient pacient = new Pacient();
        pacient.setSurName(request.getSurName());
        pacient.setName(request.getName());
        pacient.setMiddleName(request.getMiddleName());
        pacient.setBirthDay(new SimpleDateFormat("dd.MM.yyyy").parse(request.getBirthDay()));
        pacient.setPolicy(request.getPolicy());

        pacientRepository.save(pacient);
        return pacientMapping.mapToPacient(pacient);
    }

    @Operation(summary = "Изменение данных у конкретного пациента",
            description = "Доступно для пользователей с ролью ADMIN")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Данные пациента изменены",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = PacientResponse.class))})})
    @PutMapping("/update/{id}")
    public PacientResponse update(@RequestBody PacientRequest request, @PathVariable UUID id) throws ParseException {
        if (pacientRepository.existsById(id)) {
            Pacient pacient = pacientRepository.getOne(id);
            pacient.setSurName(request.getSurName());
            pacient.setName(request.getName());
            pacient.setMiddleName(request.getMiddleName());
            pacient.setBirthDay(new SimpleDateFormat("dd.MM.yyyy").parse(request.getBirthDay()));
            pacient.setPolicy(request.getPolicy());
            pacientRepository.save(pacient);
            return pacientMapping.mapToPacient(pacient);
        }
        return null;
    }

    @Operation(summary = "Удаление пациента", description = "Доступно для пользователей с ролью ADMIN")
    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable UUID id) {
        if (pacientRepository.existsById(id)) {
//            try {
//                UUID idUser = pacientRepository.getOne(id).getUser().getId();
//                pacientRepository.getOne(id).setUser(null);
//                userRepository.deleteById(idUser);
//            }
//            catch (EntityNotFoundException ignored){
//            }
            pacientRepository.deleteById(id);
            return "OK";
        }
        return null;
    }
}
