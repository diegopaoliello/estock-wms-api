package io.github.diegopaoliello.estockappapi.rest;

import io.github.diegopaoliello.estockappapi.model.entity.UsuarioPerfil;
import io.github.diegopaoliello.estockappapi.service.UsuarioPerfilService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/perfis")
public class UsuarioPerfilController {

	@Autowired
	private UsuarioPerfilService service;

	@GetMapping
	public List<UsuarioPerfil> listar() {
		return service.listar();
	}
}
