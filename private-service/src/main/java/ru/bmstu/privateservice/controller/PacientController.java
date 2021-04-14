package ru.bmstu.privateservice.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pacient")
@Tag(name = "Pacient", description = "Работа с данными пациентов")
public class PacientController {
}
