package com.distribuidora.compras;

import com.distribuidora.compras.model.Categoria;
import com.distribuidora.compras.model.Compras;
import com.distribuidora.compras.repository.ComprasRepository;
import com.distribuidora.compras.service.ComprasProducer;
import com.distribuidora.compras.service.ComprasService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyInt;

@ExtendWith(MockitoExtension.class)
class ComprasServiceTest {

    @Mock
    private ComprasRepository comprasRepository;

    @Mock
    private ComprasProducer comprasProducer;

    @InjectMocks
    private ComprasService comprasService;

    @Test
    void deveCriarCompraComStatusPendenteEChamarProducer() {

        Compras novaCompras = new Compras();
        novaCompras.setId(1L);
        novaCompras.setIdProduto(10);
        novaCompras.setDescricao("Cerveja Heineken");
        novaCompras.setCategoria(Categoria.CERVEJA);
        novaCompras.setQuantidadeComprada(50);
        novaCompras.setPrecoCusto(new java.math.BigDecimal(5.50));

        Mockito.when(comprasRepository.save(any(Compras.class))).thenReturn(novaCompras);



        Compras comprasSalva = comprasService.criar(novaCompras);


        assertNotNull(comprasSalva, "A compra não deveria ser nula");
        assertEquals("PENDENTE", comprasSalva.getStatus(), "O status inicial deve ser PENDENTE");

        Mockito.verify(comprasRepository).save(any(Compras.class));


        Mockito.verify(comprasProducer).enviarCompra(anyString(), anyInt(), anyInt());
    }

    @Test
    void naoDeveCriarCompraComQuantidadeNegativa() {

        Compras compraInvalida = new Compras();
        compraInvalida.setDescricao("Refrigerante Cola");
        compraInvalida.setCategoria(Categoria.REFRIGERANTE);
        compraInvalida.setPrecoCusto(new java.math.BigDecimal("10.00"));


        compraInvalida.setQuantidadeComprada(-5);


        org.junit.jupiter.api.Assertions.assertThrows(Exception.class, () -> {
            comprasService.criar(compraInvalida);
        }, "Deveria lançar uma exceção ao tentar salvar quantidade negativa");



        Mockito.verify(comprasRepository, Mockito.never()).save(any(Compras.class));


        Mockito.verify(comprasProducer, Mockito.never()).enviarCompra(anyString(), anyInt(), anyInt());
    }
    @Test
    void deveRetornarCompraPorIdQuandoExistir() {
        Compras compra = new Compras();
        compra.setId(1L);
        Mockito.when(comprasRepository.findById(1L)).thenReturn(Optional.of(compra));

        Optional<Compras> resultado = comprasService.buscarPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals(1L, resultado.get().getId());
    }

    @Test
    void deveRetornarVazioQuandoIdNaoExistir() {
        Mockito.when(comprasRepository.findById(99L)).thenReturn(Optional.empty());
        Optional<Compras> resultado = comprasService.buscarPorId(99L);
        assertFalse(resultado.isEmpty());
    }
}