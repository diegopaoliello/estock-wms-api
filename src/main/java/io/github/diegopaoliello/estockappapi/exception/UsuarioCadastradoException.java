package io.github.diegopaoliello.estockappapi.exception;

public class UsuarioCadastradoException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UsuarioCadastradoException(String login) {
		super("Usuário já cadastrado para o login " + login);
	}
}