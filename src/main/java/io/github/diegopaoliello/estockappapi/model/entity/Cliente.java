package io.github.diegopaoliello.estockappapi.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.br.CNPJ;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class Cliente extends AbstractEntity {
	@Column(nullable = false, length = 500)
	@NotEmpty(message = "{campo.razao_social.obrigatorio}")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String razaoSocial;

	@Column(nullable = false, length = 500)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String nomeFantasia;

	@Column(nullable = false, length = 14)
	@NotNull(message = "{campo.cnpj.obrigatorio}")
	@CNPJ(message = "{campo.cnpj.invalido}")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String cnpj;
}
