package com.distribuidora.compras.service;


import com.distribuidora.compras.model.ComprasEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ComprasProducer {

    private final KafkaTemplate<String, ComprasEvent> kafkaTemplate;

    public ComprasProducer(KafkaTemplate<String, ComprasEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void enviarCompra(Long produtoId, Integer quantidade) {
        ComprasEvent evento = new ComprasEvent(produtoId, quantidade);
        kafkaTemplate.send("compra-realizada", evento);
    }
}
