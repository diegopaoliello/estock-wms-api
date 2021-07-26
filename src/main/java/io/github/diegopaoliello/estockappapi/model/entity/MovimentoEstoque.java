package io.github.diegopaoliello.estockappapi.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class MovimentoEstoque {
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
	@JoinColumn(name = "id_produto_categoria")
	private Categoria categoria;

	@ManyToOne
	@JoinColumn(name = "id_produto_unidade_medida")
	private UnidadeMedida unidadeMedida;

	@Column(nullable = false, name = "data_cadastro", updatable = false)
	@JsonFormat(pattern = "dd/MM/yyyy:HH:mm")
	private LocalDateTime dataCadastro;

	@PrePersist
	public void beforeSave() {
		setDataCadastro(LocalDateTime.now());
	}
}
