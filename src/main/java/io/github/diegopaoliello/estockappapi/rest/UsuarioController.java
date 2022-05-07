package io.github.diegopaoliello.estockappapi.rest;

import io.github.diegopaoliello.estockappapi.exception.UniqueException;
import io.github.diegopaoliello.estockappapi.model.entity.Usuario;
import io.github.diegopaoliello.estockappapi.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

	private final UsuarioService service;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void salvar(@RequestBody @Validated Usuario usuario) {
		try {
			service.salvar(usuario);
		} catch (UniqueException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping("{idOrUserName}")
	public Usuario acharUsuario(@PathVariable String idOrUserName) {
		Usuario usuario = new Usuario();

		try {
			usuario = service.acharUsuarioAutenticado(Integer.parseInt(idOrUserName));
		} catch (NumberFormatException e) {
			System.out.println(e);
			service.existePorNome(idOrUserName);
		}

		return usuario;
	}
}