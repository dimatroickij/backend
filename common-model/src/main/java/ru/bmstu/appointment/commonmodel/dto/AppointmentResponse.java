package ru.bmstu.appointment.commonmodel.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.bmstu.appointment.commonmodel.model.Pacient;
import ru.bmstu.appointment.commonmodel.model.Schedule;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentResponse {

    @NotBlank
    @Schema(description = "ID")
    private UUID id;

    @NotBlank
    @Schema(description = "Пациент")
    private Pacient pacient;

    @NotBlank
    @Schema(description = "Расписание")
    private Schedule schedule;

    @NotBlank
    @Schema(type = "string", pattern = "^(0[1-9]|[12][0-9]|3[01])\\.(0[1-9]|1[012]).\\d{4} ([0-1]\\d|[2][0-3]|3[01]):([0-5]\\d):([0-5]\\d)$",
            description = "Дата создания записи", example = "dd.MM.yyyy HH:mm:ss")
    private Date dateRecord;
}
