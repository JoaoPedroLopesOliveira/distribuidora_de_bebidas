package com.distribuidora.compras.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ComprasConsumer {

    @KafkaListener(topics = "compra-realizada", groupId = "grupo-compras")
    public void consumir(String mensagem) {
        System.out.println("Compra recebida: " + mensagem);
    }
}
