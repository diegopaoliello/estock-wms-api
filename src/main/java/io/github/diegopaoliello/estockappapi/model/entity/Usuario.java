package io.github.diegopaoliello.estockappapi.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class Usuario extends AbstractEntity {
	@Column(unique = true, name = "login")
	@NotEmpty(message = "{campo.login.obrigatorio}")
	private String username;

	@Column(name = "senha")
	@NotEmpty(message = "{campo.senha.obrigatorio}")
	private String password;
}