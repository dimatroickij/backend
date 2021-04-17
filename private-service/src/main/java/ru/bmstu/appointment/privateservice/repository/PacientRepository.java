package ru.bmstu.appointment.privateservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bmstu.appointment.privateservice.model.Pacient;

import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.UUID;

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
