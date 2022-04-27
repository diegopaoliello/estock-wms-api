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
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "id_venda_item", name = "venda_item_uk"))
public class EstoqueSaida extends AbstractEntity {
	@ManyToOne
	@JoinColumn(name = "id_venda_item", foreignKey = @ForeignKey(name = "entrada_venda_item_fk"))
	@NotNull(message = "{campo.itemVenda.obrigatorio}")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private VendaItem itemVenda;
}
