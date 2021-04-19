package ru.bmstu.appointment.commonmodel.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {

    @NotBlank
    @Schema(description = "Логин")
    private String username;

    @NotBlank
    @Schema(format = "password", description = "Пароль")
    private String password;
}
