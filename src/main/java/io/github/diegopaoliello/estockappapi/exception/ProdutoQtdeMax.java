package io.github.diegopaoliello.estockappapi.exception;

public class ProdutoQtdeMax extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ProdutoQtdeMax(String quantidadeMaxima) {
		super("Excedida Quantidade Máxima do produto. Quantidade Máxima: " + quantidadeMaxima.replace(".", ","));
	}
}