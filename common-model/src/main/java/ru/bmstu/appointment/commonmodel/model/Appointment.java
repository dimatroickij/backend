package ru.bmstu.appointment.commonmodel.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "appointment", schema = "public")
public class Appointment {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @ManyToOne
    @NotNull
    private Pacient pacient = new Pacient();

    @ManyToOne
    @NotNull
    private Schedule schedule = new Schedule();

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    private Date dateRecord = new Date();

//    @Column(unique = true, nullable = false)
//    @NotNull
//    @Email(regexp=".*@.*\\..*", message = "Почта должна быть действующей")
//    private String email;

    @Column(nullable = false)
    private Boolean isActive = true;
}
