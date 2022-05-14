package io.github.diegopaoliello.estockappapi.rest;

import io.github.diegopaoliello.estockappapi.model.entity.EstoqueEntrada;
import io.github.diegopaoliello.estockappapi.rest.dto.EstoqueEntradaManualDTO;
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
	public EstoqueEntrada salvar(@RequestBody @Validated EstoqueEntradaManualDTO entradaManualEstoque) {
		EstoqueEntrada entradaEstoque = new EstoqueEntrada();
		entradaEstoque.setProduto(entradaManualEstoque.getProduto());
		entradaEstoque.setQuantidade(entradaManualEstoque.getQuantidade());
		entradaEstoque.setPreco(entradaManualEstoque.getPreco());
		entradaEstoque.setJustificativa(entradaManualEstoque.getJustificativa());

		return service.salvar(entradaEstoque);
	}

	@GetMapping
	public List<EstoqueEntrada> listar() {
		return service.listar();
	}

	@GetMapping("{id}")
	public EstoqueEntrada acharPorId(@PathVariable Integer id) {
		return service.acharPorId(id);
	}
}
