package ru.bmstu.appointment.commonmodel.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.bmstu.appointment.commonmodel.model.Role;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {

    @NotBlank
    @Schema(description = "JWT токен")
    private String token;

    @NotBlank
    @Schema(description = "Логин пользователя")
    private String username;

    @NotBlank
    @Schema(description = "Роль пользователя")
    private Role role;
}
