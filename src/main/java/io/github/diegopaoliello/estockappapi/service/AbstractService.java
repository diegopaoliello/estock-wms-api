package io.github.diegopaoliello.estockappapi.service;

import io.github.diegopaoliello.estockappapi.model.entity.AbstractEntity;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

public abstract class AbstractService<T extends AbstractEntity, R extends JpaRepository<T, Integer>> {

	@Autowired
	protected R repository;

	@Autowired
	protected UsuarioService usuarioService;

	protected Class<? extends T> entityClass;

	private String nomeEntidade;

	public AbstractService(Class<? extends T> entityClass) {
		this.nomeEntidade = entityClass.getSimpleName();
	}

	public AbstractService(String nomeEntidade) {
		this.nomeEntidade = nomeEntidade;
	}

	public T salvar(T entity) {
		entity.setUsuario(usuarioService.acharUsuarioAutenticado());
		return repository.save(entity);
	}

	@Transactional
	public void atualizar(Integer id, T entityAtualizado) {
		repository.findById(id).map(entity -> {
			entity = entityAtualizado;
			entity.setId(id);
			entity.setUsuario(usuarioService.acharUsuarioAutenticado());
			return repository.save(entity);
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, this.nomeEntidade + " não encontrado"));
	}

	public void deletar(Integer id) {
		repository.findById(id).map(entity -> {
			repository.delete(entity);
			return Void.TYPE;
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, this.nomeEntidade + " não encontrado"));
	}

	public T acharPorId(Integer id) {
		return repository.findById(id).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NOT_FOUND, this.nomeEntidade + " não encontrado"));
	}

	public List<T> listar() {
		return repository.findAll();
	}
}