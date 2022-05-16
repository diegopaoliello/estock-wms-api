package io.github.diegopaoliello.estockappapi.model.entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioPerfilAutorizacao {

	private String nome;

	private List<String> acoes;
}
