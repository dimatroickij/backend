package ru.bmstu.appointment.commonmodel.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PacientRequest {

    @NotBlank
    @Schema(description = "Фамилия")
    private String surName;

    @NotBlank
    @Schema(description = "Имя")
    private String name;

    @Schema(description = "Отчество")
    private String middleName;

    @NotBlank
    @Schema(type = "string", pattern = "^(0[1-9]|[12][0-9]|3[01])\\.(0[1-9]|1[012]).\\d{4}$",
            description = "Дата рождения", example = "dd.MM.yyyy")
    private String birthDay;

    @NotBlank
    @Size(min = 16, max = 16)
    @Schema(description = "Номер полиса", example = "0000000000000000")
    private String policy;
}
