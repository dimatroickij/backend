package ru.bmstu.privateservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class Pacient {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Size(min = 3, max = 20)
    @Column(length = 20)
    @NotNull
    private String surName;

    @Size(min = 3, max = 20)
    @Column(length = 20)
    @NotNull
    private String name;

    @Column(length = 20)
    @Size(min = 3, max = 20)
    private String middleName;

    @Column
    @NotNull
    private Date birthDay;

    @Column(length = 16)
    @NotNull
    private String policy;

    @ManyToOne()
    private User user;
}
