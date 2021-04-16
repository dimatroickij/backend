package ru.bmstu.privateservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.bmstu.privateservice.dto.AuthRequest;
import ru.bmstu.privateservice.dto.AuthResponse;
import ru.bmstu.privateservice.dto.RegistrationRequest;
import ru.bmstu.privateservice.dto.RegistrationResponse;
import ru.bmstu.privateservice.model.User;
import ru.bmstu.privateservice.config.jwt.JwtProvider;
import ru.bmstu.privateservice.service.UserService;

import javax.validation.Valid;

@RestController
@Tag(name = "Authorization", description = "Авторизация / регистрация в системе")
public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtProvider jwtProvider;

    @SecurityRequirements
    @Operation(summary = "Регистрация в системе",
            description = "Данный метод используется пользователями для регистрации в системе. " +
                    "Сотрудников добавляет администратор")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Регистрация прошла успешно"),
            @ApiResponse(responseCode = "401", description = "У вас нет прав на регистрацию"),
            @ApiResponse(responseCode = "409", description = "Ошибка при сохранении данных в БД"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    @PostMapping("/register")
    public RegistrationResponse registerUser(@RequestBody @Valid RegistrationRequest registrationRequest) {
        User u = new User();
        u.setPassword(registrationRequest.getPassword());
        u.setUsername(registrationRequest.getUsername());
        u.setEmail(registrationRequest.getEmail());
        userService.saveUser(u, "ROLE_USER");
        return new RegistrationResponse(u.getId(), u.getUsername(), u.getEmail(), u.getRole());
    }

    @SecurityRequirements
    @Operation(summary = "Авторизация")
    @PostMapping("/auth")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Авторизация успешно выполнена",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = AuthResponse.class))})})
    public AuthResponse auth(@RequestBody AuthRequest request) {
        User user = userService.findByUsernameAndPassword(request.getUsername(), request.getPassword());
        String token = jwtProvider.generateToken(user.getUsername());
        return new AuthResponse(token, user.getRole());
    }
}
