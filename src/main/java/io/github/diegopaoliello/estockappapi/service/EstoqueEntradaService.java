package io.github.diegopaoliello.estockappapi.service;

import io.github.diegopaoliello.estockappapi.model.entity.EstoqueEntrada;
import io.github.diegopaoliello.estockappapi.model.entity.PedidoItem;
import io.github.diegopaoliello.estockappapi.model.entity.Produto;
import io.github.diegopaoliello.estockappapi.model.repository.EstoqueEntradaRepository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class EstoqueEntradaService extends AbstractService<EstoqueEntrada, EstoqueEntradaRepository> {
	@Autowired
	private EstoqueService estoqueService;

	@Autowired
	private ProdutoService produtoService;

	public EstoqueEntradaService() {
		super(EstoqueEntrada.class);
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

		Pageable pageable = PageRequest.of(0, 3, Sort.by("id").descending());

		List<EstoqueEntrada> estoqueEntrada = super.repository.findByProduto(produto, pageable).map(estoque -> estoque)
				.orElse(new ArrayList<EstoqueEntrada>());

		produtoService.atualizarPrecoMedio(produto, estoqueEntrada);

		return entradaEstoque;
	}
}