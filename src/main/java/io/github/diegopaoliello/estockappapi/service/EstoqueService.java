package io.github.diegopaoliello.estockappapi.service;

import io.github.diegopaoliello.estockappapi.model.entity.Estoque;
import io.github.diegopaoliello.estockappapi.model.entity.EstoqueEntrada;
import io.github.diegopaoliello.estockappapi.model.entity.PedidoItem;
import io.github.diegopaoliello.estockappapi.model.entity.Produto;
import io.github.diegopaoliello.estockappapi.model.repository.EstoqueRepository;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstoqueService extends AbstractService<Estoque, EstoqueRepository> {
	@Autowired
	private PedidoItemService pedidoItemService;

	public EstoqueService() {
		super(Estoque.class);
	}

	public Estoque salvar(EstoqueEntrada estoqueEntrada) {
		PedidoItem pedidoItem = pedidoItemService.acharPorId(estoqueEntrada.getItemPedido().getId());
		Produto produto = estoqueEntrada.getItemPedido().getProduto();
		Estoque estoqueProduto = super.repository.findByProduto(produto).map(estoque -> estoque).orElse(new Estoque());
		BigDecimal quantidade = estoqueProduto.getQuantidade().add(pedidoItem.getQuantidade());

		estoqueProduto.setProduto(produto);
		estoqueProduto.setQuantidade(quantidade);
		estoqueProduto.setUsuario(estoqueEntrada.getUsuario());

		return super.salvar(estoqueProduto);
	}
}