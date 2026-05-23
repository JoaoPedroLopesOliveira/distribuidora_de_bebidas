package com.distribuidora.compras.repository;

import com.distribuidora.compras.model.Categoria;
import com.distribuidora.compras.model.Compras;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComprasRepository extends JpaRepository<Compras, Long> {
    List<Compras> findByCategoria(Categoria categoria);
}
