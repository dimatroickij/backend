package ru.bmstu.privateservice.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

@Entity
public class Appointment {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID codeAppointment;

    @ManyToOne
    private final Pacient pacient = new Pacient();

    @ManyToOne
    private Schedule schedule = new Schedule();

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    private Date dateRecord = new Date();

    @Column
    private Boolean isActive;

    public Appointment() {
        isActive = Boolean.TRUE;
    }

    public Appointment(UUID id, UUID codeAppointment, Schedule schedule, Date dateRecord, boolean isActive) {
        this.id = id;
        this.codeAppointment = codeAppointment;
        this.schedule = schedule;
        this.isActive = isActive;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getCodeAppointment() {
        return codeAppointment;
    }

    public void setCodeAppointment(UUID codeAppointment) {
        this.codeAppointment = codeAppointment;
    }

    public Pacient getPacient() {
        return pacient;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public Date getDateRecord() {
        return dateRecord;
    }

    public void setDateRecord(Date dateRecord) {
        this.dateRecord = dateRecord;
    }

    public Boolean isActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}
