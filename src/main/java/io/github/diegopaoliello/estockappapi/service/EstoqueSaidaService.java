package io.github.diegopaoliello.estockappapi.service;

import io.github.diegopaoliello.estockappapi.model.entity.EstoqueSaida;
import io.github.diegopaoliello.estockappapi.model.entity.VendaItem;
import io.github.diegopaoliello.estockappapi.model.repository.EstoqueSaidaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstoqueSaidaService extends AbstractService<EstoqueSaida, EstoqueSaidaRepository> {
	@Autowired
	private EstoqueService estoqueService;

	public EstoqueSaidaService() {
		super(EstoqueSaida.class);
	}

	public EstoqueSaida gerar(VendaItem itemVenda) {
		EstoqueSaida saidaEstoque = new EstoqueSaida();
		saidaEstoque.setItemVenda(itemVenda);
		saidaEstoque = super.salvar(saidaEstoque);
		estoqueService.saida(saidaEstoque);

		return saidaEstoque;
	}
}