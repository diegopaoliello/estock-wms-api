package io.github.diegopaoliello.estockappapi.rest;

import io.github.diegopaoliello.estockappapi.model.entity.PedidoStatus;
import io.github.diegopaoliello.estockappapi.service.PedidoStatusService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos-status")
public class PedidoStatusController {

	@Autowired
	private PedidoStatusService service;

	@GetMapping
	public List<PedidoStatus> listar() {
		return service.listar();
	}
}
