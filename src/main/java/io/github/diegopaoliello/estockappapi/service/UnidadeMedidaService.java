package io.github.diegopaoliello.estockappapi.service;

import io.github.diegopaoliello.estockappapi.exception.UniqueException;
import io.github.diegopaoliello.estockappapi.model.entity.UnidadeMedida;
import io.github.diegopaoliello.estockappapi.model.repository.UnidadeMedidaRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UnidadeMedidaService extends AbstractService<UnidadeMedida, UnidadeMedidaRepository> {
	private static String entidade = "Unidade de Medida";

	public UnidadeMedidaService() {
		super(entidade);
	}

	@Override
	public UnidadeMedida salvar(UnidadeMedida unidadeMedida) {
		try {
			valida(null, unidadeMedida);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}

		return super.salvar(unidadeMedida);
	}

	@Override
	public void atualizar(Integer id, UnidadeMedida unidadeMedida) {
		try {
			valida(id, unidadeMedida);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}

		super.atualizar(id, unidadeMedida);
	}

	private void valida(Integer id, UnidadeMedida unidadeMedida) {
		String sigla = unidadeMedida.getSigla();

		boolean exists = repository.existsByIdNotAndSiglaIgnoreCase(id == null ? 0 : id, sigla);

		if (exists) {
			throw new UniqueException(entidade);
		}
	}
}