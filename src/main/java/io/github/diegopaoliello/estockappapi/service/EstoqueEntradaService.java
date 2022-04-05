package io.github.diegopaoliello.estockappapi.service;

import io.github.diegopaoliello.estockappapi.model.entity.EstoqueEntrada;
import io.github.diegopaoliello.estockappapi.model.entity.PedidoItem;
import io.github.diegopaoliello.estockappapi.model.repository.EstoqueEntradaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstoqueEntradaService extends AbstractService<EstoqueEntrada, EstoqueEntradaRepository> {
	@Autowired
	private EstoqueService estoqueService;

	public EstoqueEntradaService() {
		super(EstoqueEntrada.class);
	}

	public EstoqueEntrada gerar(PedidoItem itemPedido) {
		EstoqueEntrada entradaEstoque = new EstoqueEntrada();
		entradaEstoque.setItemPedido(itemPedido);
		entradaEstoque = super.salvar(entradaEstoque);
		estoqueService.salvar(entradaEstoque);

		return entradaEstoque;
	}
}