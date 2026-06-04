package com.distribuidora.estoque.kafka;

import com.distribuidora.estoque.dto.CompraEventDTO;
import com.distribuidora.estoque.dto.VendaEventDTO;
import com.distribuidora.estoque.service.EstoqueService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;


@Component
@RequiredArgsConstructor
public class EstoqueKafkaListener {

    private final EstoqueService estoqueService;
    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @KafkaListener(topics = "vendas-iniciadas", groupId = "estoque-group")
    public void processarVenda(String mensagemJson) {
        String idVenda = obterCampo(mensagemJson, "idVenda");
        try {
            VendaEventDTO evento = objectMapper.readValue(mensagemJson, VendaEventDTO.class);

            estoqueService.subtrairQuantidade(evento.getIdProduto(), evento.getQuantidadeVendida());

            String sucesso = String.format("{\"idVenda\": \"%s\", \"status\": \"APROVADO\"}", evento.getIdVenda());
            kafkaTemplate.send("vendas-processadas", sucesso);

        } catch (Exception e) {
            String erro = String.format("{\"idVenda\": \"%s\", \"status\": \"REJEITADO\", \"motivo\": \"%s\"}",
                    idVenda, e.getMessage());
            kafkaTemplate.send("vendas-processadas", erro);
            System.err.println("Erro ao processar venda " + idVenda + ": " + e.getMessage());
        }
    }

    @KafkaListener(topics = "compras-iniciadas", groupId = "estoque-group")
    public void processarCompra(String mensagemJson) {
        String idCompra = obterCampo(mensagemJson, "idCompra");
        try {
            CompraEventDTO evento = objectMapper.readValue(mensagemJson, CompraEventDTO.class);

            estoqueService.adicionarQuantidade(evento.getIdProduto(), evento.getQuantidadeComprada());

            String sucesso = String.format("{\"idCompra\": \"%s\", \"status\": \"CONCLUIDA\"}", evento.getIdCompra());
            kafkaTemplate.send("compras-processadas", sucesso);

        } catch (Exception e) {
            String erro = String.format("{\"idCompra\": \"%s\", \"status\": \"FALHA\", \"motivo\": \"%s\"}",
                    idCompra, e.getMessage());
            kafkaTemplate.send("compras-processadas", erro);
            System.err.println("Erro ao processar compra " + idCompra + ": " + e.getMessage());
        }
    }

    private String obterCampo(String json, String campo) {
        try { return objectMapper.readTree(json).get(campo).asText(); }
        catch (Exception e) { return "DESCONHECIDO"; }
    }
}
