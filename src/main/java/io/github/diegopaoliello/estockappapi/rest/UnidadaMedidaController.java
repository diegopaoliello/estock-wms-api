package io.github.diegopaoliello.estockappapi.rest;

import io.github.diegopaoliello.estockappapi.model.entity.UnidadeMedida;
import io.github.diegopaoliello.estockappapi.service.UnidadeMedidaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/unidades-medida")
public class UnidadaMedidaController {

	@Autowired
	private UnidadeMedidaService service;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public UnidadeMedida salvar(@RequestBody @Validated UnidadeMedida unidadeMedida) {
		return service.salvar(unidadeMedida);
	}

	@PutMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizar(@PathVariable Integer id, @RequestBody @Validated UnidadeMedida unidadeMedida) {

		service.atualizar(id, unidadeMedida);
	}

	@DeleteMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable Integer id) {
		service.deletar(id);
	}

	@GetMapping("{id}")
	public UnidadeMedida acharPorId(@PathVariable Integer id) {
		return service.acharPorId(id);
	}

	@GetMapping
	public List<UnidadeMedida> listar() {
		return service.listar();
	}
}
