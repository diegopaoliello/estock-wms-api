package io.github.diegopaoliello.estockappapi.exception;

public class ProdutoAbaixoPrecoMedio extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ProdutoAbaixoPrecoMedio(String precoMedio) {
		super("Valor abaixo do preço mínimo. Preço Médio: " + precoMedio.replace(".", ","));
	}
}