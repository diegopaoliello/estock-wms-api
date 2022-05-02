package io.github.diegopaoliello.estockappapi.service;

import io.github.diegopaoliello.estockappapi.model.entity.EstoqueEntrada;
import io.github.diegopaoliello.estockappapi.model.entity.PedidoItem;
import io.github.diegopaoliello.estockappapi.model.entity.Produto;
import io.github.diegopaoliello.estockappapi.model.repository.EstoqueEntradaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstoqueEntradaService extends AbstractService<EstoqueEntrada, EstoqueEntradaRepository> {
	@Autowired
	private EstoqueService estoqueService;

	@Autowired
	private ProdutoService produtoService;

	public EstoqueEntradaService() {
		super("Entrada de Estoque");
	}

	public EstoqueEntrada gerar(PedidoItem itemPedido) {
		EstoqueEntrada entradaEstoque = new EstoqueEntrada();
		Produto produto = itemPedido.getProduto();

		entradaEstoque.setItemPedido(itemPedido);
		entradaEstoque.setProduto(produto);
		entradaEstoque.setPreco(itemPedido.getValorFinal());
		entradaEstoque.setQuantidade(itemPedido.getQuantidade());

		entradaEstoque = super.salvar(entradaEstoque);

		estoqueService.entrada(entradaEstoque);

		produtoService.atualizarPrecoMedio(produto);

		return entradaEstoque;
	}
}