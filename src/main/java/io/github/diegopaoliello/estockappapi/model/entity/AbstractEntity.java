package io.github.diegopaoliello.estockappapi.model.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;

@Data
@MappedSuperclass
public abstract class AbstractEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GenericGenerator(name = "increment", strategy = "increment")
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "id_usuario", foreignKey = @ForeignKey(name = "usuario_fk"))
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Usuario usuario;

	@Column(nullable = false, name = "data_cadastro", updatable = false)
	@JsonFormat(pattern = "dd/MM/yyyy:HH:mm")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private LocalDateTime dataCadastro;

	@Column(nullable = false, name = "data_atualizacao")
	@JsonFormat(pattern = "dd/MM/yyyy:HH:mm")
	@JsonInclude(JsonInclude.Include.NON_NULL)
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
