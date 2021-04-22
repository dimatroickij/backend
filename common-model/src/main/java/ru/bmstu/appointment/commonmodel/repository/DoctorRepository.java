package ru.bmstu.appointment.commonmodel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bmstu.appointment.commonmodel.model.Doctor;
import ru.bmstu.appointment.commonmodel.model.Speciality;
import ru.bmstu.appointment.commonmodel.model.User;

import java.util.List;
import java.util.UUID;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, UUID> {
    List<Doctor> findAllBySpeciality(Speciality speciality);
    Doctor getDoctorByUser(User user);
}
