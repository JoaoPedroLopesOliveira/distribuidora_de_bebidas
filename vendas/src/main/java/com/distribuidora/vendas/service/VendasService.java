package com.distribuidora.vendas.service;

import com.distribuidora.vendas.model.Vendas;
import com.distribuidora.vendas.repository.VendasRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VendasService {

    private final VendasRepository vendasRepository;
    private final VendasProducer vendasProducer;

    public Vendas criar(Vendas vendas) {
        vendas.setDataVenda(LocalDateTime.now());
        vendas.setStatus("PENDENTE");
        Vendas salva = vendasRepository.save(vendas);
        vendasProducer.enviarVenda(
                String.valueOf(salva.getId()),
                salva.getIdProduto(),
                salva.getQuantidadeVendida()
        );
        return salva;
    }

    public List<Vendas> listarTodos() {
        return vendasRepository.findAll();
    }

    public Optional<Vendas> buscarPorId(Long id) {
        return vendasRepository.findById(id);
    }

    public void deletar(Long id) {
        vendasRepository.deleteById(id);
    }
}