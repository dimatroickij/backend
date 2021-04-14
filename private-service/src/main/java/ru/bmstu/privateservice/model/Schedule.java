package ru.bmstu.privateservice.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Time;
import java.util.Date;
import java.util.UUID;

@Entity
public class Schedule {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @ManyToOne
    private Doctor doctor = new Doctor();

    @Column
    @Temporal(TemporalType.DATE)
    @NotNull
    private Date date = new Date();

    @Column
    @NotNull
    @Temporal(TemporalType.TIME)
    private Date startTime = new Date();

    @Column
    @NotNull
    @Temporal(TemporalType.DATE)
    private Date endTime = new Date();

    @Column
    private String cabinet;

    public Schedule(UUID id, Doctor doctor, Date date, Date startTime, Date endTime, String cabinet) {
        this.id = id;
        this.doctor = doctor;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.cabinet = cabinet;
    }

    public Schedule() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getCabinet() {
        return cabinet;
    }

    public void setCabinet(String cabinet) {
        this.cabinet = cabinet;
    }
}
