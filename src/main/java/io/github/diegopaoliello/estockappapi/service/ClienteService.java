package io.github.diegopaoliello.estockappapi.service;

import io.github.diegopaoliello.estockappapi.model.entity.Cliente;
import io.github.diegopaoliello.estockappapi.model.repository.ClienteRepository;

import org.springframework.stereotype.Service;

@Service
public class ClienteService extends AbstractService<Cliente, ClienteRepository> {
	public ClienteService() {
		super(Cliente.class);
	}
}