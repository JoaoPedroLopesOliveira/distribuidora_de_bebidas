package com.distribuidora.vendas.service;

import com.distribuidora.vendas.model.Categoria;
import com.distribuidora.vendas.model.Vendas;
import com.distribuidora.vendas.repository.VendasRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VendasServiceTest {

    @Mock
    private VendasRepository vendasRepository;

    @Mock
    private VendasProducer vendasProducer;

    @InjectMocks
    private VendasService vendasService;

    private Vendas vendaBase;

    @BeforeEach
    void setUp() {
        vendaBase = new Vendas();
        vendaBase.setIdProduto(1);
        vendaBase.setDescricao("Cerveja Heineken");
        vendaBase.setCategoria(Categoria.CERVEJA);
        vendaBase.setValorUnitario(new BigDecimal("10.00"));
        vendaBase.setQuantidadeVendida(10);
    }

    // ----------------------------------------------------------------
    // Testes de criação
    // ----------------------------------------------------------------

    @Test
    @DisplayName("Ao criar uma venda, o status deve ser PENDENTE")
    void deveCriarVendaComStatusPendente() {
        when(vendasRepository.save(any(Vendas.class))).thenAnswer(inv -> {
            Vendas v = inv.getArgument(0);
            v.setId(1L);
            return v;
        });

        Vendas resultado = vendasService.criar(vendaBase);

        assertThat(resultado.getStatus()).isEqualTo("PENDENTE");
    }

    @Test
    @DisplayName("Ao criar uma venda, a data deve ser preenchida automaticamente")
    void deveCriarVendaComDataPreenchidaAutomaticamente() {
        LocalDateTime antes = LocalDateTime.now().minusSeconds(1);

        when(vendasRepository.save(any(Vendas.class))).thenAnswer(inv -> {
            Vendas v = inv.getArgument(0);
            v.setId(1L);
            return v;
        });

        Vendas resultado = vendasService.criar(vendaBase);

        assertThat(resultado.getDataVenda())
                .isNotNull()
                .isAfter(antes);
    }

    @Test
    @DisplayName("Ao criar uma venda, o Producer Kafka deve ser chamado")
    void deveChamarProducerKafkaAoCriarVenda() {
        when(vendasRepository.save(any(Vendas.class))).thenAnswer(inv -> {
            Vendas v = inv.getArgument(0);
            v.setId(1L);
            return v;
        });

        vendasService.criar(vendaBase);

        verify(vendasProducer, times(1)).enviarVenda("1", 1, 10);
    }

    @Test
    @DisplayName("Ao criar uma venda, o repository deve ser chamado para salvar")
    void deveSalvarVendaNoRepositoryAoCriar() {
        when(vendasRepository.save(any(Vendas.class))).thenAnswer(inv -> {
            Vendas v = inv.getArgument(0);
            v.setId(1L);
            return v;
        });

        vendasService.criar(vendaBase);

        verify(vendasRepository, times(1)).save(vendaBase);
    }

    @Test
    @DisplayName("Ao criar uma venda, os dados originais devem ser preservados")
    void deveManter_DadosOriginaisAoCriarVenda() {
        when(vendasRepository.save(any(Vendas.class))).thenAnswer(inv -> {
            Vendas v = inv.getArgument(0);
            v.setId(1L);
            return v;
        });

        Vendas resultado = vendasService.criar(vendaBase);

        assertThat(resultado.getDescricao()).isEqualTo("Cerveja Heineken");
        assertThat(resultado.getCategoria()).isEqualTo(Categoria.CERVEJA);
        assertThat(resultado.getQuantidadeVendida()).isEqualTo(10);
        assertThat(resultado.getValorUnitario()).isEqualByComparingTo(new BigDecimal("10.00"));
    }

    // ----------------------------------------------------------------
    // Testes de listagem
    // ----------------------------------------------------------------

    @Test
    @DisplayName("Ao listar todos, o repository deve ser chamado")
    void deveChamarRepositoryAoListarTodas() {
        when(vendasRepository.findAll()).thenReturn(List.of());

        vendasService.listarTodos();

        verify(vendasRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Ao listar todos, deve retornar a lista completa do repository")
    void deveRetornarListaCompletaAoListarTodos() {
        Vendas v1 = new Vendas();
        v1.setId(1L);
        Vendas v2 = new Vendas();
        v2.setId(2L);

        when(vendasRepository.findAll()).thenReturn(List.of(v1, v2));

        List<Vendas> resultado = vendasService.listarTodos();

        assertThat(resultado).hasSize(2);
    }

    // ----------------------------------------------------------------
    // Testes de busca por ID
    // ----------------------------------------------------------------

    @Test
    @DisplayName("Ao buscar por ID existente, deve retornar a venda")
    void deveRetornarVendaParaIdExistente() {
        vendaBase.setId(1L);
        when(vendasRepository.findById(1L)).thenReturn(Optional.of(vendaBase));

        Optional<Vendas> resultado = vendasService.buscarPorId(1L);

        assertThat(resultado).isPresent();
        assertThat(resultado.get().getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("Ao buscar por ID inexistente, deve retornar Optional vazio")
    void deveRetornarVazioParaIdInexistente() {
        when(vendasRepository.findById(9999L)).thenReturn(Optional.empty());

        Optional<Vendas> resultado = vendasService.buscarPorId(9999L);

        assertThat(resultado).isEmpty();
    }

    // ----------------------------------------------------------------
    // Testes de exclusão
    // ----------------------------------------------------------------

    @Test
    @DisplayName("Ao deletar, o repository deve ser chamado corretamente")
    void deveChamarRepositoryAoDeletar() {
        vendasService.deletar(1L);

        verify(vendasRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Ao deletar, o Kafka Producer não deve ser chamado")
    void naoDeveChamarKafkaAoDeletar() {
        vendasService.deletar(1L);

        verifyNoInteractions(vendasProducer);
    }

    // ----------------------------------------------------------------
    // Testes de validação de categoria (enum)
    // ----------------------------------------------------------------

    @Test
    @DisplayName("O enum Categoria deve conter apenas os valores válidos do negócio")
    void categoriaDeveConterApenasValoresValidos() {
        Categoria[] categorias = Categoria.values();

        assertThat(categorias).containsExactlyInAnyOrder(
                Categoria.CERVEJA,
                Categoria.DESTILADO,
                Categoria.REFRIGERANTE,
                Categoria.AGUA,
                Categoria.VINHO
        );
    }

    @Test
    @DisplayName("Deve criar venda com categoria VINHO sem erros")
    void deveCriarVendaComCategoriaVinho() {
        vendaBase.setCategoria(Categoria.VINHO);

        when(vendasRepository.save(any(Vendas.class))).thenAnswer(inv -> {
            Vendas v = inv.getArgument(0);
            v.setId(2L);
            return v;
        });

        Vendas resultado = vendasService.criar(vendaBase);

        assertThat(resultado.getCategoria()).isEqualTo(Categoria.VINHO);
    }
}
