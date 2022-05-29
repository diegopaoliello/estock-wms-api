package io.github.diegopaoliello.estockappapi.rest;

import io.github.diegopaoliello.estockappapi.model.entity.BeforeValidInfo;
import io.github.diegopaoliello.estockappapi.model.entity.Venda;
import io.github.diegopaoliello.estockappapi.service.VendaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vendas")
public class VendaController {

	@Autowired
	private VendaService service;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@Secured({ "ADMINISTRADOR", "GERENTE" })
	public Venda salvar(@RequestBody @Validated(BeforeValidInfo.class) Venda venda) {
		return service.salvar(venda);
	}

	@PutMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@Secured({ "ADMINISTRADOR", "GERENTE" })
	public void atualizar(@PathVariable Integer id,
			@RequestBody @Validated(BeforeValidInfo.class) Venda vendaAtualizado) {
		service.atualizar(id, vendaAtualizado);
	}

	@DeleteMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@Secured({ "ADMINISTRADOR", "GERENTE" })
	public void deletar(@PathVariable Integer id) {
		service.deletar(id);
	}

	@GetMapping("{id}")
	@Secured({ "ADMINISTRADOR", "GERENTE", "OPERADOR" })
	public Venda acharPorId(@PathVariable Integer id) {
		return service.acharPorId(id);
	}

	@GetMapping
	@Secured({ "ADMINISTRADOR", "GERENTE", "OPERADOR" })
	public List<Venda> listar() {
		return service.listar();
	}

	@PatchMapping("{id}/aprovar")
	@ResponseStatus(HttpStatus.ACCEPTED)
	@Secured({ "GERENTE" })
	public void aprovar(@PathVariable Integer id) {
		service.atualizarStatus(id, "APROVADO");
	}

	@PatchMapping("{id}/reprovar")
	@ResponseStatus(HttpStatus.ACCEPTED)
	@Secured({ "GERENTE" })
	public void reprovar(@PathVariable Integer id) {
		service.atualizarStatus(id, "REPROVADO");
	}

	@PatchMapping("{id}/concluir")
	@ResponseStatus(HttpStatus.ACCEPTED)
	@Secured({ "ADMINISTRADOR", "GERENTE", "OPERADOR" })
	public void concluir(@PathVariable Integer id) {
		service.atualizarStatus(id, "CONCLUIDO");
	}
}
