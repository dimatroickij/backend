package ru.bmstu.appointment.commonmodel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bmstu.appointment.commonmodel.model.Speciality;

@Repository
public interface SpecialityRepository extends JpaRepository<Speciality, Long> {
}
