package io.github.diegopaoliello.estockappapi.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class PedidoItem extends AbstractEntity {
	@Column(nullable = false)
	private Integer quantidade;

	@Column(nullable = false)
	private Double preco;

	private Double desconto;

	@ManyToOne
	@JoinColumn(name = "id_pedido")
	Pedido pedido;

	@ManyToOne
	@JoinColumn(name = "id_produto")
	Produto produto;
}
