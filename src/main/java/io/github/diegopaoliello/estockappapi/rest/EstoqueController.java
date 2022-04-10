package io.github.diegopaoliello.estockappapi.rest;

import io.github.diegopaoliello.estockappapi.model.entity.Estoque;
import io.github.diegopaoliello.estockappapi.service.EstoqueService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/estoques")
public class EstoqueController {
	@Autowired
	private EstoqueService service;

	@GetMapping
	public List<Estoque> estoque(@RequestParam(value = "idProduto", required = false) Integer idProduto,
			@RequestParam(value = "quantidade", required = false) BigDecimal quantidade) {
		return service.listar(idProduto, quantidade);
	}
}
