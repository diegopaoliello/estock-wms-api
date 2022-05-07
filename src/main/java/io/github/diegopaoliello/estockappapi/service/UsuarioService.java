package io.github.diegopaoliello.estockappapi.service;

import io.github.diegopaoliello.estockappapi.exception.UsuarioAutenticadoException;
import io.github.diegopaoliello.estockappapi.exception.UniqueException;
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

	private static String entidade = "Usuário";

	public Usuario salvar(Usuario usuario) {
		try {
			String userName = usuario.getUsername();
			boolean exists = repository.existsByUsername(userName);
			if (exists) {
				throw new UniqueException(entidade);
			}
		} catch (UniqueException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}

		return repository.save(usuario);
	}

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		Usuario usuario = repository.findByUsername(userName)
				.orElseThrow(() -> new UsernameNotFoundException(entidade + " não encontrado"));

		return User.builder().username(usuario.getUsername()).password(usuario.getSenha()).roles("USER").build();
	}

	public Usuario acharPorId(Integer id) {
		return repository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, entidade + " não encontrado"));
	}

	public Usuario acharPorUserName(String userName) {
		return repository.findByUsername(userName)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, entidade + " não encontrado"));
	}

	public void existePorNome(String userName) {
		if (acharPorUserName(userName) == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, entidade + " não encontrado");
		} else {
			throw new ResponseStatusException(HttpStatus.NO_CONTENT, entidade + " encontrado");
		}
	}

	public Usuario acharUsuarioAutenticado(Integer id) {
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();

		try {
			if (userName == null || userName.equals("anonymousUser")) {
				throw new UsuarioAutenticadoException();
			}
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}

		return acharPorId(id);
	}

	public Usuario acharUsuarioAutenticado() {
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();

		try {
			if (userName == null || userName.equals("anonymousUser")) {
				throw new UsuarioAutenticadoException();
			}
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}

		return acharPorUserName(userName);
	}
}