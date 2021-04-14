package ru.bmstu.privateservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bmstu.privateservice.model.Speciality;
import ru.bmstu.privateservice.repository.SpecialityRepository;

import java.util.List;

@RestController
@RequestMapping("/speciality")
public class SpecialityController {
    @Autowired
    private SpecialityRepository specialityRepository;

    @Operation(summary = "Список специальностей")
    @GetMapping("/all")
    public List<Speciality> allSpeciality(){
        return specialityRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }
}
