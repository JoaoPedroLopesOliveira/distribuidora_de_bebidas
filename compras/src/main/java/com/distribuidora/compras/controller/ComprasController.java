package com.distribuidora.compras.controller;

import com.distribuidora.compras.model.Compras;
import com.distribuidora.compras.service.ComprasService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/compras")
@RequiredArgsConstructor
public class ComprasController {

    private final ComprasService comprasService;

    @PostMapping
    public ResponseEntity<Compras> criar(@RequestBody Compras compras) {
        return ResponseEntity.status(HttpStatus.CREATED).body(comprasService.criar(compras));
    }

    @GetMapping
    public ResponseEntity<List<Compras>> listarTodos() {
        return ResponseEntity.ok(comprasService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Compras> buscarPorId(@PathVariable Long id) {
        return comprasService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        comprasService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}