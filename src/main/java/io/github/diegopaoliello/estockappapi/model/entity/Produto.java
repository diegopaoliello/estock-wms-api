package io.github.diegopaoliello.estockappapi.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class Produto extends AbstractEntity {
	@Column(nullable = false, length = 500)
	@NotEmpty(message = "{campo.descricao.obrigatorio}")
	private String descricao;

	@Column(nullable = false, length = 50, unique = true)
	@NotEmpty(message = "{campo.codigo.obrigatorio}")
	private String codigo;

	@ManyToOne
	@NotNull(message = "{campo.categoria.obrigatorio}")
	@JoinColumn(name = "id_categoria", foreignKey = @ForeignKey(name = "produto_categoria_fk"))
	private Categoria categoria;

	@ManyToOne
	@NotNull(message = "{campo.unidadeMedida.obrigatorio")
	@JoinColumn(name = "id_unidade_medida")
	private UnidadeMedida unidadeMedida;
}
