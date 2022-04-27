package io.github.diegopaoliello.estockappapi.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "id_pedido_item", name = "pedido_item_uk"))
public class EstoqueEntrada extends AbstractEntity {
	@ManyToOne
	@JoinColumn(name = "id_pedido_item", foreignKey = @ForeignKey(name = "entrada_pedido_item_fk"))
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private PedidoItem itemPedido;

	@ManyToOne
	@JoinColumn(name = "id_produto", nullable = false, foreignKey = @ForeignKey(name = "pedido_item_produto_fk"))
	@NotNull(groups = BeforeValidInfo.class, message = "{campo.produto.obrigatorio}")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Produto produto;

	@Column(nullable = false)
	@NotNull(groups = BeforeValidInfo.class, message = "{campo.quantidade.obrigatorio}")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private BigDecimal quantidade;

	@Column(nullable = false)
	@NotNull(groups = BeforeValidInfo.class, message = "{campo.preco.obrigatorio}")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private BigDecimal preco;

	@Column
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String observacao;
}
