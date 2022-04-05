package io.github.diegopaoliello.estockappapi.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;

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
	@JoinColumn(name = "id_fornecedor", foreignKey = @ForeignKey(name = "pedido_fornecedor_fk"))
	@NotNull(message = "{campo.fornecedor.obrigatorio}")
	private Fornecedor fornecedor;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "pedido")
	@JsonBackReference
	private List<PedidoItem> itens;
}
