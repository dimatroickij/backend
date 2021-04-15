package ru.bmstu.privateservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class AuthRequest {

    @NotBlank
    @Schema(description = "Логин")
    private String username;

    @NotBlank
    @Schema(description = "Пароль")
    private String password;
}
