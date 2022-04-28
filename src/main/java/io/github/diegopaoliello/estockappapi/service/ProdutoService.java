package io.github.diegopaoliello.estockappapi.service;

import io.github.diegopaoliello.estockappapi.model.entity.EstoqueEntrada;
import io.github.diegopaoliello.estockappapi.model.entity.Produto;
import io.github.diegopaoliello.estockappapi.model.repository.ProdutoRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class ProdutoService extends AbstractService<Produto, ProdutoRepository> {
	public ProdutoService() {
		super(Produto.class);
	}

	public BigDecimal calcularPrecoMedio(Produto produto, List<EstoqueEntrada> estoqueEntrada) {
		BigDecimal valorTotal = estoqueEntrada.stream().map(x -> x.getPreco().multiply(x.getQuantidade()))
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		BigDecimal quantidadeTotal = estoqueEntrada.stream().map(x -> x.getQuantidade()).reduce(BigDecimal.ZERO,
				BigDecimal::add);

		BigDecimal precoMedio = valorTotal.divide(quantidadeTotal, 2, RoundingMode.HALF_UP);

		return precoMedio;
	}

	public void atualizarPrecoMedio(Produto produto, List<EstoqueEntrada> estoqueEntrada) {
		BigDecimal precoMedio = calcularPrecoMedio(produto, estoqueEntrada);

		produto.setPrecoMedio(precoMedio);

		super.atualizar(produto.getId(), produto);
	}

}