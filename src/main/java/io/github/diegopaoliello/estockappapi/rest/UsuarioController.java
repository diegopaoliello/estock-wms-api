package io.github.diegopaoliello.estockappapi.rest;

import io.github.diegopaoliello.estockappapi.model.entity.Usuario;
import io.github.diegopaoliello.estockappapi.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

	private final UsuarioService service;

	@PutMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizar(@RequestBody @Validated Usuario usuario) {
		service.atualizar(usuario);
	}

	@GetMapping
	public Usuario acharUsuario() {
		return service.acharUsuarioAutenticado();
	}
}