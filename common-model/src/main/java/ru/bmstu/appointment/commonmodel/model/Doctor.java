package ru.bmstu.appointment.commonmodel.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "doctor", schema = "public")
public class Doctor {

    @Id
    @NotEmpty
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Size(min = 3, max = 20)
    @Column(length = 20, nullable = false)
    @NotEmpty
    private String surName;

    @Size(min = 3, max = 20)
    @Column(length = 20, nullable = false)
    @NotEmpty
    private String name;

    @Size(min = 3, max = 20)
    @Column(length = 20)
    private String middleName;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotEmpty
    private Speciality speciality = new Speciality();

    @Column(nullable = false)
    @NotEmpty
    private String cabinet;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @NotNull
    private User user = new User();

    @Column(nullable = false)
    @ColumnDefault("true")
    private Boolean isActive;
}
