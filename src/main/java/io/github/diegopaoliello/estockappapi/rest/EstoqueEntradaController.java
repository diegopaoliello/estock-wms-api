package io.github.diegopaoliello.estockappapi.rest;

import io.github.diegopaoliello.estockappapi.model.entity.EstoqueEntrada;
import io.github.diegopaoliello.estockappapi.service.EstoqueEntradaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/entradas-estoque")
public class EstoqueEntradaController {

	@Autowired
	private EstoqueEntradaService service;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EstoqueEntrada salvar(@RequestBody @Validated EstoqueEntrada entrada) {
		return service.salvar(entrada);
	}

	@GetMapping
	public List<EstoqueEntrada> listar() {
		return service.listar();
	}
}
