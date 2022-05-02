package io.github.diegopaoliello.estockappapi.exception;

public class ProdutoQtdeEstoque extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ProdutoQtdeEstoque(String quantidade) {
		super("Excedida quantidade disponível em estoque. Quantidade Estoque: " + quantidade.replace(".", ","));
	}
}