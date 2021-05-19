package ru.bmstu.appointment.privateservice.controller;

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
import ru.bmstu.appointment.commonmodel.utils.DoctorMapping;
import ru.bmstu.appointment.privateservice.config.jwt.JwtProvider;
import ru.bmstu.appointment.commonmodel.dto.AuthRequest;
import ru.bmstu.appointment.commonmodel.dto.AuthResponse;
import ru.bmstu.appointment.commonmodel.dto.RegistrationRequest;
import ru.bmstu.appointment.commonmodel.dto.RegistrationResponse;
import ru.bmstu.appointment.commonmodel.model.User;
import ru.bmstu.appointment.commonmodel.service.UserService;

import javax.validation.Valid;

@RestController
@Tag(name = "Authorization", description = "Авторизация / регистрация в системе")
public class AuthController {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private DoctorMapping doctorMapping;

    @SecurityRequirements
    @Operation(summary = "Регистрация в системе",
            description = "Данный метод используется пользователями для регистрации в системе. " +
                    "Сотрудников добавляет администратор")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Авторизация успешно выполнена",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuthResponse.class))}),
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
        if (user.getRole().getName().equals("ROLE_DOCTOR"))
            return new AuthResponse(token, user.getUsername(), user.getRole(),
                doctorMapping.mapToDoctor(user.getDoctors().get(0)));
        return new AuthResponse(token, user.getUsername(), user.getRole(), null);
    }
}
