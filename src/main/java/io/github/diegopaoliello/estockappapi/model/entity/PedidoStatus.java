package io.github.diegopaoliello.estockappapi.model.entity;

public enum PedidoStatus {
	ABERTO("Em aberto"), APROVADO("Aprovado"), REPROVADO("Reprovado"), CONCLUIDO("Conclúdo");

	private String descricao;

	private PedidoStatus(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return this.descricao;
	}
}
