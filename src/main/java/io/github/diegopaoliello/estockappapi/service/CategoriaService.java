package io.github.diegopaoliello.estockappapi.service;

import io.github.diegopaoliello.estockappapi.model.entity.Categoria;
import io.github.diegopaoliello.estockappapi.model.repository.CategoriaRepository;

import org.springframework.stereotype.Service;

@Service
public class CategoriaService extends AbstractService<Categoria, CategoriaRepository> {

	public CategoriaService() {
		super(Categoria.class);
	}
}