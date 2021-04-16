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
public class Schedule {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @ManyToOne
    @NotNull
    private Doctor doctor = new Doctor();

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    @NotNull
    private Date date = new Date();

    @Column(nullable = false)
    @NotNull
    @Temporal(TemporalType.TIME)
    private Date startTime = new Date();

    @Column(nullable = false)
    @NotNull
    @Temporal(TemporalType.DATE)
    private Date endTime = new Date();

}
