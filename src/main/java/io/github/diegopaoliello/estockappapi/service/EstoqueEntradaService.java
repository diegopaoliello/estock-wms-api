package io.github.diegopaoliello.estockappapi.service;

import io.github.diegopaoliello.estockappapi.model.entity.EstoqueEntrada;
import io.github.diegopaoliello.estockappapi.model.entity.PedidoItem;
import io.github.diegopaoliello.estockappapi.model.entity.Produto;
import io.github.diegopaoliello.estockappapi.model.repository.EstoqueEntradaRepository;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class EstoqueEntradaService extends AbstractService<EstoqueEntrada, EstoqueEntradaRepository> {
	@Autowired
	private EstoqueService estoqueService;

	@Autowired
	private ProdutoService produtoService;

	public EstoqueEntradaService() {
		super("Entrada de Estoque");
	}

	@Override
	@Transactional
	public EstoqueEntrada salvar(EstoqueEntrada entradaEstoque) {
		try {
			estoqueService.entrada(entradaEstoque);
			produtoService.atualizarPrecoMedio(entradaEstoque.getProduto());
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}

		return super.salvar(entradaEstoque);
	}

	public EstoqueEntrada gerar(PedidoItem itemPedido) {
		EstoqueEntrada entradaEstoque = new EstoqueEntrada();
		Produto produto = itemPedido.getProduto();

		entradaEstoque.setItemPedido(itemPedido);
		entradaEstoque.setProduto(produto);
		entradaEstoque.setPreco(itemPedido.getValorFinal());
		entradaEstoque.setQuantidade(itemPedido.getQuantidade());

		return salvar(entradaEstoque);
	}
}