package io.github.diegopaoliello.estockappapi.rest;

import io.github.diegopaoliello.estockappapi.model.entity.Produto;

import io.github.diegopaoliello.estockappapi.service.ProdutoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

	@Autowired
	private ProdutoService service;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Produto salvar(@RequestBody @Validated Produto produto) {
		return service.salvar(produto);
	}

	@PutMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizar(@PathVariable Integer id, @RequestBody @Validated Produto produto) {
		service.atualizar(id, produto);
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

	@GetMapping("{idProduto}/calcularPrecoMedio")
	public BigDecimal calcularPrecoMedio(@PathVariable(value = "idProduto") Integer idProduto) {
		return service.calcularPrecoMedio(idProduto);
	}
}
