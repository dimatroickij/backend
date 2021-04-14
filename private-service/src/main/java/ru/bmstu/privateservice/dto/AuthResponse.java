package ru.bmstu.privateservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.bmstu.privateservice.model.Role;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
public class AuthResponse {
    @NotEmpty
    private String token;
    @NotEmpty
    private Role role;
}
