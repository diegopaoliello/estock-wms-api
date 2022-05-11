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
public class Venda extends AbstractEntity {
	@ManyToOne
	@JoinColumn(name = "id_status", nullable = false, foreignKey = @ForeignKey(name = "venda_status_fk"))
	@NotNull(groups = AfterValidInfo.class, message = "{campo.status.obrigatorio}")
	private VendaStatus status;

	@ManyToOne
	@JoinColumn(name = "id_cliente", nullable = false, foreignKey = @ForeignKey(name = "venda_cliente_fk"))
	@NotNull(groups = BeforeValidInfo.class, message = "{campo.cliente.obrigatorio}")
	private Cliente cliente;
}
