package ru.bmstu.privateservice.model;

import org.hibernate.annotations.GenericGenerator;
import ru.bmstu.privateservice.model.Speciality;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@Entity
public class Doctor {
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

    @Size(min = 3, max = 20)
    @Column(length = 20)
    private String middleName;

    @ManyToOne()
    private Speciality speciality = new Speciality();

    @ManyToOne()
    private User user = new User();

    public Doctor() {
    }

    public Doctor(UUID id, String surName, String name, String middleName, Speciality speciality, User user) {
        this.id = id;
        this.surName = surName;
        this.name = name;
        this.middleName = middleName;
        this.speciality = speciality;
        this.user = user;
    }

    public Doctor(UUID id, String surName, String name, Speciality speciality, User user) {
        this.id = id;
        this.surName = surName;
        this.name = name;
        this.speciality = speciality;
        this.user = user;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public Speciality getSpeciality() {
        return speciality;
    }

    public void setSpeciality(Speciality speciality) {
        this.speciality = speciality;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
