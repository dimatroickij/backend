package ru.bmstu.appointment.commonmodel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bmstu.appointment.commonmodel.model.User;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID>{
    User findByUsername(String username);
}
