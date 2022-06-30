package io.github.diegopaoliello.estockappapi.service;

import io.github.diegopaoliello.estockappapi.exception.ProdutoQtdeMinMaiorMax;
import io.github.diegopaoliello.estockappapi.exception.UniqueException;
import io.github.diegopaoliello.estockappapi.model.entity.EstoqueEntrada;
import io.github.diegopaoliello.estockappapi.model.entity.Produto;
import io.github.diegopaoliello.estockappapi.model.repository.EstoqueEntradaRepository;
import io.github.diegopaoliello.estockappapi.model.repository.ProdutoRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ProdutoService extends AbstractService<Produto, ProdutoRepository> {
	private static String entidade = "Produto";

	@Autowired
	private EstoqueEntradaRepository estoqueEntradaRepository;

	public ProdutoService() {
		super("Produto");
	}

	@Override
	public Produto salvar(Produto produto) {
		try {
			valida(null, produto);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}

		return super.salvar(produto);
	}

	@Override
	public void atualizar(Integer id, Produto produto) {
		try {
			produto.setId(id);
			valida(id, produto);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}

		super.atualizar(id, produto);
	}

	@Transactional
	public BigDecimal calcularPrecoMedio(EstoqueEntrada entradaEstoque) {
		Pageable pageable = PageRequest.of(0, 3, Sort.by("id").descending());

		List<EstoqueEntrada> estoqueEntrada = estoqueEntradaRepository
				.findByProduto(entradaEstoque.getProduto(), pageable).map(estoque -> estoque)
				.orElse(new ArrayList<EstoqueEntrada>());

		BigDecimal valorTotal = entradaEstoque.getPreco().multiply(entradaEstoque.getQuantidade());

		valorTotal.add(estoqueEntrada.stream().map(x -> x.getPreco().multiply(x.getQuantidade()))
				.reduce(BigDecimal.ZERO, BigDecimal::add));

		valorTotal.add(entradaEstoque.getPreco().multiply(entradaEstoque.getQuantidade()));

		BigDecimal quantidadeTotal = entradaEstoque.getQuantidade();

		quantidadeTotal
				.add(estoqueEntrada.stream().map(x -> x.getQuantidade()).reduce(BigDecimal.ZERO, BigDecimal::add));

		quantidadeTotal.add(entradaEstoque.getQuantidade());

		BigDecimal precoMedio = BigDecimal.ZERO;

		precoMedio = valorTotal.divide(quantidadeTotal, 2, RoundingMode.HALF_UP);

		return precoMedio;
	}

	@Transactional
	public void atualizarPrecoMedio(EstoqueEntrada entradaEstoque) {
		BigDecimal precoMedio = calcularPrecoMedio(entradaEstoque);
		Produto produto = entradaEstoque.getProduto();

		produto.setPrecoMedio(precoMedio);

		super.atualizar(produto.getId(), produto);
	}

	private void valida(Integer id, Produto produto) {
		if (produto.getQuantidadeMinima().compareTo(produto.getQuantidadeMaxima()) == 1) {
			throw new ProdutoQtdeMinMaiorMax();
		}

		String codigo = produto.getCodigo();

		boolean exists = repository.existsByIdNotAndCodigo(id == null ? 0 : id, codigo);

		if (exists) {
			throw new UniqueException(entidade);
		}
	}
}