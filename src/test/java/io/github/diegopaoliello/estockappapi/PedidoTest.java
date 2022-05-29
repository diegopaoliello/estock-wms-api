package io.github.diegopaoliello.estockappapi;

import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import io.github.diegopaoliello.estockappapi.model.entity.Categoria;
import io.github.diegopaoliello.estockappapi.model.entity.Fornecedor;
import io.github.diegopaoliello.estockappapi.model.entity.Pedido;
import io.github.diegopaoliello.estockappapi.model.entity.PedidoItem;
import io.github.diegopaoliello.estockappapi.model.entity.PedidoStatus;
import io.github.diegopaoliello.estockappapi.model.entity.Produto;
import io.github.diegopaoliello.estockappapi.model.entity.UnidadeMedida;
import io.github.diegopaoliello.estockappapi.model.repository.PedidoStatusRepository;
import io.github.diegopaoliello.estockappapi.service.UnidadeMedidaService;
import io.github.diegopaoliello.estockappapi.service.PedidoItemService;
import io.github.diegopaoliello.estockappapi.service.CategoriaService;
import io.github.diegopaoliello.estockappapi.service.FornecedorService;
import io.github.diegopaoliello.estockappapi.service.PedidoService;
import io.github.diegopaoliello.estockappapi.service.ProdutoService;

@SpringBootTest
@Transactional
@TestInstance(Lifecycle.PER_CLASS)
class PedidoTest {
	@Autowired
	private UnidadeMedidaService unidadeMedidaService;
	@Autowired
	private CategoriaService categoriaService;
	@Autowired
	private ProdutoService produtoService;
	@Autowired
	private FornecedorService fornecedorService;
	@Autowired
	private PedidoService pedidoService;
	@Autowired
	private PedidoItemService pedidoItemService;
	@Autowired
	private PedidoStatusRepository pedidoStatusRepository;

	@Test()
	void produtoDuplicado() {
		ResponseStatusException thrown = Assertions.assertThrows(ResponseStatusException.class, () -> {
			Pedido pedido = pedidoService.acharPorId(1);
			Produto produto = produtoService.acharPorId(1);

			pedidoItemService.salvar(
					new PedidoItem(pedido, new BigDecimal(100), BigDecimal.ZERO, new BigDecimal(100), null, produto));
			pedidoItemService.salvar(
					new PedidoItem(pedido, new BigDecimal(100), BigDecimal.ZERO, new BigDecimal(100), null, produto));

		}, "ResponseStatusException deve ocorrer");

		Assertions.assertTrue(thrown.getMessage().contains("Já existe o item SSD adicionado ao pedido"));
	}

	@BeforeAll
	void montaAmbienteTeste() {
		UnidadeMedida unidadeMedida = unidadeMedidaService.salvar(new UnidadeMedida("Peça", "Pc"));
		Categoria categoria = categoriaService.salvar(new Categoria("Hardware"));
		produtoService.salvar(
				new Produto("SSD", "SSD", categoria, unidadeMedida, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO));
		Fornecedor fornecedor = fornecedorService
				.salvar(new Fornecedor("Fornecedor Teste", "Fornecedor", "39601973000111"));
		PedidoStatus pedidoStatus = pedidoStatusRepository.getById(1);

		pedidoService.salvar(new Pedido(pedidoStatus, fornecedor));
	}
}
