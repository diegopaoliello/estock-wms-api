package io.github.diegopaoliello.estockappapi.rest;

import io.github.diegopaoliello.estockappapi.model.entity.EstoqueSaida;
import io.github.diegopaoliello.estockappapi.service.EstoqueSaidaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/saidas-estoque")
@Secured({ "ADMINISTRADOR", "GERENTE", "OPERADOR" })
public class EstoqueSaidaController {
	@Autowired
	private EstoqueSaidaService service;

	@GetMapping
	public List<EstoqueSaida> listar() {
		return service.listar();
	}

	@GetMapping("{id}")
	public EstoqueSaida acharPorId(@PathVariable Integer id) {
		return service.acharPorId(id);
	}
}
