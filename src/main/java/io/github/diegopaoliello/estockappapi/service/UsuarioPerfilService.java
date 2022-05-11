package io.github.diegopaoliello.estockappapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.diegopaoliello.estockappapi.model.entity.UsuarioPerfil;
import io.github.diegopaoliello.estockappapi.model.repository.UsuarioPerfilRepository;

@Service
public class UsuarioPerfilService {
	@Autowired
	private UsuarioPerfilRepository repository;

	public List<UsuarioPerfil> listar() {
		return repository.findAll();
	}
}