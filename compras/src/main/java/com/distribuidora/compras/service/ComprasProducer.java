package com.distribuidora.compras.service;

import com.distribuidora.compras.model.ComprasEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ComprasProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public void enviarCompra(String idCompra, int idProduto, int quantidadeComprada) {
        try {
            ComprasEvent evento = new ComprasEvent();
            evento.setIdCompra(idCompra);
            evento.setIdProduto(idProduto);
            evento.setQuantidadeComprada(quantidadeComprada);

            String json = objectMapper.writeValueAsString(evento);
            kafkaTemplate.send("compras-iniciadas", json);
        } catch (Exception e) {
            System.err.println("Erro ao enviar evento de compra: " + e.getMessage());
        }
    }
}