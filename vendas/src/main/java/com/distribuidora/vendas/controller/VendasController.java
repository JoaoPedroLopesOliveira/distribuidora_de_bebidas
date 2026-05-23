package com.distribuidora.vendas.controller;

import com.distribuidora.vendas.model.Vendas;
import com.distribuidora.vendas.service.VendasService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vendas")
@RequiredArgsConstructor
public class VendasController {

    private final VendasService vendasService;

    @PostMapping
    public ResponseEntity<Vendas> criar(@RequestBody Vendas vendas) {
        return ResponseEntity.status(HttpStatus.CREATED).body(vendasService.criar(vendas));
    }

    @GetMapping
    public ResponseEntity<List<Vendas>> listarTodos() {
        return ResponseEntity.ok(vendasService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vendas> buscarPorId(@PathVariable Long id) {
        return vendasService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        vendasService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}