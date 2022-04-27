package io.github.diegopaoliello.estockappapi.exception;

public class VendaStatusException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public VendaStatusException(String acao) {
		super("Não foi possível " + acao + " o pedido");
	}
}