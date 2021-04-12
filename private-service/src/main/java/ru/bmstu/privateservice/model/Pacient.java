package ru.bmstu.privateservice.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.UUID;

@Entity
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

    public Pacient(UUID id, String surName, String name, String middleName, Date birthDay, String policy, User user) {
        this.id = id;
        this.surName = surName;
        this.name = name;
        this.middleName = middleName;
        this.birthDay = birthDay;
        this.policy = policy;
        this.user = user;
    }

    public Pacient(UUID id, String surName, String name, Date birthDay, String policy) {
        this.id = id;
        this.surName = surName;
        this.name = name;
        this.birthDay = birthDay;
        this.policy = policy;
    }

    public Pacient(UUID id, String surName, String name, Date birthDay, String policy, User user) {
        this.id = id;
        this.surName = surName;
        this.name = name;
        this.birthDay = birthDay;
        this.policy = policy;
        this.user = user;
    }

    public Pacient() {
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

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public String getPolicy() {
        return policy;
    }

    public void setPolicy(String policy) {
        this.policy = policy;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
