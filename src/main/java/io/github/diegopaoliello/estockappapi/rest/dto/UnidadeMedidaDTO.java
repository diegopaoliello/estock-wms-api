package io.github.diegopaoliello.estockappapi.rest.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UnidadeMedidaDTO {
	private String descricao;

	private String sigla;
}