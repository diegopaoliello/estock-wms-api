package io.github.diegopaoliello.estockappapi.rest;

import io.github.diegopaoliello.estockappapi.model.entity.VendaStatus;
import io.github.diegopaoliello.estockappapi.model.entity.BeforeValidInfo;
import io.github.diegopaoliello.estockappapi.model.entity.Venda;
import io.github.diegopaoliello.estockappapi.service.VendaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
	public Venda salvar(@RequestBody @Validated(BeforeValidInfo.class) Venda venda) {
		return service.salvar(venda);
	}

	@PutMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizar(@PathVariable Integer id,
			@RequestBody @Validated(BeforeValidInfo.class) Venda vendaAtualizado) {
		service.atualizar(id, vendaAtualizado);
	}

	@DeleteMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable Integer id) {
		service.deletar(id);
	}

	@GetMapping("{id}")
	public Venda acharPorId(@PathVariable Integer id) {
		return service.acharPorId(id);
	}

	@GetMapping
	public List<Venda> listar() {
		return service.listar();
	}

	@PatchMapping("{id}/aprovar")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void aprovar(@PathVariable Integer id) {
		service.atualizarStatus(id, VendaStatus.APROVADO);
	}

	@PatchMapping("{id}/reprovar")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void reprovar(@PathVariable Integer id) {
		service.atualizarStatus(id, VendaStatus.REPROVADO);
	}

	@PatchMapping("{id}/concluir")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void concluir(@PathVariable Integer id) {
		service.atualizarStatus(id, VendaStatus.CONCLUIDO);
	}
}
