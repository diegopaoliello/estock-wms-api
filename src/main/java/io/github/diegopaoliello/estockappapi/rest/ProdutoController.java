package io.github.diegopaoliello.estockappapi.rest;

import io.github.diegopaoliello.estockappapi.model.entity.Produto;

import io.github.diegopaoliello.estockappapi.service.ProdutoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

	@Autowired
	private ProdutoService service;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Produto salvar(@RequestBody @Valid Produto produto) {
		return service.salvar(produto);
	}

	@PutMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizar(@PathVariable Integer id, @RequestBody @Valid Produto produto) {
		service.salvar(produto);
	}

	@DeleteMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable Integer id) {
		service.deletar(id);
	}

	@GetMapping
	public List<Produto> listar() {
		return service.listar();
	}

	@GetMapping("{id}")
	public Produto acharPorId(@PathVariable Integer id) {
		return service.acharPorId(id);
	}
}
