package io.github.diegopaoliello.estockappapi.service;

import io.github.diegopaoliello.estockappapi.model.entity.VendaStatus;
import io.github.diegopaoliello.estockappapi.model.repository.VendaStatusRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VendaStatusService  {

	@Autowired
	private VendaStatusRepository repository;

	public List<VendaStatus> listar() {
		return repository.findAll();
	}
}