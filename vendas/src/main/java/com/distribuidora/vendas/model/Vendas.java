package com.distribuidora.vendas.model;

import com.distribuidora.vendas.model.Categoria;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "vendas")
public class Vendas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int idProduto;

    @Column(nullable = false)
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Categoria categoria;

    @Column(nullable = false)
    private BigDecimal valorUnitario;

    @Column(nullable = false)
    private int quantidadeVendida;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private LocalDateTime dataVenda;
}