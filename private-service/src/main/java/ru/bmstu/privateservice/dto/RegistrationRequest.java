package ru.bmstu.privateservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class RegistrationRequest {

    @NotBlank
    @Schema(description = "Логин")
    private String username;

    @NotBlank
    @Email
    @Schema(format = "email", description = "Email", example = "email@email.email")
    private String email;

    @NotBlank
    @Schema(format = "password", description = "Пароль")
    private String password;
}
