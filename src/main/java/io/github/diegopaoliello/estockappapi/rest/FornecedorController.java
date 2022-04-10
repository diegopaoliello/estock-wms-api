package io.github.diegopaoliello.estockappapi.rest;

import io.github.diegopaoliello.estockappapi.model.entity.Fornecedor;
import io.github.diegopaoliello.estockappapi.service.FornecedorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fornecedores")
public class FornecedorController {

	@Autowired
	private FornecedorService service;

	@GetMapping
	public List<Fornecedor> listar() {
		return service.listar();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Fornecedor salvar(@RequestBody @Validated Fornecedor fornecedor) {
		return service.salvar(fornecedor);
	}

	@GetMapping("{id}")
	public Fornecedor acharPorId(@PathVariable Integer id) {
		return service.acharPorId(id);
	}

	@DeleteMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable Integer id) {
		service.deletar(id);
	}

	@PutMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizar(@PathVariable Integer id, @RequestBody @Validated Fornecedor fornecedorNew) {
		service.atualizar(id, fornecedorNew);
	}
}
