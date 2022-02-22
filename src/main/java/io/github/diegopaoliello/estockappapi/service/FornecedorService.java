package io.github.diegopaoliello.estockappapi.service;

import io.github.diegopaoliello.estockappapi.model.entity.Fornecedor;
import io.github.diegopaoliello.estockappapi.model.repository.FornecedorRepository;

import org.springframework.stereotype.Service;

@Service
public class FornecedorService extends AbstractService<Fornecedor, FornecedorRepository> {
	public FornecedorService() {
		super(Fornecedor.class);
	}
}