package ru.bmstu.privateservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
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
}
