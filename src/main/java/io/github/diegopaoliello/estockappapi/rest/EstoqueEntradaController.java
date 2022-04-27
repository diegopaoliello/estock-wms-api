package io.github.diegopaoliello.estockappapi.rest;

import io.github.diegopaoliello.estockappapi.model.entity.EstoqueEntrada;
import io.github.diegopaoliello.estockappapi.service.EstoqueEntradaService;
import io.github.diegopaoliello.estockappapi.service.EstoqueEntradaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estoque-entrada")
public class EstoqueEntradaController {

	@Autowired
	private EstoqueEntradaService service;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EstoqueEntrada salvar(@RequestBody @Validated EstoqueEntrada categoria) {
		return service.salvar(categoria);
	}

	@DeleteMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable Integer id) {
		service.deletar(id);
	}

	@PutMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizar(@PathVariable Integer id, @RequestBody @Validated EstoqueEntrada categoria) {
		service.atualizar(id, categoria);
	}

	@GetMapping("{id}")
	public EstoqueEntrada acharPorId(@PathVariable Integer id) {
		return service.acharPorId(id);
	}

	@GetMapping
	public List<EstoqueEntrada> listar() {
		return service.listar();
	}
}
