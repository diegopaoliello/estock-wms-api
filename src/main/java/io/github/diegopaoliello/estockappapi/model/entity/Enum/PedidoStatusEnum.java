package io.github.diegopaoliello.estockappapi.model.entity.Enum;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(using = PedidoStatusEnumSerializer.class)
public enum PedidoStatusEnum {
	ABERTO(1), APROVADO(2), REPROVADO(3), CONCLUIDO(4);

	private Integer id;

	private PedidoStatusEnum(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return this.id;
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
