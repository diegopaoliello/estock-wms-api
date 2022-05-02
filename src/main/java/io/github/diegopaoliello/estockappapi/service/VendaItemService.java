package io.github.diegopaoliello.estockappapi.service;

import io.github.diegopaoliello.estockappapi.exception.ProdutoAbaixoPrecoMedio;
import io.github.diegopaoliello.estockappapi.exception.ProdutoQtdeEstoque;
import io.github.diegopaoliello.estockappapi.exception.VendaItemProdutoExistente;
import io.github.diegopaoliello.estockappapi.model.entity.Estoque;
import io.github.diegopaoliello.estockappapi.model.entity.Produto;
import io.github.diegopaoliello.estockappapi.model.entity.Venda;
import io.github.diegopaoliello.estockappapi.model.entity.VendaItem;
import io.github.diegopaoliello.estockappapi.model.repository.EstoqueRepository;
import io.github.diegopaoliello.estockappapi.model.repository.VendaItemRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class VendaItemService extends AbstractService<VendaItem, VendaItemRepository> {
	@Autowired
	private EstoqueRepository estoqueRepository;

	@Autowired
	private ProdutoService produtoService;

	public VendaItemService() {
		super(VendaItem.class);
	}

	public List<VendaItem> listar(Integer idVenda) {
		return repository.listar(idVenda);
	}

	@Override
	public VendaItem salvar(VendaItem itemVenda) {
		try {
			preencheValoresAusentes(itemVenda);
			valida(itemVenda);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}

		return super.salvar(itemVenda);
	}

	@Override
	public void atualizar(Integer id, VendaItem itemVenda) {
		try {
			preencheValoresAusentes(itemVenda);
			valida(itemVenda);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}

		super.atualizar(id, itemVenda);
	}

	public List<VendaItem> acharPorVenda(Venda venda) {
		return super.repository.findByVenda(venda).orElse(new ArrayList<VendaItem>());
	}

	private void valida(VendaItem itemVenda) {
		Produto produto = produtoService.acharPorId(itemVenda.getProduto().getId());
		BigDecimal precoMedio = produtoService.calcularPrecoMedio(produto);

		if (itemVenda.calcularValorFinal().compareTo(precoMedio) == -1) {
			throw new ProdutoAbaixoPrecoMedio(precoMedio.toString());
		}

		Venda venda = itemVenda.getVenda();

		VendaItem produtoExistente = super.repository.findByVendaAndProduto(venda, produto).orElse(null);

		if (produtoExistente != null && (produtoExistente.getId() != itemVenda.getId())) {
			throw new VendaItemProdutoExistente(produto.getDescricao());
		}

		Estoque estoqueProduto = estoqueRepository.findByProduto(itemVenda.getProduto()).map(estoque -> estoque)
				.orElse(new Estoque());

		List<VendaItem> produtosVendaAberto = super.repository
				.findByProdutoAndStatusEqualAberto(venda.getId(), produto.getId()).map(estoque -> estoque)
				.orElse(new ArrayList<VendaItem>());

		BigDecimal quantidadeProdutosAberto = produtosVendaAberto.stream().map(x -> x.getQuantidade())
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		BigDecimal quantidadeProdutos = estoqueProduto.getQuantidade().subtract(itemVenda.getQuantidade())
				.subtract(quantidadeProdutosAberto);

		if (quantidadeProdutos.compareTo(BigDecimal.ZERO) == -1) {
			throw new ProdutoQtdeEstoque(estoqueProduto.getQuantidade().toString());
		}
	}

	private void preencheValoresAusentes(VendaItem itemVenda) {
		if (itemVenda.getQuantidade() == null) {
			itemVenda.setQuantidade(BigDecimal.ZERO);
		}

		if (itemVenda.getPreco() == null) {
			itemVenda.setPreco(BigDecimal.ZERO);
		}

		if (itemVenda.getDesconto() == null) {
			itemVenda.setDesconto(BigDecimal.ZERO);
		}
	}
}