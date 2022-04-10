package io.github.diegopaoliello.estockappapi.rest;

import io.github.diegopaoliello.estockappapi.model.entity.Categoria;
import io.github.diegopaoliello.estockappapi.service.CategoriaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

	@Autowired
	private CategoriaService service;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Categoria salvar(@RequestBody @Validated Categoria categoria) {
		return service.salvar(categoria);
	}

	@DeleteMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable Integer id) {
		service.deletar(id);
	}

	@PutMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizar(@PathVariable Integer id, @RequestBody @Validated Categoria categoria) {
		service.atualizar(id, categoria);
	}

	@GetMapping("{id}")
	public Categoria acharPorId(@PathVariable Integer id) {
		return service.acharPorId(id);
	}

	@GetMapping
	public List<Categoria> listar() {
		return service.listar();
	}
}
