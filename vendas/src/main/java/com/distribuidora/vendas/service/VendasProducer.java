package com.distribuidora.vendas.service;

import com.distribuidora.vendas.model.VendasEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VendasProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public void enviarVenda(String idVenda, int idProduto, int quantidadeVendida) {
        try {
            VendasEvent evento = new VendasEvent();
            evento.setIdVenda(idVenda);
            evento.setIdProduto(idProduto);
            evento.setQuantidadeVendida(quantidadeVendida);

            String json = objectMapper.writeValueAsString(evento);
            kafkaTemplate.send("vendas-iniciadas", json);
        } catch (Exception e) {
            throw new RuntimeException("Falha ao enviar evento de venda para o Kafka", e);
        }
    }
}