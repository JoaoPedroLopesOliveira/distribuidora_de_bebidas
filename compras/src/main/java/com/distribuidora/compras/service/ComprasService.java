package com.distribuidora.compras.service;

import com.distribuidora.compras.model.Compras;
import com.distribuidora.compras.repository.ComprasRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class ComprasService {

    private final ComprasRepository comprasRepository;

    private final ComprasProducer comprasProducer;

    public ComprasService(ComprasRepository comprasRepository, ComprasProducer comprasProducer) {
        this.comprasRepository = comprasRepository;
        this.comprasProducer = comprasProducer;
    }

    public Compras criar(Compras compras) {
        compras.setDataCompra(LocalDateTime.now());
        Compras salva = comprasRepository.save(compras);
        comprasProducer.enviarCompra(salva.getId(), salva.getQuantidadeComprada());
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
