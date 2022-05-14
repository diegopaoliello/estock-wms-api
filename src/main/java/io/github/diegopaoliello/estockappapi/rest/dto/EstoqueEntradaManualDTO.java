package io.github.diegopaoliello.estockappapi.rest.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import io.github.diegopaoliello.estockappapi.model.entity.Produto;

@Data
@NoArgsConstructor
public class EstoqueEntradaManualDTO {
	@NotNull(message = "{campo.produto.obrigatorio}")
	private Produto produto;

	@NotNull(message = "{campo.quantidade.obrigatorio}")
	private BigDecimal quantidade;

	@NotNull(message = "{campo.preco.obrigatorio}")
	private BigDecimal preco;

	@NotNull(message = "{campo.justificativa.obrigatorio}")
	private String justificativa;
}