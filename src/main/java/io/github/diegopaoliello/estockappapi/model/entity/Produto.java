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
public class Produto extends AbstractEntity {
	@Column(nullable = false, length = 500)
	@NotEmpty(message = "{campo.descricao.obrigatorio}")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String descricao;

	@Column(nullable = false, length = 50, unique = true)
	@NotEmpty(message = "{campo.codigo.obrigatorio}")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String codigo;

	@ManyToOne
	@NotNull(message = "{campo.categoria.obrigatorio}")
	@JoinColumn(name = "id_categoria", foreignKey = @ForeignKey(name = "produto_categoria_fk"))
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Categoria categoria;

	@ManyToOne
	@NotNull(message = "{campo.unidadeMedida.obrigatorio")
	@JoinColumn(name = "id_unidade_medida")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private UnidadeMedida unidadeMedida;

	@Column(nullable = false)
	@NotNull(groups = BeforeValidInfo.class, message = "{campo.quantidade_minima.obrigatorio}")
	private BigDecimal quantidadeMinima = BigDecimal.ZERO;

	@Column(nullable = false)
	@NotNull(groups = AfterValidInfo.class, message = "{campo.preco_medio.obrigatorio}")
	private BigDecimal precoMedio = BigDecimal.ZERO;
}
