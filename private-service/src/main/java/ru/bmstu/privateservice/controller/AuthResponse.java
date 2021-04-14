package ru.bmstu.privateservice.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.bmstu.privateservice.model.Role;

@Data
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private Role role;
}
