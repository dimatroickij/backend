package ru.bmstu.privateservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.bmstu.privateservice.model.Speciality;
import ru.bmstu.privateservice.model.User;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
public class DoctorRequest {
    @NotEmpty
    private String surName;
    @NotEmpty
    private String name;
    private String middleName;
    @NotEmpty
    private Long speciality;
    @NotEmpty
    private String username;
    @NotEmpty
    @Email
    private String email;
    @NotEmpty
    private String password;
}
