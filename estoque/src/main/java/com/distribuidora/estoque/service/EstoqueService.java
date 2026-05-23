package com.distribuidora.estoque.service;

import com.distribuidora.estoque.model.ItemEstoque;
import com.distribuidora.estoque.repository.EstoqueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EstoqueService {

    private final EstoqueRepository repository;

    @Transactional
    public void adicionarQuantidade(int idProduto, int quantidadeComprada) {
        ItemEstoque item = repository.findById(idProduto)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado no estoque: " + idProduto));

        item.setQuantidade(item.getQuantidade() + quantidadeComprada);
        repository.save(item);
    }

    @Transactional
    public void subtrairQuantidade(int idProduto, int quantidadeVendida) {
        ItemEstoque item = repository.findById(idProduto)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado no estoque: " + idProduto));

        if (item.getQuantidade() < quantidadeVendida) {
            throw new IllegalStateException("Estoque insuficiente para o produto: " + idProduto);
        }

        item.setQuantidade(item.getQuantidade() - quantidadeVendida);
        repository.save(item);
    }
}