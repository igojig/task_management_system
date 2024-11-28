package ru.igojig.taskmanagementsystem.entities;

import jakarta.persistence.*;
import lombok.Data;
import ru.igojig.taskmanagementsystem.enums.Roles;

@Data
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Roles role;
}
