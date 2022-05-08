package io.github.diegopaoliello.estockappapi.exception;

public class UsuarioDiferenteDoAutenticadoException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UsuarioDiferenteDoAutenticadoException() {
		super("Não está autenticado com esse usuário");
	}
}