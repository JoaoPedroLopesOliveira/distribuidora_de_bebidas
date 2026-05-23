package com.distribuidora.compras.model;

import lombok.Data;

@Data
public class ComprasEvent {
    private String idCompra;
    private int idProduto;
    private int quantidadeComprada;
}