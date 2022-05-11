package io.github.diegopaoliello.estockappapi.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Check;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "codigo", name = "pedido_status_codigo_uk"))
@Check(constraints = "codigo IN ('ABERTO', 'APROVADO', 'REPROVADO', 'CONCLUIDO')")
public class PedidoStatus {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GenericGenerator(name = "increment", strategy = "increment")
	private Integer id;

	@Column(nullable = false, length = 50)
	@NotNull(groups = BeforeValidInfo.class, message = "{campo.codigo.obrigatorio}")
	private String codigo;

	@Column(nullable = false, length = 50)
	@NotNull(groups = BeforeValidInfo.class, message = "{campo.descricao.obrigatorio}")
	private String descricao;

	public String getAcao() {
		String acao = null;

		switch (this.codigo) {
		case "APROVADO":
			acao = "aprovar";
			break;
		case "REPROVADO":
			acao = "reprovar";
			break;
		case "CONCLUIDO":
			acao = "concluir";
			break;

		default:
			break;
		}
		return acao;
	}
}
