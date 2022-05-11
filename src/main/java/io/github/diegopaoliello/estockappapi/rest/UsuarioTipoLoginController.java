package io.github.diegopaoliello.estockappapi.rest;

import io.github.diegopaoliello.estockappapi.model.entity.UsuarioTipoLogin;
import io.github.diegopaoliello.estockappapi.service.UsuarioTipoLoginService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tipos-login")
public class UsuarioTipoLoginController {

	@Autowired
	private UsuarioTipoLoginService service;

	@GetMapping
	public List<UsuarioTipoLogin> listar() {
		return service.listar();
	}
}
