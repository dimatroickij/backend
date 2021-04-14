package ru.bmstu.privateservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.bmstu.privateservice.model.Speciality;

import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@Data
@AllArgsConstructor
public class DoctorResponse {
    @NotEmpty
    private UUID id;
    @NotEmpty
    private String surName;
    @NotEmpty
    private String name;
    private String middleName;
    @NotEmpty
    private Speciality speciality;

    public DoctorResponse() {

    }
}
