package io.github.diegopaoliello.estockappapi.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "sigla", name = "sigla_uk"))
public class UnidadeMedida extends AbstractEntity {
	@Column(nullable = false, length = 500)
	@NotEmpty(message = "{campo.descricao.obrigatorio}")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String descricao;

	@Column(nullable = false, length = 50)
	@NotEmpty(message = "{campo.sigla.obrigatorio}")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String sigla;
}
