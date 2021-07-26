package io.github.diegopaoliello.estockappapi.rest;

import io.github.diegopaoliello.estockappapi.model.entity.UnidadeMedida;
import io.github.diegopaoliello.estockappapi.model.repository.UnidadeMedidaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/unidade-medida")
public class UnidadaMedidaController {

	private final UnidadeMedidaRepository repository;

	@Autowired
	public UnidadaMedidaController(UnidadeMedidaRepository repository) {
		this.repository = repository;
	}

	@GetMapping
	public List<UnidadeMedida> obterTodos() {
		return repository.findAll();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public UnidadeMedida salvar(@RequestBody @Valid UnidadeMedida unidadeMedida) {
		return repository.save(unidadeMedida);
	}

	@GetMapping("{id}")
	public UnidadeMedida acharPorId(@PathVariable Integer id) {
		return repository.findById(id).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unidade de medida não encontrada"));
	}

	@DeleteMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable Integer id) {
		repository.findById(id).map(unidadeMedida -> {
			repository.delete(unidadeMedida);
			return Void.TYPE;
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unidade de medida não encontrada"));
	}

	@PutMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizar(@PathVariable Integer id, @RequestBody @Valid UnidadeMedida unidadeMedidaNew) {
		repository.findById(id).map(unidadeMedidaOld -> {
			unidadeMedidaOld.setDescricao(unidadeMedidaNew.getDescricao());
			unidadeMedidaOld.setSigla(unidadeMedidaNew.getSigla());
			return repository.save(unidadeMedidaOld);
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unidade de medida não encontrada"));
	}
}
