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
public class Venda extends AbstractEntity {
	@Enumerated(value = EnumType.STRING)
	@Column(nullable = false, length = 50)
	@NotNull(groups = AfterValidInfo.class, message = "{campo.status.obrigatorio}")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private VendaStatus status;

	@ManyToOne
	@JoinColumn(name = "id_cliente", foreignKey = @ForeignKey(name = "venda_cliente_fk"))
	@NotNull(groups = BeforeValidInfo.class, message = "{campo.cliente.obrigatorio}")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Cliente cliente;
}
