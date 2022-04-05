package io.github.diegopaoliello.estockappapi.exception;

public class PedidoStatusException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PedidoStatusException(String acao) {
		super("Não foi possível " + acao + " o pedido");
	}
}