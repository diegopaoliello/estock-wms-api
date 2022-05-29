package io.github.diegopaoliello.estockappapi.rest;

import io.github.diegopaoliello.estockappapi.model.entity.BeforeValidInfo;
import io.github.diegopaoliello.estockappapi.model.entity.Pedido;
import io.github.diegopaoliello.estockappapi.model.entity.PedidoItem;
import io.github.diegopaoliello.estockappapi.service.PedidoItemService;
import io.github.diegopaoliello.estockappapi.service.PedidoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos/{idPedido}/itens")
public class PedidoItemController {

	@Autowired
	private PedidoItemService service;

	@Autowired
	private PedidoService pedidoService;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@Secured({ "ADMINISTRADOR", "GERENTE" })
	public PedidoItem salvar(@PathVariable(value = "idPedido") Integer idPedido,
			@RequestBody @Validated(BeforeValidInfo.class) PedidoItem itemPedido) {
		Pedido pedido = pedidoService.acharPorId(idPedido);
		itemPedido.setPedido(pedido);

		return service.salvar(itemPedido);
	}

	@PutMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@Secured({ "ADMINISTRADOR", "GERENTE" })
	public void atualizar(@PathVariable(value = "id") Integer id,
			@RequestBody @Validated(BeforeValidInfo.class) PedidoItem itemPedido,
			@PathVariable(value = "idPedido") Integer idPedido) {
		Pedido pedido = pedidoService.acharPorId(idPedido);
		itemPedido.setId(id);
		itemPedido.setPedido(pedido);

		service.atualizar(id, itemPedido);
	}

	@DeleteMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@Secured({ "ADMINISTRADOR", "GERENTE" })
	public void deletar(@PathVariable(value = "id") Integer id) {
		service.deletar(id);
	}

	@GetMapping("{id}")
	@Secured({ "ADMINISTRADOR", "GERENTE" })
	public PedidoItem acharPorId(@PathVariable(value = "id") Integer id) {
		return service.acharPorId(id);
	}

	@GetMapping
	@Secured({ "ADMINISTRADOR", "GERENTE", "OPERADOR" })
	public List<PedidoItem> listar(@PathVariable(name = "idPedido") Integer idPedido) {
		return service.listar(idPedido);
	}
}
