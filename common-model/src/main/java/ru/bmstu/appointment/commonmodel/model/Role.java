package ru.bmstu.appointment.commonmodel.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    @NotBlank
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID")
    private Long id;

    @Column(length = 20, nullable = false, unique = true)
    @NotBlank
    @Schema(description = "Название роли")
    private String name;
}
