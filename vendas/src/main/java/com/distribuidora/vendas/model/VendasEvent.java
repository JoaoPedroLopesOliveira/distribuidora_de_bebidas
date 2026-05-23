package com.distribuidora.vendas.model;

import lombok.Data;

@Data
public class VendasEvent {
    private String idVenda;
    private int idProduto;
    private int quantidadeVendida;
}