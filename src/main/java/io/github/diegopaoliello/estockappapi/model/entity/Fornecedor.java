package io.github.diegopaoliello.estockappapi.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CNPJ;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class Fornecedor extends AbstractEntity {
	@Column(nullable = false, length = 500)
	@NotEmpty(message = "{campo.razao_social.obrigatorio}")
	private String razaoSocial;

	@Column(nullable = false, length = 500)
	private String nomeFantasia;

	@Column(nullable = false, length = 14)
	@NotNull(message = "{campo.cnpj.obrigatorio}")
	@CNPJ(message = "{campo.cnpj.invalido}")
	private String cnpj;
}
