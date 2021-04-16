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
    @Schema(type = "string", format = "date", description = "Дата приёма", example = "YYYY-MM-DD")
    private Date date;

    @NotBlank
    @Schema(type = "string", format = "time", description = "Время начала приёма", example = "")
    private Date startTime;

    @NotBlank
    @Schema(type = "string", format = "time", description = "Время окончания приёма", example = "")
    private Date endTime;
}
