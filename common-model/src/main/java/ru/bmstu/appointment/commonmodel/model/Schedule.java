package ru.bmstu.appointment.commonmodel.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@Table(name = "schedule", schema = "public")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
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
    @Temporal(TemporalType.TIME)
    private Date endTime = new Date();

    @Column(nullable = false)
    @ColumnDefault("true")
    private Boolean isActive;

    @OneToMany(mappedBy = "schedule", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Appointment> appointments;
}
