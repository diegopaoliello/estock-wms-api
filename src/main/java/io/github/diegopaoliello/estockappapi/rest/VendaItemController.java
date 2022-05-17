package io.github.diegopaoliello.estockappapi.rest;

import io.github.diegopaoliello.estockappapi.model.entity.BeforeValidInfo;
import io.github.diegopaoliello.estockappapi.model.entity.Venda;
import io.github.diegopaoliello.estockappapi.model.entity.VendaItem;
import io.github.diegopaoliello.estockappapi.service.VendaItemService;
import io.github.diegopaoliello.estockappapi.service.VendaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vendas/{idVenda}/itens")
public class VendaItemController {

	@Autowired
	private VendaItemService service;

	@Autowired
	private VendaService vendaService;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public VendaItem salvar(@PathVariable(value = "idVenda") Integer idVenda,
			@RequestBody @Validated(BeforeValidInfo.class) VendaItem itemVenda) {
		Venda venda = vendaService.acharPorId(idVenda);
		itemVenda.setVenda(venda);

		return service.salvar(itemVenda);
	}

	@PutMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizar(@PathVariable(value = "id") Integer id,
			@RequestBody @Validated(BeforeValidInfo.class) VendaItem itemVenda,
			@PathVariable(value = "idVenda") Integer idVenda) {
		Venda venda = vendaService.acharPorId(idVenda);
		itemVenda.setId(id);
		itemVenda.setVenda(venda);
		
		service.atualizar(id, itemVenda);
	}

	@DeleteMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable(value = "id") Integer id) {
		service.deletar(id);
	}

	@GetMapping("{id}")
	public VendaItem acharPorId(@PathVariable(value = "id") Integer id) {
		return service.acharPorId(id);
	}

	@GetMapping
	public List<VendaItem> listar(@PathVariable(value = "idVenda") Integer idVenda) {
		return service.listar(idVenda);
	}
}
