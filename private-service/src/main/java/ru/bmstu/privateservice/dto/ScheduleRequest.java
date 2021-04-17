package ru.bmstu.privateservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleRequest {

    @NotBlank
    @Schema(description = "ID врача")
    private UUID doctor;

    @NotBlank
    @Schema(type = "string", pattern = "^(0[1-9]|[12][0-9]|3[01])\\.(0[1-9]|1[012]).\\d{4}$",
            description = "Дата приёма", example = "dd.MM.yyyy")
    private String date;

    @NotBlank
    @Schema(type = "string", pattern = "^([0-1]\\d|[2][0-3]|3[01]):([0-5]\\d)$", description = "Время начала приёма",
            example = "HH:mm")
    private String startTime;

    @NotBlank
    @Schema(type = "string", pattern = "^([0-1]\\d|[2][0-3]|3[01]):([0-5]\\d)$", description = "Время окончания приёма",
            example = "HH:mm")
    private String endTime;
}
