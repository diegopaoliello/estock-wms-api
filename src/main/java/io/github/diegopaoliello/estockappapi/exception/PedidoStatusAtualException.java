package io.github.diegopaoliello.estockappapi.exception;

public class PedidoStatusAtualException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PedidoStatusAtualException(String status) {
		super("Pedido já está " + status);
	}
}