package ru.bmstu.privateservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.bmstu.privateservice.model.User;
import ru.bmstu.privateservice.service.UserService;

@Component
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username);
        if (user == null)
            throw new UsernameNotFoundException("User name not found.");
        return CustomUserDetails.fromUserEntityToCustomUserDetails(user);
    }
}
