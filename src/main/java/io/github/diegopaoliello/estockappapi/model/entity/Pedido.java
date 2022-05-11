package io.github.diegopaoliello.estockappapi.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class Pedido extends AbstractEntity {
	@ManyToOne
	@JoinColumn(name = "id_status", nullable = false, foreignKey = @ForeignKey(name = "pedido_status_fk"))
	@NotNull(groups = AfterValidInfo.class, message = "{campo.status.obrigatorio}")
	private PedidoStatus status;

	@ManyToOne
	@JoinColumn(name = "id_fornecedor", nullable = false, foreignKey = @ForeignKey(name = "pedido_fornecedor_fk"))
	@NotNull(groups = BeforeValidInfo.class, message = "{campo.fornecedor.obrigatorio}")
	private Fornecedor fornecedor;
}
