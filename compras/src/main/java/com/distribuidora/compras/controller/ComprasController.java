package com.distribuidora.compras.controller;

import com.distribuidora.compras.model.Compras;
import com.distribuidora.compras.service.ComprasService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/compras")

public class ComprasController {

    private final ComprasService comprasService;

    public ComprasController(ComprasService comprasService) {
        this.comprasService = comprasService;
    }

    @PostMapping
    public ResponseEntity<Compras> criar(@RequestBody Compras compras) {
        return ResponseEntity.ok(comprasService.criar(compras));
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
