package io.github.diegopaoliello.estockappapi;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import io.github.diegopaoliello.estockappapi.model.entity.Usuario;
import io.github.diegopaoliello.estockappapi.model.entity.UsuarioPerfil;
import io.github.diegopaoliello.estockappapi.model.entity.UsuarioTipoLogin;
import io.github.diegopaoliello.estockappapi.model.repository.UsuarioPerfilRepository;
import io.github.diegopaoliello.estockappapi.model.repository.UsuarioRepository;
import io.github.diegopaoliello.estockappapi.model.repository.UsuarioTipoLoginRepository;
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
abstract class AbstractTest {
	@Autowired
	private UsuarioTipoLoginRepository tipoLoginRepository;

	@Autowired
	private UsuarioPerfilRepository perfilRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@BeforeEach
	@Transactional
	void montarAmbiente() {
		System.out.println("AQUI teste");
		UsuarioTipoLogin tipoLogin = new UsuarioTipoLogin(null, "PADRAO", "Padrão");
		tipoLogin = tipoLoginRepository.save(tipoLogin);

		UsuarioPerfil perfil = new UsuarioPerfil(null, "GERENTE", "Gerente", null);
		perfil = perfilRepository.save(perfil);

		Usuario usuario = new Usuario(null, "estock", "app", "user@estock.com", "123", perfil, tipoLogin, null, null);
		usuarioRepository.save(usuario);
	}
}
