package io.github.diegopaoliello.estockappapi.service;

import io.github.diegopaoliello.estockappapi.model.entity.UnidadeMedida;
import io.github.diegopaoliello.estockappapi.model.repository.UnidadeMedidaRepository;

import org.springframework.stereotype.Service;

@Service
public class UnidadeMedidaService extends AbstractService<UnidadeMedida, UnidadeMedidaRepository> {
	public UnidadeMedidaService() {
		super(UnidadeMedida.class);
	}
}