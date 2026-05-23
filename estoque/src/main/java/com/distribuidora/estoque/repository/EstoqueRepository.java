package com.distribuidora.estoque.repository;

import com.distribuidora.estoque.model.ItemEstoque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EstoqueRepository extends JpaRepository<ItemEstoque, Integer> {
    Optional<ItemEstoque> findByDescricao();
}
