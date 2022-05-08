package io.github.diegopaoliello.estockappapi.service;

import io.github.diegopaoliello.estockappapi.exception.UsuarioAutenticadoException;
import io.github.diegopaoliello.estockappapi.exception.UsuarioDiferenteDoAutenticadoException;
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
			String email = usuario.getEmail();
			boolean exists = repository.existsByEmail(email);
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
		Usuario usuario = acharPorEmail(userName);

		return User.builder().username(usuario.getEmail()).password(usuario.getSenha()).roles("USER").build();
	}

	public Usuario acharPorId(Integer id) {
		return repository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, entidade + " não encontrado"));
	}

	public Usuario acharPorEmail(String email) {
		return repository.findByEmail(email)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, entidade + " não encontrado"));
	}

	public void existePorNome(String userName) {
		if (acharPorEmail(userName) == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, entidade + " não encontrado");
		} else {
			throw new ResponseStatusException(HttpStatus.NO_CONTENT, entidade + " encontrado");
		}
	}

	public Usuario acharUsuarioAutenticado(Integer id) {
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		Usuario usuarioLogado = null;
		try {
			if (userName != null && !userName.equals("anonymousUser")) {
				usuarioLogado = acharPorEmail(userName);
			} else {
				throw new UsuarioAutenticadoException();
			}

			if (usuarioLogado != null && !usuarioLogado.getId().equals(id)) {
				throw new UsuarioDiferenteDoAutenticadoException();
			}
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}

		return acharPorId(id);
	}

	public Usuario acharUsuarioAutenticado() {
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		return acharPorEmail(userName);
	}
}