package io.github.diegopaoliello.estockappapi.service;

import io.github.diegopaoliello.estockappapi.model.entity.Estoque;
import io.github.diegopaoliello.estockappapi.model.entity.EstoqueEntrada;
import io.github.diegopaoliello.estockappapi.model.entity.EstoqueSaida;
import io.github.diegopaoliello.estockappapi.model.entity.Produto;
import io.github.diegopaoliello.estockappapi.model.entity.VendaItem;
import io.github.diegopaoliello.estockappapi.model.repository.EstoqueRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstoqueService extends AbstractService<Estoque, EstoqueRepository> {
	@Autowired
	private VendaItemService vendaItemService;

	public EstoqueService() {
		super(Estoque.class);
	}

	@Transactional
	public Estoque entrada(EstoqueEntrada estoqueEntrada) {
		Produto produto = estoqueEntrada.getProduto();
		Estoque estoqueProduto = super.repository.findByProduto(produto).map(estoque -> estoque).orElse(new Estoque());
		BigDecimal quantidade = estoqueProduto.getQuantidade().add(estoqueEntrada.getQuantidade());

		estoqueProduto.setProduto(produto);
		estoqueProduto.setQuantidade(quantidade);
		estoqueProduto.setUsuario(estoqueEntrada.getUsuario());

		return super.salvar(estoqueProduto);
	}

	public Estoque saida(EstoqueSaida estoqueSaida) {
		VendaItem vendaItem = vendaItemService.acharPorId(estoqueSaida.getItemVenda().getId());
		Produto produto = vendaItem.getProduto();
		Estoque estoqueProduto = super.repository.findByProduto(produto).map(estoque -> estoque).orElse(new Estoque());
		BigDecimal quantidade = estoqueProduto.getQuantidade().subtract(vendaItem.getQuantidade());

		estoqueProduto.setProduto(produto);
		estoqueProduto.setQuantidade(quantidade);
		estoqueProduto.setUsuario(estoqueSaida.getUsuario());

		return super.salvar(estoqueProduto);
	}

	public List<Estoque> listar(Integer idProduto, BigDecimal quantidade) {
		List<Estoque> estoque = repository.listar(idProduto, quantidade).orElse(new ArrayList<Estoque>());

		return estoque;
	}
}