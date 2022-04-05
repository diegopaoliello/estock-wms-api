package io.github.diegopaoliello.estockappapi.exception;

public class UsuarioAutenticadoException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UsuarioAutenticadoException() {
		super("Usuário não autenticado");
	}
}