package io.github.diegopaoliello.estockappapi.rest;

import io.github.diegopaoliello.estockappapi.model.entity.PedidoItem;
import io.github.diegopaoliello.estockappapi.service.PedidoItemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/pedidos/{idPedido}/itens-pedido")
public class PedidoItemController {

	@Autowired
	private PedidoItemService service;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PedidoItem salvar(@RequestBody @Valid PedidoItem itemPedido) {
		return service.salvar(itemPedido);
	}

	@PutMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizar(@PathVariable Integer id, @RequestBody @Valid PedidoItem itemPedido) {
		service.atualizar(id, itemPedido);
	}

	@DeleteMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable Integer id) {
		service.deletar(id);
	}
	

	@GetMapping("{id}")
	public PedidoItem acharPorId(@PathVariable Integer id) {
		return service.acharPorId(id);
	}

	@GetMapping
	public List<PedidoItem> listar(@RequestParam(value = "idPedido", required = false) Integer idPedido) {
		return service.listar(idPedido);
	}
}
