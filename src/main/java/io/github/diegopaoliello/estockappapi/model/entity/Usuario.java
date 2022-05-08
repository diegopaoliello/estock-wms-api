package io.github.diegopaoliello.estockappapi.model.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "email", name = "email_uk"))
public class Usuario extends AbstractEntity {
	@Column
	@NotEmpty(message = "{campo.nome.obrigatorio}")
	private String nome;

	@Column
	@NotEmpty(message = "{campo.sobrenome.obrigatorio}")
	private String sobrenome;

	@Column
	@NotEmpty(message = "{campo.email.obrigatorio}")
	private String email;

	@Column(name = "senha")
	@NotEmpty(message = "{campo.senha.obrigatorio}")
	@Getter(AccessLevel.NONE)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String password;

	@JsonIgnore
	public String getSenha() {
		return this.password;
	}
}