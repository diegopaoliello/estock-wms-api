package io.github.diegopaoliello.estockappapi.service;

import io.github.diegopaoliello.estockappapi.exception.UsuarioAutenticadoException;
import io.github.diegopaoliello.estockappapi.exception.UsuarioDiferenteDoAutenticadoException;
import io.github.diegopaoliello.estockappapi.exception.UniqueException;
import io.github.diegopaoliello.estockappapi.model.entity.Usuario;
import io.github.diegopaoliello.estockappapi.model.entity.UsuarioPerfil;
import io.github.diegopaoliello.estockappapi.model.repository.UsuarioPerfilRepository;
import io.github.diegopaoliello.estockappapi.model.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UsuarioService implements UserDetailsService {
	@Autowired
	private UsuarioRepository repository;

	@Autowired
	private UsuarioPerfilRepository perfilRepository;

	private static String entidade = "Usuário";

	public Usuario salvar(Usuario usuario) {
		try {
			String email = usuario.getEmail();
			boolean exists = repository.existsByEmail(email);
			if (exists) {
				throw new UniqueException(entidade);
			}

			UsuarioPerfil perfil = perfilRepository.findByCodigo("USUARIO");

			usuario.setPerfil(perfil);
		} catch (UniqueException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}

		return repository.save(usuario);
	}

	@Transactional
	public void atualizar(Usuario usuarioAtualizado) {
		Integer id = acharUsuarioAutenticado().getId();

		repository.findById(id).map(usuario -> {
			usuario = usuarioAtualizado;
			usuario.setId(id);
			return repository.save(usuario);
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, entidade + " não encontrado"));
	}

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		Usuario usuario = acharPorEmail(userName);

		return User.builder().username(usuario.getEmail()).password(usuario.getPassword()).roles("USER").build();
	}

	public Usuario acharPorId(Integer id) {
		return repository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, entidade + " não encontrado"));
	}

	public Usuario acharPorEmail(String email) {
		return repository.findByEmail(email)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, entidade + " não encontrado"));
	}

	public void existePorEmail(String email) {
		if (acharPorEmail(email) == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, entidade + " não encontrado");
		} else {
			throw new ResponseStatusException(HttpStatus.NO_CONTENT);
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