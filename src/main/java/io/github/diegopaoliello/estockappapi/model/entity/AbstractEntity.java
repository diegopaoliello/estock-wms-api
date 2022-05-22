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
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
@MappedSuperclass
public abstract class AbstractEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GenericGenerator(name = "increment", strategy = "increment")
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "id_usuario", nullable = false)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonBackReference
	private Usuario usuario;

	@Transient
	@Setter(AccessLevel.NONE)
	private boolean permiteInserir = false;

	@Transient
	@Setter(AccessLevel.NONE)
	private boolean permiteEditar = false;

	@Transient
	@Setter(AccessLevel.NONE)
	private boolean permiteExcluir = false;

	@Column(nullable = false, name = "data_cadastro", updatable = false)
	@JsonFormat(pattern = "dd/MM/yyyy:HH:mm")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private LocalDateTime dataCadastro;

	@Column(nullable = false, name = "data_atualizacao")
	@JsonFormat(pattern = "dd/MM/yyyy:HH:mm")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private LocalDateTime dataAtualizacao;

	public boolean isPermiteInserir() {
		if (usuario != null) {
			if (usuario.getPerfil().getCodigo().equals("USUARIO")) {
				this.permiteInserir = false;
			} else if (this.id == null) {
				this.permiteInserir = true;
			} else {
				this.permiteInserir = false;
			}
		} else {
			this.permiteInserir = false;
		}

		return this.permiteInserir;
	}

	public boolean isPermiteEditar() {
		if (usuario != null) {
			if (usuario.getPerfil().getCodigo().equals("USUARIO")) {
				this.permiteEditar = false;
			} else if (this.id != null) {
				this.permiteEditar = true;
			} else {
				this.permiteEditar = false;
			}
		} else {
			this.permiteEditar = false;
		}

		return this.permiteEditar;
	}

	public boolean isPermiteExcluir() {
		return this.permiteEditar;
	}

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
