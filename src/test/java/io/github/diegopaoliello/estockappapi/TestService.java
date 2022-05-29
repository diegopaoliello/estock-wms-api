package io.github.diegopaoliello.estockappapi;

import io.github.diegopaoliello.estockappapi.model.entity.PedidoStatus;
import io.github.diegopaoliello.estockappapi.model.entity.Usuario;
import io.github.diegopaoliello.estockappapi.model.entity.UsuarioPerfil;
import io.github.diegopaoliello.estockappapi.model.entity.UsuarioTipoLogin;
import io.github.diegopaoliello.estockappapi.model.entity.VendaStatus;
import io.github.diegopaoliello.estockappapi.model.repository.PedidoStatusRepository;
import io.github.diegopaoliello.estockappapi.model.repository.UsuarioPerfilRepository;
import io.github.diegopaoliello.estockappapi.model.repository.UsuarioRepository;
import io.github.diegopaoliello.estockappapi.model.repository.UsuarioTipoLoginRepository;
import io.github.diegopaoliello.estockappapi.model.repository.VendaStatusRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestService {
	private final UsuarioTipoLoginRepository tipoLoginRepository;
	private final UsuarioPerfilRepository perfilRepository;
	private final UsuarioRepository usuarioRepository;
	private final PedidoStatusRepository pedidoStatusRepository;
	private final VendaStatusRepository vendaStatusRepository;

	@Autowired
	public TestService(UsuarioTipoLoginRepository tipoLoginRepository, UsuarioPerfilRepository perfilRepository,
			UsuarioRepository usuarioRepository, PedidoStatusRepository pedidoStatusRepository,
			VendaStatusRepository vendaStatusRepository) {
		super();

		this.tipoLoginRepository = tipoLoginRepository;
		this.perfilRepository = perfilRepository;
		this.usuarioRepository = usuarioRepository;
		this.pedidoStatusRepository = pedidoStatusRepository;
		this.vendaStatusRepository = vendaStatusRepository;

		montarAmbienteTeste();
	}

	void montarAmbienteTeste() {
		UsuarioTipoLogin tipoLogin = new UsuarioTipoLogin(null, "PADRAO", "Padrão");
		tipoLogin = tipoLoginRepository.save(tipoLogin);

		UsuarioPerfil perfil = new UsuarioPerfil(null, "GERENTE", "Gerente", null);
		perfil = perfilRepository.save(perfil);

		Usuario usuario = new Usuario(null, "estock", "app", "user@estock.com", "123", perfil, tipoLogin, null, null);
		usuarioRepository.save(usuario);

		pedidoStatusRepository.save(new PedidoStatus(null, "ABERTO", "Aberto"));
		pedidoStatusRepository.save(new PedidoStatus(null, "APROVADO", "Aprovado"));
		pedidoStatusRepository.save(new PedidoStatus(null, "CONCLUIDO", "Concluído"));

		vendaStatusRepository.save(new VendaStatus(null, "ABERTO", "Aberto"));
		vendaStatusRepository.save(new VendaStatus(null, "APROVADO", "Aprovado"));
		vendaStatusRepository.save(new VendaStatus(null, "CONCLUIDO", "Concluído"));
	}
}