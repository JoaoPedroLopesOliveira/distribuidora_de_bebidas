package com.distribuidora.usuarios.model;


import jakarta.persistence.*;
import com.distribuidora.usuarios.role.Role;
import lombok.Data;

@Entity
@Table(name = "usuarios")
@Data


public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String user;

    @Column(nullable = false)
    private String senha;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;
}