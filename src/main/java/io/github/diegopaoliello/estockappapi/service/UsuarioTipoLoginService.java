package io.github.diegopaoliello.estockappapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.diegopaoliello.estockappapi.model.entity.UsuarioTipoLogin;
import io.github.diegopaoliello.estockappapi.model.repository.UsuarioTipoLoginRepository;

@Service
public class UsuarioTipoLoginService {
	@Autowired
	private UsuarioTipoLoginRepository repository;

	public List<UsuarioTipoLogin> listar() {
		return repository.findAll();
	}
}