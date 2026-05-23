package com.distribuidora.estoque.dto;

import lombok.Data;

@Data
public class VendaEventDTO {
    private String idVenda;
    private int idProduto;
    private int quantidadeVendida;
}
