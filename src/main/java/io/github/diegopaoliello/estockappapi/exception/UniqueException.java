package io.github.diegopaoliello.estockappapi.exception;

public class UniqueException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UniqueException(String entidade) {
		super(entidade  + " já existente");
	}
}