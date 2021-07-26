package io.github.diegopaoliello.estockappapi.rest;

import io.github.diegopaoliello.estockappapi.model.entity.Produto;
import io.github.diegopaoliello.estockappapi.model.repository.ProdutoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

	private final ProdutoRepository repository;

	@Autowired
	public ProdutoController(ProdutoRepository repository) {
		this.repository = repository;
	}

	@GetMapping
	public List<Produto> obterTodos() {
		return repository.findAll();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Produto salvar(@RequestBody @Valid Produto produto) {
		return repository.save(produto);
	}

	@GetMapping("{id}")
	public Produto acharPorId(@PathVariable Integer id) {
		return repository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));
	}

	@DeleteMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable Integer id) {
		repository.findById(id).map(produto -> {
			repository.delete(produto);
			return Void.TYPE;
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));
	}

	@PutMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizar(@PathVariable Integer id, @RequestBody @Valid Produto produtoNew) {
		repository.findById(id).map(produtoOld -> {
			produtoOld.setCategoria(produtoNew.getCategoria());
			produtoOld.setCodigo(produtoNew.getCodigo());
			produtoOld.setDescricao(produtoNew.getDescricao());
			produtoOld.setUnidadeMedida(produtoNew.getUnidadeMedida());
			return repository.save(produtoOld);
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));
	}
}
