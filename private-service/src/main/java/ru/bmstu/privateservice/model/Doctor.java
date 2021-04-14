package ru.bmstu.privateservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class Doctor {
    @Id
    @NotEmpty
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Size(min = 3, max = 20)
    @Column(length = 20)
    @NotEmpty
    private String surName;

    @Size(min = 3, max = 20)
    @Column(length = 20)
    @NotEmpty
    private String name;

    @Size(min = 3, max = 20)
    @Column(length = 20)
    private String middleName;

    @ManyToOne
    @NotEmpty
    private Speciality speciality = new Speciality();

    @ManyToOne
    @NotEmpty
    private User user = new User();
}
