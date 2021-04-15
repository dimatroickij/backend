package ru.bmstu.privateservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.bmstu.privateservice.model.Role;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationResponse {

    @NotBlank

    private UUID id;

    @NotBlank
    @Schema(description = "Логин")
    private String username;

    @NotBlank
    @Email
    @Schema(type = "email", description = "Email", example = "email@email.email")
    private String email;

    @NotBlank
    @Schema(description = "Роль пользователя")
    private Role role;
}
