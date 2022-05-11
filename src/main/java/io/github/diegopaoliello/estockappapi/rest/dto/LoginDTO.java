package io.github.diegopaoliello.estockappapi.rest.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import io.github.diegopaoliello.estockappapi.model.entity.AfterValidInfo;
import io.github.diegopaoliello.estockappapi.model.entity.UsuarioPerfil;
import io.github.diegopaoliello.estockappapi.model.entity.UsuarioTipoLogin;

@Data
@NoArgsConstructor
public class LoginDTO {
	@NotEmpty(message = "{campo.nome.obrigatorio}")
	private String nome;

	@NotEmpty(message = "{campo.sobrenome.obrigatorio}")
	private String sobrenome;

	@NotEmpty(message = "{campo.email.obrigatorio}")
	private String email;

	@NotEmpty(message = "{campo.senha.obrigatorio}")
	private String password;

	@NotNull(message = "{campo.tipo_login.obrigatorio}")
	private UsuarioTipoLogin tipoLogin;
}