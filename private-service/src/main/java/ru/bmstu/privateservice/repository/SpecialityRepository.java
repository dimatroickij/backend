package ru.bmstu.privateservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bmstu.privateservice.model.Speciality;

@Repository
public interface SpecialityRepository extends JpaRepository<Speciality, Long> {
}
