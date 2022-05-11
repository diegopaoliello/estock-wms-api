package io.github.diegopaoliello.estockappapi.rest;

import io.github.diegopaoliello.estockappapi.model.entity.VendaStatus;
import io.github.diegopaoliello.estockappapi.service.VendaStatusService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vendas-status")
public class VendaStatusController {

	@Autowired
	private VendaStatusService service;

	@GetMapping
	public List<VendaStatus> listar() {
		return service.listar();
	}
}
