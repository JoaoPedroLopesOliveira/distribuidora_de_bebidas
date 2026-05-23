package com.distribuidora.estoque.model;

import com.distribuidora.estoque.enuns.Categoria;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "estoque")
@Data
public class ItemEstoque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    @Column( nullable = false )
    private Categoria categoria;

    @Column( nullable = false )
    private String descricao;

    @Column(nullable = false)
    private int quantidade;
}
