package com.distribuidora.estoque.controller;

import com.distribuidora.estoque.model.ItemEstoque;
import com.distribuidora.estoque.repository.EstoqueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estoque")
@RequiredArgsConstructor
public class EstoqueController {

    private final EstoqueRepository repository;

    @PostMapping
    public ResponseEntity<ItemEstoque> cadastrarNovoItem(@RequestBody ItemEstoque novoItem) {
        ItemEstoque itemSalvo = repository.save(novoItem);
        return ResponseEntity.status(HttpStatus.CREATED).body(itemSalvo);
    }

    @GetMapping
    public ResponseEntity<List<ItemEstoque>> listarEstoque() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemEstoque> buscarPorId(@PathVariable int id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}