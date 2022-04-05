package io.github.diegopaoliello.estockappapi.rest.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import io.github.diegopaoliello.estockappapi.model.entity.Fornecedor;
import io.github.diegopaoliello.estockappapi.model.entity.PedidoItem;

@Data
@NoArgsConstructor
public class PedidoDTO {
	private Fornecedor fornecedor;

	private List<PedidoItem> itens;
}