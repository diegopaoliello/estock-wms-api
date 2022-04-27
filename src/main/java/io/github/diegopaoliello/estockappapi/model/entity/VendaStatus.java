package io.github.diegopaoliello.estockappapi.model.entity;

public enum VendaStatus {
	ABERTO("Em aberto"), APROVADO("Aprovado"), REPROVADO("Reprovado"), CONCLUIDO("Conclúdo");

	private String descricao;

	private VendaStatus(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public String getAcao() {
		String acao = null;

		switch (this) {
		case APROVADO:
			acao = "aprovar";
			break;
		case REPROVADO:
			acao = "reprovar";
			break;
		case CONCLUIDO:
			acao = "concluir";
			break;

		default:
			break;
		}
		return acao;
	}
}
