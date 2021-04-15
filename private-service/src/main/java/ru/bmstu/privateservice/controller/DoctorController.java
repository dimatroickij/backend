package ru.bmstu.privateservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.bmstu.privateservice.dto.DoctorRequest;
import ru.bmstu.privateservice.dto.DoctorResponse;
import ru.bmstu.privateservice.dto.DoctorUpdateRequest;
import ru.bmstu.privateservice.model.Doctor;
import ru.bmstu.privateservice.model.User;
import ru.bmstu.privateservice.repository.DoctorRepository;
import ru.bmstu.privateservice.repository.SpecialityRepository;
import ru.bmstu.privateservice.repository.UserRepository;
import ru.bmstu.privateservice.service.UserService;
import ru.bmstu.privateservice.utils.DoctorMapping;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/doctor")
@Tag(name = "Doctor", description = "Операции с данными работников")
public class DoctorController {
    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SpecialityRepository specialityRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private DoctorMapping doctorMapping;

    @Operation(summary = "Список сотрудников")
    @GetMapping("/all")
    public List<DoctorResponse> all() {
        return doctorRepository.findAll().stream().map(doctorMapping::mapToDoctor).collect(Collectors.toList());
    }

    @Operation(summary = "Список сотрудников в зависимости от специальности")
    @GetMapping("/all/{speciality}")
    public List<DoctorResponse> getBySpeciality(@PathVariable Long speciality) {
        return doctorRepository.findAllBySpeciality(specialityRepository.getOne(speciality)).stream().
                map(doctorMapping::mapToDoctor).collect(Collectors.toList());
    }

    @Operation(summary = "Просмотр данных конкретного сотрудника")
    @GetMapping("/{id}")
    public DoctorResponse get(@PathVariable UUID id) {
        if (doctorRepository.existsById(id)) {
            Doctor doctor = doctorRepository.getOne(id);
            return doctorMapping.mapToDoctor(doctor);
        }
        return null;
    }

    @Operation(summary = "Добавление сотрудника")
    @PostMapping("/add")
    public DoctorResponse add(@RequestBody @Valid DoctorRequest request) {
        Doctor doctor = new Doctor();
        doctor.setSurName(request.getSurName());
        doctor.setName(request.getName());
        doctor.setMiddleName(request.getMiddleName());
        doctor.setSpeciality(specialityRepository.getOne(request.getSpeciality()));

        User u = new User();
        u.setPassword(request.getPassword());
        u.setUsername(request.getUsername());
        u.setEmail(request.getEmail());
        userService.saveUser(u, "ROLE_DOCTOR");

        doctor.setUser(u);
        doctorRepository.save(doctor);
        return doctorMapping.mapToDoctor(doctor);
    }

    @Operation(summary = "Изменение данных у конкретного сотрудника")
    @PutMapping("/update/{id}")
    public DoctorResponse update(@RequestBody DoctorUpdateRequest request, @PathVariable UUID id) {
        if (doctorRepository.existsById(id)) {
            Doctor doctor = doctorRepository.getOne(id);
            doctor.setSurName(request.getSurName());
            doctor.setName(request.getName());
            doctor.setMiddleName(request.getMiddleName());
            doctor.setSpeciality(specialityRepository.getOne(request.getSpeciality()));
            doctorRepository.save(doctor);
            return doctorMapping.mapToDoctor(doctor);
        }
        return null;
    }

    @Operation(summary = "Удаление сотрудника")
    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable UUID id) {
        if (doctorRepository.existsById(id)) {
            UUID idUser = doctorRepository.getOne(id).getUser().getId();
            doctorRepository.deleteById(id);
            userRepository.deleteById(idUser);
            return "OK";
        }
        return null;
    }

}
