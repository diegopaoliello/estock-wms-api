package io.github.diegopaoliello.estockappapi.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "id_pedido_item", name = "pedido_item_uk"))
public class EstoqueEntrada extends AbstractEntity {
	@ManyToOne
	@JoinColumn(name = "id_pedido_item", foreignKey = @ForeignKey(name = "entrada_pedido_item_fk"))
	@NotEmpty(message = "{campo.itemPedido.obrigatorio}")
	private PedidoItem itemPedido;
}
