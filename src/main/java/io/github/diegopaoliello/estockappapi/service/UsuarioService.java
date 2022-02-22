package io.github.diegopaoliello.estockappapi.service;

import io.github.diegopaoliello.estockappapi.exception.UsuarioCadastradoException;
import io.github.diegopaoliello.estockappapi.model.entity.Usuario;
import io.github.diegopaoliello.estockappapi.model.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UsuarioService implements UserDetailsService {

	@Autowired
	private UsuarioRepository repository;

	public Usuario salvar(Usuario usuario) {
		boolean exists = repository.existsByUsername(usuario.getUsername());
		if (exists) {
			throw new UsuarioCadastradoException(usuario.getUsername());
		}
		return repository.save(usuario);
	}

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		Usuario usuario = repository.findByUsername(userName)
				.orElseThrow(() -> new UsernameNotFoundException("Login não encontrado."));

		return User.builder().username(usuario.getUsername()).password(usuario.getPassword()).roles("USER").build();
	}

	public Usuario findByUsername(String userName) {
		Usuario usuario = repository.findByUsername(userName)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Login não encontrado!"));

		return usuario;
	}

	public Usuario findAuthenticatedUser() {
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		Usuario usuario = findByUsername(userName);

		return usuario;
	}
}