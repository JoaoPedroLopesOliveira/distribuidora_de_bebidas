package com.distribuidora.estoque.dto;

import lombok.Data;

@Data
public class CompraEventDTO {
    private String idCompra;
    private int idProduto;
    private int quantidadeComprada;
}