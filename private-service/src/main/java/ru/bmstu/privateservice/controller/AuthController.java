package ru.bmstu.privateservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.bmstu.privateservice.config.jwt.JwtProvider;
import ru.bmstu.privateservice.model.User;
import ru.bmstu.privateservice.service.UserService;

import javax.validation.Valid;

@RestController
public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtProvider jwtProvider;

//    @PostMapping("/register")
//    public String registerUser(@RequestBody @Valid RegistrationRequest registrationRequest) {
//        User u = new User();
//        u.setPassword(registrationRequest.getPassword());
//        u.setUsername(registrationRequest.getUsername());
//        userService.saveUser(u);
//        return "OK";
//    }

    @PostMapping("/auth")
    public AuthResponse auth(@RequestBody AuthRequest request) {
        User user = userService.findByUsernameAndPassword(request.getUsername(), request.getPassword());
        String token = jwtProvider.generateToken(user.getUsername());
        return new AuthResponse(token);
    }
}
