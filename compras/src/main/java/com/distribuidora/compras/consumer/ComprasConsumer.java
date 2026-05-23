package com.distribuidora.compras.consumer;

import com.distribuidora.compras.repository.ComprasRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ComprasConsumer {

    private final ComprasRepository comprasRepository;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "compras-processadas", groupId = "compras-group")
    public void processarResposta(String mensagemJson) {
        try {
            var node = objectMapper.readTree(mensagemJson);
            String idCompra = node.get("idCompra").asText();
            String status = node.get("status").asText();

            comprasRepository.findById(Long.parseLong(idCompra)).ifPresent(compra -> {
                compra.setStatus(status);
                comprasRepository.save(compra);
            });

        } catch (Exception e) {
            System.err.println("Erro ao processar resposta da compra: " + e.getMessage());
        }
    }
}