package io.github.diegopaoliello.estockappapi.service;

import io.github.diegopaoliello.estockappapi.model.entity.EstoqueEntrada;
import io.github.diegopaoliello.estockappapi.model.entity.Usuario;
import io.github.diegopaoliello.estockappapi.model.repository.EstoqueEntradaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class EstoqueEntradaService extends AbstractService<EstoqueEntrada, EstoqueEntradaRepository> {
	@Autowired
	private UsuarioService usuarioService;

	public EstoqueEntradaService() {
		super(EstoqueEntrada.class);
	}

	@Override
	public EstoqueEntrada salvar(EstoqueEntrada entradaEstoque) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userName = authentication.getName();
		Usuario usuario = usuarioService.findByUsername(userName);

		entradaEstoque.setUsuario(usuario);

		return super.salvar(entradaEstoque);
	}
}