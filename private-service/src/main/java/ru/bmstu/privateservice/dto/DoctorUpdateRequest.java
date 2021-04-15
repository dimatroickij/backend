package ru.bmstu.privateservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
public class DoctorUpdateRequest {
    @NotEmpty
    private String surName;
    @NotEmpty
    private String name;
    private String middleName;
    @NotEmpty
    private Long speciality;
}
