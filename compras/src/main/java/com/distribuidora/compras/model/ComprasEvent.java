package com.distribuidora.compras.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComprasEvent {
    private Long produtoId;
    private Integer quantidade;
}