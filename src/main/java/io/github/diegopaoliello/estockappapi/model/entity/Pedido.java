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
	@Enumerated(value = EnumType.STRING)
	@Column(nullable = false, length = 50)
	@NotNull(message = "{campo.status.obrigatorio}")
	private PedidoStatus status;

	@ManyToOne
	@NotNull(message = "{campo.fornecedor.obrigatorio}")
	@JoinColumn(name = "id_fornecedor", foreignKey = @ForeignKey(name = "pedido_fornecedor_fk"))
	private Fornecedor fornecedor;
}
