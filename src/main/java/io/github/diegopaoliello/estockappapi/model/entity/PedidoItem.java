package io.github.diegopaoliello.estockappapi.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;
import lombok.Setter;

import java.math.BigDecimal;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class PedidoItem extends AbstractEntity {
	@Column(nullable = false)
	private BigDecimal quantidade;

	@Column(nullable = false)
	private BigDecimal preco;

	private BigDecimal desconto;

	@Column(nullable = false)
	@Setter(AccessLevel.NONE)
	private BigDecimal valorFinal;

	@ManyToOne
	@JoinColumn(name = "id_pedido", nullable = false, foreignKey = @ForeignKey(name = "pedido_item_fk"))
	@NotNull(message = "{campo.pedido.obrigatorio}")
	Pedido pedido;

	@ManyToOne
	@JoinColumn(name = "id_produto", nullable = false, foreignKey = @ForeignKey(name = "pedido_item_produto_fk"))
	@NotNull(message = "{campo.produto.obrigatorio}")
	Produto produto;

	private void calcularValorFinal() {
		if (desconto != null) {
			this.valorFinal = this.preco.subtract(desconto);
		}
	}

	@Override
	public void beforeSave() {
		calcularValorFinal();
		super.beforeSave();
	}

	@Override
	public void beforeUpdate() {
		calcularValorFinal();
		super.beforeUpdate();
	}

}
