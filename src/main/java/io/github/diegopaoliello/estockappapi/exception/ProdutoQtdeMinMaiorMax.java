package io.github.diegopaoliello.estockappapi.exception;

public class ProdutoQtdeMinMaiorMax extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ProdutoQtdeMinMaiorMax() {
		super("Quantidade Mínima não pode ser maior que Quantidade Máxima");
	}
}