package io.github.diegopaoliello.estockappapi.rest;

import io.github.diegopaoliello.estockappapi.model.entity.PedidoStatus;

import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/pedidos/status")
public class PedidoStatusController {

	@GetMapping
	public List<String> listar() {
		return Arrays.asList(PedidoStatus.values());
	}
}
