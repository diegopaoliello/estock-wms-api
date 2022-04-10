package io.github.diegopaoliello.estockappapi.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class Estoque extends AbstractEntity {
	@ManyToOne
	@JoinColumn(name = "id_produto", nullable = false, foreignKey = @ForeignKey(name = "estoque_produto_fk"))
	@NotNull(message = "{campo.produto.obrigatorio}")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Produto produto;

	@Column(nullable = false)
	@NotNull(message = "{campo.quantidade.obrigatorio}")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private BigDecimal quantidade = BigDecimal.ZERO;
}
