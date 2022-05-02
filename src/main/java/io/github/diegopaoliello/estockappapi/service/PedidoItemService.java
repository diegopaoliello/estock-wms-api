package io.github.diegopaoliello.estockappapi.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import io.github.diegopaoliello.estockappapi.exception.PedidoItemProdutoExistente;
import io.github.diegopaoliello.estockappapi.exception.ProdutoQtdeMax;
import io.github.diegopaoliello.estockappapi.model.entity.Estoque;
import io.github.diegopaoliello.estockappapi.model.entity.Pedido;
import io.github.diegopaoliello.estockappapi.model.entity.PedidoItem;
import io.github.diegopaoliello.estockappapi.model.entity.Produto;
import io.github.diegopaoliello.estockappapi.model.repository.EstoqueRepository;
import io.github.diegopaoliello.estockappapi.model.repository.PedidoItemRepository;
import io.github.diegopaoliello.estockappapi.model.repository.ProdutoRepository;

@Service
public class PedidoItemService extends AbstractService<PedidoItem, PedidoItemRepository> {
	@Autowired
	private EstoqueRepository estoqueRepository;

	@Autowired
	private ProdutoRepository produtoRepository;

	public PedidoItemService() {
		super("Item do Pedido");
	}

	@Override
	public PedidoItem salvar(PedidoItem itemPedido) {
		try {
			preencheValoresAusentes(itemPedido);
			valida(itemPedido);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}

		return super.salvar(itemPedido);
	}

	@Override
	public void atualizar(Integer id, PedidoItem itemPedido) {
		try {
			preencheValoresAusentes(itemPedido);
			valida(itemPedido);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}

		super.atualizar(id, itemPedido);
	}

	public List<PedidoItem> listar(Integer idPedido) {
		return repository.listar(idPedido);
	}

	public List<PedidoItem> acharPorPedido(Pedido pedido) {
		return super.repository.findByPedido(pedido).orElse(new ArrayList<PedidoItem>());
	}

	private void valida(PedidoItem itemPedido) {
		Produto produto = produtoRepository.getById(itemPedido.getProduto().getId());
		Pedido pedido = itemPedido.getPedido();

		PedidoItem produtoExistente = super.repository.findByPedidoAndProduto(pedido, produto).orElse(null);

		if (produtoExistente != null && (produtoExistente.getId() != itemPedido.getId())) {
			throw new PedidoItemProdutoExistente(produto.getDescricao());
		}

		Estoque estoqueProduto = estoqueRepository.findByProduto(produto).map(estoque -> estoque).orElse(new Estoque());

		List<PedidoItem> produtosPedidoAberto = super.repository
				.findByProdutoAndStatusEqualAberto(pedido.getId(), produto.getId()).map(estoque -> estoque)
				.orElse(new ArrayList<PedidoItem>());

		BigDecimal quantidadeProdutosAberto = produtosPedidoAberto.stream().map(x -> x.getQuantidade())
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		BigDecimal quantidadeProdutos = estoqueProduto.getQuantidade().add(itemPedido.getQuantidade())
				.add(quantidadeProdutosAberto);

		if (quantidadeProdutos.compareTo(produto.getQuantidadeMaxima()) == 1) {
			throw new ProdutoQtdeMax(produto.getQuantidadeMaxima().toString());
		}
	}

	private void preencheValoresAusentes(PedidoItem itemPedido) {
		if (itemPedido.getQuantidade() == null) {
			itemPedido.setQuantidade(BigDecimal.ZERO);
		}
	}
}