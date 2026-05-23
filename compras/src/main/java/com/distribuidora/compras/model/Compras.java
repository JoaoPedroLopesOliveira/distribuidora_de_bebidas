package com.distribuidora.compras.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "compras")

public class Compras {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (nullable = false)
    private String descricaoProduto;

    @Enumerated(EnumType.STRING)
    @Column (nullable = false)
    private Categoria categoria;

    @Column(nullable = false)
    private Double precoCompra;

    @Column(nullable = false)
    private Integer quantidadeComprada;

    @Column(nullable = false)
    private LocalDateTime DataCompra;
}
