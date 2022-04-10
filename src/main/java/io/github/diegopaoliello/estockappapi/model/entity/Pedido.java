package io.github.diegopaoliello.estockappapi.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class Pedido extends AbstractEntity {
	@Enumerated(value = EnumType.STRING)
	@Column(nullable = false, length = 50)
	@NotNull(groups = AfterValidInfo.class, message = "{campo.status.obrigatorio}")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private PedidoStatus status;

	@ManyToOne
	@JoinColumn(name = "id_fornecedor", foreignKey = @ForeignKey(name = "pedido_fornecedor_fk"))
	@NotNull(groups = BeforeValidInfo.class, message = "{campo.fornecedor.obrigatorio}")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Fornecedor fornecedor;
}
