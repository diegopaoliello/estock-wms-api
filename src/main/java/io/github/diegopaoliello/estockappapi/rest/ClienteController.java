package io.github.diegopaoliello.estockappapi.rest;

import io.github.diegopaoliello.estockappapi.model.entity.Cliente;
import io.github.diegopaoliello.estockappapi.service.ClienteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

	@Autowired
	private ClienteService service;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente salvar(@RequestBody @Valid Cliente cliente) {
		return service.salvar(cliente);
	}

	@DeleteMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable Integer id) {
		service.deletar(id);
	}

	@PutMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizar(@PathVariable Integer id, @RequestBody @Valid Cliente cliente) {
		service.atualizar(id, cliente);
	}

	@GetMapping("{id}")
	public Cliente acharPorId(@PathVariable Integer id) {
		return service.acharPorId(id);
	}

	@GetMapping
	public List<Cliente> listar() {
		return service.listar();
	}
}
