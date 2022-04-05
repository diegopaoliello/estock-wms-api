package io.github.diegopaoliello.estockappapi.rest;

import io.github.diegopaoliello.estockappapi.model.entity.PedidoStatus;
import io.github.diegopaoliello.estockappapi.model.entity.Pedido;
import io.github.diegopaoliello.estockappapi.rest.dto.PedidoDTO;
import io.github.diegopaoliello.estockappapi.service.PedidoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

	@Autowired
	private PedidoService service;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Pedido salvar(@RequestBody @Valid PedidoDTO pedidoDto) {
		Pedido pedido = new Pedido();

		try {
			pedido.setFornecedor(pedidoDto.getFornecedor());

			service.salvar(pedido);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}

		return pedido;
	}

	@PutMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizar(@PathVariable Integer id, @RequestBody @Valid PedidoDTO pedidoAtualizado) {
		try {
			Pedido pedido = new Pedido();

			pedido.setFornecedor(pedidoAtualizado.getFornecedor());

			service.atualizar(id, pedido);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@DeleteMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable Integer id) {
		try {
			service.deletar(id);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping("{id}")
	public Pedido acharPorId(@PathVariable Integer id) {
		try {
			service.acharPorId(id);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}

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
