package io.github.diegopaoliello.estockappapi.exception;

public class VendaItemProdutoExistente extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public VendaItemProdutoExistente(String produto) {
		super("Já existe o item " + produto + " adicionado ao pedido");
	}
}