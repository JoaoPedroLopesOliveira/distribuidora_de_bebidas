package com.distribuidora.vendas.repository;

import com.distribuidora.vendas.model.Vendas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendasRepository extends JpaRepository<Vendas, Long> {
}