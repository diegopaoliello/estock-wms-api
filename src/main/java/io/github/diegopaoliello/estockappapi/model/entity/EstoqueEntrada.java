package io.github.diegopaoliello.estockappapi.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class EstoqueEntrada extends AbstractEntity {
	@ManyToOne
	@JoinColumn(name = "id_pedido_item")
	private PedidoItem itemPedido;

	@ManyToOne
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;
}
