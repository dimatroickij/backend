package ru.bmstu.privateservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@AllArgsConstructor
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
    @Schema(type = "string", format = "date", description = "Дата рождения", example = "YYYY-MM-DD")
    private Date birthDay;

    @NotBlank
    @Size(min = 16, max = 16)
    @Schema(description = "Номер полиса", example = "0000000000000000")
    private String policy;
}
