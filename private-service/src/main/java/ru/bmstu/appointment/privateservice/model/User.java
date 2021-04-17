package ru.bmstu.appointment.privateservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
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
    private Role role = new Role();
}
