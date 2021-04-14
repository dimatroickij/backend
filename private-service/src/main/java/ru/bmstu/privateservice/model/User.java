package ru.bmstu.privateservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username")
        })
@Data
@NoArgsConstructor
public class User{

    @Id
    @NotEmpty
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column
    @NotEmpty
    private String username;

    @Column
    @NotEmpty
    @Email(regexp=".*@.*\\..*", message = "Почта должна быть действующей")
    private String email;

    @Column
    @NotEmpty
    private String password;

    @ManyToOne
    @NotEmpty
    private Role role = new Role();
}
