package io.github.diegopaoliello.estockappapi.service;

import io.github.diegopaoliello.estockappapi.exception.ProdutoQtdeMinMaiorMax;
import io.github.diegopaoliello.estockappapi.model.entity.EstoqueEntrada;
import io.github.diegopaoliello.estockappapi.model.entity.Produto;
import io.github.diegopaoliello.estockappapi.model.repository.EstoqueEntradaRepository;
import io.github.diegopaoliello.estockappapi.model.repository.ProdutoRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ProdutoService extends AbstractService<Produto, ProdutoRepository> {
	@Autowired
	private EstoqueEntradaRepository estoqueEntradaRepository;

	public ProdutoService() {
		super("Produto");
	}

	@Override
	public Produto salvar(Produto produto) {
		try {
			preencheValoresAusentes(produto);
			valida(produto);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}

		return super.salvar(produto);
	}

	@Override
	public void atualizar(Integer id, Produto produto) {
		try {
			preencheValoresAusentes(produto);
			valida(produto);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}

		super.atualizar(id, produto);
	}

	public BigDecimal calcularPrecoMedio(Produto produto) {
		Pageable pageable = PageRequest.of(0, 3, Sort.by("id").descending());

		List<EstoqueEntrada> estoqueEntrada = estoqueEntradaRepository.findByProduto(produto, pageable)
				.map(estoque -> estoque).orElse(new ArrayList<EstoqueEntrada>());

		BigDecimal valorTotal = estoqueEntrada.stream().map(x -> x.getPreco().multiply(x.getQuantidade()))
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		BigDecimal quantidadeTotal = estoqueEntrada.stream().map(x -> x.getQuantidade()).reduce(BigDecimal.ZERO,
				BigDecimal::add);

		BigDecimal precoMedio = BigDecimal.ZERO;

		if (valorTotal.compareTo(BigDecimal.ZERO) == 1 && quantidadeTotal.compareTo(BigDecimal.ZERO) == 1) {
			precoMedio = valorTotal.divide(quantidadeTotal, 2, RoundingMode.HALF_UP);
		}

		return precoMedio;
	}

	public BigDecimal calcularPrecoMedio(Integer idProduto) {
		Produto produto = super.acharPorId(idProduto);

		return calcularPrecoMedio(produto);
	}

	public void atualizarPrecoMedio(Produto produto) {
		BigDecimal precoMedio = calcularPrecoMedio(produto);

		produto.setPrecoMedio(precoMedio);

		super.atualizar(produto.getId(), produto);
	}

	private void valida(Produto produto) {
		if (produto.getQuantidadeMinima().compareTo(produto.getQuantidadeMaxima()) == 1) {
			throw new ProdutoQtdeMinMaiorMax();
		}
	}

	private void preencheValoresAusentes(Produto produto) {
		if (produto.getQuantidadeMinima() == null) {
			produto.setQuantidadeMinima(BigDecimal.ZERO);
		}

		if (produto.getQuantidadeMaxima() == null) {
			produto.setQuantidadeMaxima(BigDecimal.ZERO);
		}
	}

}