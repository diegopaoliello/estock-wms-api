package io.github.diegopaoliello.estockappapi;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.server.ResponseStatusException;

import io.github.diegopaoliello.estockappapi.model.entity.UnidadeMedida;
import io.github.diegopaoliello.estockappapi.service.UnidadeMedidaService;

class CadastroTest extends AbstractTest {
	@Autowired
	private UnidadeMedidaService unidadeMedidaService;

	@Test()
	void unidadeMedidaDuplicadaException() {
		ResponseStatusException thrown = Assertions.assertThrows(ResponseStatusException.class, () -> {
			unidadeMedidaService.salvar(new UnidadeMedida("KG", "Kilograma"));
			unidadeMedidaService.salvar(new UnidadeMedida("KG", "Kilograma"));

		}, "ResponseStatusException deve ocorrer");

		Assertions.assertTrue(thrown.getMessage().contains("Unidade de Medida já existente"));
	}
}
