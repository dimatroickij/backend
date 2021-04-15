package ru.bmstu.privateservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class Pacient {

    @Id
    @GeneratedValue(generator = "uuid2", strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(length = 20, nullable = false)
    @NotEmpty
    private String surName;

    @Column(length = 20, nullable = false)
    @NotEmpty
    private String name;

    @Column(length = 20)
    private String middleName;

    @Column(nullable = false)
    @NotEmpty
    @Temporal(TemporalType.DATE)
    private Date birthDay;

    @Column(length = 16, unique = true, nullable = false)
    @NotEmpty
    private String policy;

    @ManyToOne
    private User user;
}
