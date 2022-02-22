package io.github.diegopaoliello.estockappapi.service;

import io.github.diegopaoliello.estockappapi.model.entity.Produto;
import io.github.diegopaoliello.estockappapi.model.repository.ProdutoRepository;

import org.springframework.stereotype.Service;

@Service
public class ProdutoService extends AbstractService<Produto, ProdutoRepository> {
	public ProdutoService() {
		super(Produto.class);
	}
}