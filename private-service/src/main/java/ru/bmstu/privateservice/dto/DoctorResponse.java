package ru.bmstu.privateservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.bmstu.privateservice.model.Speciality;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorResponse {

    @NotBlank
    @Schema(description = "ID")
    private UUID id;

    @NotBlank
    @Schema(description = "Фамилия")
    private String surName;

    @NotBlank
    @Schema(description = "Имя")
    private String name;

    @Schema(description = "Отчество")
    private String middleName;

    @NotBlank
    @Schema(description = "Специальность работника")
    private Speciality speciality;

    @NotBlank
    @Schema(description = "Номер кабинета")
    private String cabinet;
}
