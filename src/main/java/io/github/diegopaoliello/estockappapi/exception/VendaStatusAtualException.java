package io.github.diegopaoliello.estockappapi.exception;

public class VendaStatusAtualException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public VendaStatusAtualException(String status) {
		super("Venda já está " + status);
	}
}