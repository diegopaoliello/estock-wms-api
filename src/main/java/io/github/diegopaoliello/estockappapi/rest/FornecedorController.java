package io.github.diegopaoliello.estockappapi.rest;

import io.github.diegopaoliello.estockappapi.model.entity.Fornecedor;
import io.github.diegopaoliello.estockappapi.model.repository.FornecedorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/fornecedor")
public class FornecedorController {

	private final FornecedorRepository repository;

	@Autowired
	public FornecedorController(FornecedorRepository repository) {
		this.repository = repository;
	}

	@GetMapping
	public List<Fornecedor> obterTodos() {
		return repository.findAll();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Fornecedor salvar(@RequestBody @Valid Fornecedor fornecedor) {
		return repository.save(fornecedor);
	}

	@GetMapping("{id}")
	public Fornecedor acharPorId(@PathVariable Integer id) {
		return repository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Fornecedor não encontrado"));
	}

	@DeleteMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable Integer id) {
		repository.findById(id).map(fornecedor -> {
			repository.delete(fornecedor);
			return Void.TYPE;
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Fornecedor não encontrado"));
	}

	@PutMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizar(@PathVariable Integer id, @RequestBody @Valid Fornecedor fornecedorNew) {
		repository.findById(id).map(fornecedorOld -> {
			fornecedorOld.setRazaoSocial(fornecedorNew.getRazaoSocial());
			fornecedorOld.setNomeFantasia(fornecedorNew.getNomeFantasia());
			fornecedorOld.setCnpj(fornecedorNew.getCnpj());
			return repository.save(fornecedorOld);
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Fornecedor não encontrado"));
	}
}
