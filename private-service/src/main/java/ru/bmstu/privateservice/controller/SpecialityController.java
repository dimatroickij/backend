package ru.bmstu.privateservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import ru.bmstu.privateservice.model.Speciality;
import ru.bmstu.privateservice.repository.SpecialityRepository;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/speciality")
@Tag(name = "Speciality", description = "Работа со специальностями работников")
public class SpecialityController {
    @Autowired
    private SpecialityRepository specialityRepository;

    @SecurityRequirements
    @Operation(summary = "Список специальностей")
    @GetMapping("/all")
    public List<Speciality> all() {
        return specialityRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    @SecurityRequirements
    @Operation(summary = "Название специальности по ID")
    @GetMapping("/{id}")
    public String get(@PathVariable Long id) {
        if (specialityRepository.existsById(id)) {
            return specialityRepository.getOne(id).getName();
        }
        return null;
    }

    @Operation(summary = "Добавление специальности", description = "Доступно для пользователей с ролью ADMIN")
    @PostMapping("/add")
    public Speciality add(@RequestBody @Valid String request) {
        Speciality speciality = new Speciality();
        speciality.setName(request);
        specialityRepository.save(speciality);
        return speciality;
    }

    @Operation(summary = "Изменение названия специальности", description = "Доступно для пользователей с ролью ADMIN")
    @PutMapping("/update/{id}")
    public String update(@RequestBody String request, @PathVariable Long id) {
        if (specialityRepository.existsById(id)) {
            Speciality speciality = new Speciality();
            speciality.setName(request);
            specialityRepository.save(speciality);
            return speciality.getName();
        }
        return null;
    }

    @Operation(summary = "Удаление специальности", description = "Доступно для пользователей с ролью ADMIN")
    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        if (specialityRepository.existsById(id)) {
            specialityRepository.deleteById(id);
            return "OK";
        }
        return null;
    }

}
