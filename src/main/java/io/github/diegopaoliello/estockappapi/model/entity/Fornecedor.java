package io.github.diegopaoliello.estockappapi.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.br.CNPJ;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Fornecedor {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GenericGenerator(name = "increment", strategy = "increment")
	private Integer id;

	@Column(nullable = false, length = 500)
	@NotEmpty(message = "{campo.razao_social.obrigatorio}")
	private String razaoSocial;

	@Column(nullable = false, length = 500)
	private String nomeFantasia;

	@Column(nullable = false, length = 14)
	@NotNull(message = "{campo.cnpj.obrigatorio}")
	@CNPJ(message = "{campo.cnpj.invalido}")
	private String cnpj;

	@Column(nullable = false, name = "data_cadastro", updatable = false)
	@JsonFormat(pattern = "dd/MM/yyyy:HH:mm")
	private LocalDateTime dataCadastro;

	@Column(nullable = false, name = "data_atualizacao")
	@JsonFormat(pattern = "dd/MM/yyyy:HH:mm")
	private LocalDateTime dataAtualizacao;

	@PrePersist
	public void beforeSave() {
		setDataCadastro(LocalDateTime.now());
		setDataAtualizacao(LocalDateTime.now());
	}

	@PreUpdate
	public void beforeUpdate() {
		setDataAtualizacao(LocalDateTime.now());
	}

}
