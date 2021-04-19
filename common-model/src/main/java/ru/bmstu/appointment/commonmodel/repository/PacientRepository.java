package ru.bmstu.appointment.commonmodel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bmstu.appointment.commonmodel.model.Pacient;

import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.UUID;

@Repository
public interface PacientRepository extends JpaRepository<Pacient, UUID> {

    public boolean existsBySurNameAndNameAndMiddleNameAndBirthDayAndPolicy(@NotEmpty String surName,
                                                                         @NotEmpty String name,
                                                                         String middleName,
                                                                         @NotEmpty Date birthDay,
                                                                         @NotEmpty String policy);
    public Pacient findBySurNameAndNameAndMiddleNameAndBirthDayAndPolicy(@NotEmpty String surName,
                                                                           @NotEmpty String name,
                                                                           String middleName,
                                                                           @NotEmpty Date birthDay,
                                                                           @NotEmpty String policy);
}
