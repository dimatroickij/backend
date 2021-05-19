package ru.bmstu.appointment.commonmodel.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users", schema = "public")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User{

    @Id
    @NotEmpty
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(unique = true, nullable = false)
    @NotNull
    private String username;

    @Column(unique = true, nullable = false)
    @NotNull
    @Email(regexp=".*@.*\\..*", message = "Почта должна быть действующей")
    private String email;

    @Column(nullable = false)
    @NotNull
    private String password;

    @ManyToOne
    @NotNull
    private Role role = new Role();

    @Column(nullable = false)
    @ColumnDefault("true")
    private Boolean isActive;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Doctor> doctors;
}
