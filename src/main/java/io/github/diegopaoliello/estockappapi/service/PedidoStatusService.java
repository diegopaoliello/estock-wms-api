package io.github.diegopaoliello.estockappapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.diegopaoliello.estockappapi.model.entity.PedidoStatus;
import io.github.diegopaoliello.estockappapi.model.repository.PedidoStatusRepository;

@Service
public class PedidoStatusService {
	@Autowired
	private PedidoStatusRepository repository;

	public List<PedidoStatus> listar() {
		return repository.findAll();
	}
}