package io.github.diegopaoliello.estockappapi.rest.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import io.github.diegopaoliello.estockappapi.model.entity.ItemPedido;

@Data
@NoArgsConstructor
public class PedidoDTO {

	@NotNull(message = "{campo.cliente.obrigatorio}")
	private Integer idCliente;
	
	private Set<ItemPedido> itens = new HashSet<>();

}