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

    public Long getId() {
        return id;
    }

    public String getUser() {
        return user;
    }

    public String getSenha() {
        return senha;
    }

    public Role getRole() {
        return role;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}