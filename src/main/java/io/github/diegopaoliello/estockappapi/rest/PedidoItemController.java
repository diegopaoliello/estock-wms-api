package io.github.diegopaoliello.estockappapi.rest;

import io.github.diegopaoliello.estockappapi.model.entity.BeforeValidInfo;
import io.github.diegopaoliello.estockappapi.model.entity.Pedido;
import io.github.diegopaoliello.estockappapi.model.entity.PedidoItem;
import io.github.diegopaoliello.estockappapi.service.PedidoItemService;
import io.github.diegopaoliello.estockappapi.service.PedidoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos/{idPedido}/itens-pedido")
public class PedidoItemController {

	@Autowired
	private PedidoItemService service;

	@Autowired
	private PedidoService pedidoService;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PedidoItem salvar(@PathVariable(value = "idPedido") Integer idPedido,
			@RequestBody @Validated(BeforeValidInfo.class) PedidoItem itemPedido) {
		Pedido pedido = pedidoService.acharPorId(idPedido);
		itemPedido.setPedido(pedido);

		return service.salvar(itemPedido);
	}

	@PutMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizar(@PathVariable Integer id,
			@RequestBody @Validated(BeforeValidInfo.class) PedidoItem itemPedido) {
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
	public List<PedidoItem> listar(@PathVariable(value = "idPedido") Integer idPedido) {
		return service.listar(idPedido);
	}
}
