package ru.bmstu.privateservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.bmstu.privateservice.model.Role;

import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@Data
@AllArgsConstructor
public class RegistrationResponse {
    @NotEmpty
    private UUID id;
    @NotEmpty
    private String username;
    @NotEmpty
    private String email;
    @NotEmpty
    private Role role;
}
