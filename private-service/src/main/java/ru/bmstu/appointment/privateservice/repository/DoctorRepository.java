package ru.bmstu.appointment.privateservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bmstu.appointment.privateservice.model.Doctor;
import ru.bmstu.appointment.privateservice.model.Speciality;

import java.util.List;
import java.util.UUID;

public interface DoctorRepository extends JpaRepository<Doctor, UUID> {
    List<Doctor> findAllBySpeciality(Speciality speciality);
}
