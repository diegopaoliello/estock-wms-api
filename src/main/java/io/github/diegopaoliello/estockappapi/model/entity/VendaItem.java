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

import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class VendaItem extends AbstractEntity {
	@ManyToOne
	@JoinColumn(name = "id_venda", nullable = false, foreignKey = @ForeignKey(name = "venda_item_fk"))
	@NotNull(groups = AfterValidInfo.class, message = "{campo.venda.obrigatorio}")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Venda venda;

	@Column(nullable = false)
	@NotNull(groups = BeforeValidInfo.class, message = "{campo.quantidade.obrigatorio}")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private BigDecimal quantidade;

	@Column(nullable = false)
	@NotNull(groups = BeforeValidInfo.class, message = "{campo.preco.obrigatorio}")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private BigDecimal preco;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private BigDecimal desconto;

	@Column(nullable = false)
	@Setter(AccessLevel.NONE)
	@NotNull(groups = AfterValidInfo.class, message = "{campo.valor.obrigatorio}")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private BigDecimal valorFinal;

	@ManyToOne
	@JoinColumn(name = "id_produto", nullable = false, foreignKey = @ForeignKey(name = "venda_item_produto_fk"))
	@NotNull(groups = BeforeValidInfo.class, message = "{campo.produto.obrigatorio}")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Produto produto;

	private void calcularValorFinal() {
		if (desconto != null) {
			this.valorFinal = this.preco.subtract(desconto);
		} else {
			this.valorFinal = this.preco;
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
