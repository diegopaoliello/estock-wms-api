package io.github.diegopaoliello.estockappapi.exception;

public class PedidoItemProdutoExistente extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PedidoItemProdutoExistente(String produto) {
		super("Já existe o item " + produto + " adicionado ao pedido");
	}
}