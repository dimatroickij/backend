package ru.bmstu.appointment.publicservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bmstu.appointment.publicservice.model.Appointment;

import java.util.UUID;

public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {
}
