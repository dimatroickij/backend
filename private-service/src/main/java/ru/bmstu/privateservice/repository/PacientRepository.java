package ru.bmstu.privateservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bmstu.privateservice.model.Pacient;

import java.util.UUID;

public interface PacientRepository extends JpaRepository<Pacient, UUID> {
}
