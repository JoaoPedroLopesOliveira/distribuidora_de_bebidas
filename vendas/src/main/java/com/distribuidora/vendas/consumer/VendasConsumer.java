package com.distribuidora.vendas.consumer;

import com.distribuidora.vendas.repository.VendasRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VendasConsumer {

    private final VendasRepository vendasRepository;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "vendas-processadas", groupId = "vendas-group")
    public void processarResposta(String mensagemJson) {
        try {
            var node = objectMapper.readTree(mensagemJson);
            String idVenda = node.get("idVenda").asText();
            String status = node.get("status").asText();

            vendasRepository.findById(Long.parseLong(idVenda)).ifPresent(venda -> {
                venda.setStatus(status);
                vendasRepository.save(venda);
            });

        } catch (Exception e) {
            System.err.println("Erro ao processar resposta da venda: " + e.getMessage());
        }
    }
}