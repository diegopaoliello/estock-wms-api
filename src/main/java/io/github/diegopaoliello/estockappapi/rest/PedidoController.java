package io.github.diegopaoliello.estockappapi.rest;

import io.github.diegopaoliello.estockappapi.model.entity.PedidoStatus;
import io.github.diegopaoliello.estockappapi.model.entity.BeforeValidInfo;
import io.github.diegopaoliello.estockappapi.model.entity.Pedido;
import io.github.diegopaoliello.estockappapi.service.PedidoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

	@Autowired
	private PedidoService service;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Pedido salvar(@RequestBody @Validated(BeforeValidInfo.class) Pedido pedido) {
		return service.salvar(pedido);
	}

	@PutMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizar(@PathVariable Integer id,
			@RequestBody @Validated(BeforeValidInfo.class) Pedido pedidoAtualizado) {
		service.atualizar(id, pedidoAtualizado);
	}

	@DeleteMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable Integer id) {
		service.deletar(id);
	}

	@GetMapping("{id}")
	public Pedido acharPorId(@PathVariable Integer id) {
		return service.acharPorId(id);
	}

	@GetMapping
	public List<Pedido> listar() {
		return service.listar();
	}

	@PatchMapping("{id}/aprovar")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void aprovar(@PathVariable Integer id) {
		service.atualizarStatus(id, PedidoStatus.APROVADO);
	}

	@PatchMapping("{id}/reprovar")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void reprovar(@PathVariable Integer id) {
		service.atualizarStatus(id, PedidoStatus.REPROVADO);
	}

	@PatchMapping("{id}/concluir")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void concluir(@PathVariable Integer id) {
		service.atualizarStatus(id, PedidoStatus.CONCLUIDO);
	}
}
