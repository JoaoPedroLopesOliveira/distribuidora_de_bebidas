package com.distribuidora.estoque;

import com.distribuidora.estoque.model.ItemEstoque;
import com.distribuidora.estoque.repository.EstoqueRepository;
import com.distribuidora.estoque.service.EstoqueService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class EstoqueServiceTest {

    @Mock
    private EstoqueRepository repository;

    @InjectMocks
    private EstoqueService estoqueService;

    @Test
    void deveAdicionarQuantidadeAoEstoque() {

        int idProduto = 1;
        ItemEstoque itemExistente = new ItemEstoque();
        itemExistente.setId(idProduto);
        itemExistente.setQuantidade(10);

        Mockito.when(repository.findById(idProduto)).thenReturn(Optional.of(itemExistente));


        estoqueService.adicionarQuantidade(idProduto, 5);


        assertEquals(15, itemExistente.getQuantidade(), "A quantidade deve ser somada corretamente");
        Mockito.verify(repository).save(itemExistente);
    }

    @Test
    void deveSubtrairQuantidadeDoEstoque() {

        int idProduto = 2;
        ItemEstoque itemExistente = new ItemEstoque();
        itemExistente.setId(idProduto);
        itemExistente.setQuantidade(20);

        Mockito.when(repository.findById(idProduto)).thenReturn(Optional.of(itemExistente));


        estoqueService.subtrairQuantidade(idProduto, 8);


        assertEquals(12, itemExistente.getQuantidade(), "A quantidade deve ser subtraída corretamente");
        Mockito.verify(repository).save(itemExistente);
    }

    @Test
    void naoDevePermitirEstoqueNegativoAoSubtrair() {

        int idProduto = 3;
        ItemEstoque itemExistente = new ItemEstoque();
        itemExistente.setId(idProduto);
        itemExistente.setQuantidade(5);

        Mockito.when(repository.findById(idProduto)).thenReturn(Optional.of(itemExistente));


        assertThrows(IllegalStateException.class, () -> {
            estoqueService.subtrairQuantidade(idProduto, 10);
        }, "Deveria lançar IllegalStateException por falta de estoque");


        Mockito.verify(repository, Mockito.never()).save(Mockito.any(ItemEstoque.class));
    }
}