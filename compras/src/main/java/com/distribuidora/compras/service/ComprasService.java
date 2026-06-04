package com.distribuidora.compras.service;

import com.distribuidora.compras.model.Compras;
import com.distribuidora.compras.repository.ComprasRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ComprasService {

    private final ComprasRepository comprasRepository;
    private final ComprasProducer comprasProducer;

    public Compras criar(Compras compras) {
        compras.setDataCompra(LocalDateTime.now());
        compras.setStatus("PENDENTE");

        if (compras.getQuantidadeComprada() <= 0) {
            throw new IllegalArgumentException("A quantidade comprada deve ser maior que zero.");
        }

        Compras salva = comprasRepository.save(compras);
        comprasProducer.enviarCompra(
                String.valueOf(salva.getId()),
                salva.getIdProduto(),
                salva.getQuantidadeComprada()
        );
        return salva;
    }

    public List<Compras> listarTodos() {
        return comprasRepository.findAll();
    }

    public Optional<Compras> buscarPorId(Long id) {
        return comprasRepository.findById(id);
    }

    public void deletar(Long id) {
        comprasRepository.deleteById(id);
    }
}