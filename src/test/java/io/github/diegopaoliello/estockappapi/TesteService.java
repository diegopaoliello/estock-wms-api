package io.github.diegopaoliello.estockappapi;

import io.github.diegopaoliello.estockappapi.model.entity.Usuario;
import io.github.diegopaoliello.estockappapi.model.entity.UsuarioPerfil;
import io.github.diegopaoliello.estockappapi.model.entity.UsuarioTipoLogin;
import io.github.diegopaoliello.estockappapi.model.repository.UsuarioPerfilRepository;
import io.github.diegopaoliello.estockappapi.model.repository.UsuarioRepository;
import io.github.diegopaoliello.estockappapi.model.repository.UsuarioTipoLoginRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TesteService {
	private final UsuarioTipoLoginRepository tipoLoginRepository;
	private final UsuarioPerfilRepository perfilRepository;
	private final UsuarioRepository usuarioRepository;

	@Autowired
	public TesteService(UsuarioTipoLoginRepository tipoLoginRepository, UsuarioPerfilRepository perfilRepository,
			UsuarioRepository usuarioRepository) {
		super();

		this.tipoLoginRepository = tipoLoginRepository;
		this.perfilRepository = perfilRepository;
		this.usuarioRepository = usuarioRepository;

		montarAmbienteTeste();
	}

	void montarAmbienteTeste() {
		UsuarioTipoLogin tipoLogin = new UsuarioTipoLogin(null, "PADRAO", "Padrão");
		tipoLogin = tipoLoginRepository.save(tipoLogin);

		UsuarioPerfil perfil = new UsuarioPerfil(null, "GERENTE", "Gerente", null);
		perfil = perfilRepository.save(perfil);

		Usuario usuario = new Usuario(null, "estock", "app", "user@estock.com", "123", perfil, tipoLogin, null, null);
		usuarioRepository.save(usuario);
	}
}