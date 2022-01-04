package io.github.diegopaoliello.estockappapi.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Produto implements Serializable {
	private static final long serialVersionUID = 8459561752437007351L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GenericGenerator(name = "increment", strategy = "increment")
	private Integer id;

	@Column(nullable = false, length = 500)
	@NotEmpty(message = "{campo.descricao.obrigatorio}")
	private String descricao;

	@Column(nullable = false, length = 50)
	@NotEmpty(message = "{campo.codigo.obrigatorio}")
	private String codigo;

	@ManyToOne
	@JoinColumn(name = "id_categoria")
	private Categoria categoria;

	@ManyToOne
	@JoinColumn(name = "id_unidade_medida")
	private UnidadeMedida unidadeMedida;

	@JsonIgnore
	@OneToMany(mappedBy = "produto")
	private Set<ItemPedido> itens = new HashSet<>();

	@Column(nullable = false, name = "data_cadastro", updatable = false)
	@JsonFormat(pattern = "dd/MM/yyyy:HH:mm")
	private LocalDateTime dataCadastro;

	@PrePersist
	public void beforeSave() {
		setDataCadastro(LocalDateTime.now());
	}
}
