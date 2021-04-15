package ru.bmstu.privateservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bmstu.privateservice.model.Doctor;
import ru.bmstu.privateservice.model.Speciality;

import java.util.List;
import java.util.UUID;

public interface DoctorRepository extends JpaRepository<Doctor, UUID> {
    List<Doctor> findAllBySpeciality(Speciality speciality);
}
