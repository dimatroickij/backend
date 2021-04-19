package ru.bmstu.appointment.commonmodel.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.bmstu.appointment.commonmodel.model.Role;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationResponse {

    @NotBlank
    @Schema(description = "ID")
    private UUID id;

    @NotBlank
    @Schema(description = "Логин")
    private String username;

    @NotBlank
    @Email
    @Schema(format = "email", description = "Email", example = "email@email.email")
    private String email;

    @NotBlank
    @Schema(description = "Роль пользователя")
    private Role role;
}
