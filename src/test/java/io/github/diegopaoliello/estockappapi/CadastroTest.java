package io.github.diegopaoliello.estockappapi;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import io.github.diegopaoliello.estockappapi.model.entity.UnidadeMedida;
import io.github.diegopaoliello.estockappapi.service.UnidadeMedidaService;

@SpringBootTest
@Transactional
class CadastroTest {
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
	
	@Test()
	void unidadeMedidaDuplicadaDoisException() {
		ResponseStatusException thrown = Assertions.assertThrows(ResponseStatusException.class, () -> {
			unidadeMedidaService.salvar(new UnidadeMedida("KG", "Kilograma"));
			unidadeMedidaService.salvar(new UnidadeMedida("KG", "Kilograma"));

		}, "ResponseStatusException deve ocorrer");

		Assertions.assertTrue(thrown.getMessage().contains("Unidade de Medida já existente"));
	}
}
