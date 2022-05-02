package io.github.diegopaoliello.estockappapi.exception;

public class ProdutoQtdeMin extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ProdutoQtdeMin(String quantidadeMínima) {
		super("Excedida Quantidade Mínima do produto. Quantidade Minima: " + quantidadeMínima);
	}
}