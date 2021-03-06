package ru.bmstu.appointment.commonmodel.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.bmstu.appointment.commonmodel.model.Role;
import ru.bmstu.appointment.commonmodel.model.User;
import ru.bmstu.appointment.commonmodel.repository.RoleRepository;
import ru.bmstu.appointment.commonmodel.repository.UserRepository;

@Service
@AllArgsConstructor
public class UserService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final RoleRepository roleRepository;

//    @Autowired
    private final PasswordEncoder passwordEncoder;

    public void saveUser(User user, String role) {
        Role userRole = roleRepository.findByName(role);
        user.setRole(userRole);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User findByUsernameAndPassword(String username, String password) {
        User user = findByUsername(username);
        if (user != null) {
            if (passwordEncoder.matches(password, user.getPassword())) {
                return user;
            }
        }
        return null;
    }

}
