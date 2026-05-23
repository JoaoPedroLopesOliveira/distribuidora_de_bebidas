package com.distribuidora.compras.model;

import com.distribuidora.compras.model.Categoria;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "compras")
public class Compras {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private int idProduto;

    @Column(nullable = false)
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Categoria categoria;

    @Column(nullable = false)
    private BigDecimal precoCusto;

    @Column(nullable = false)
    private int quantidadeComprada;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private LocalDateTime dataCompra;
}