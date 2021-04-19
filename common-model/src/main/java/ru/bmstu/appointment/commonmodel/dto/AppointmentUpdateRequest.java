package ru.bmstu.appointment.commonmodel.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentUpdateRequest {

    @NotBlank
    @Schema(description = "ID расписания")
    private UUID schedule;

    @NotBlank
    @Email
    @Schema(format = "email", description = "Email", example = "email@email.email")
    private String email;
}
