package io.github.diegopaoliello.estockappapi.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "email", name = "email_uk"))
public class Usuario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GenericGenerator(name = "increment", strategy = "increment")
	private Integer id;

	@Column
	@NotEmpty(message = "{campo.nome.obrigatorio}")
	private String nome;

	@Column
	@NotEmpty(message = "{campo.sobrenome.obrigatorio}")
	private String sobrenome;

	@Column
	@NotEmpty(message = "{campo.email.obrigatorio}")
	private String email;

	@Column(name = "senha")
	@NotEmpty(message = "{campo.senha.obrigatorio}")
	private String password;

	@ManyToOne
	@JoinColumn(name = "id_perfil", nullable = false, foreignKey = @ForeignKey(name = "usuario_perfil_fk"))
	@NotNull(groups = AfterValidInfo.class, message = "{campo.perfil.obrigatorio}")
	private UsuarioPerfil perfil;

	@ManyToOne
	@NotNull(groups = BeforeValidInfo.class, message = "{campo.tipo_login.obrigatorio}")
	@JoinColumn(name = "id_tipo_login", nullable = false, foreignKey = @ForeignKey(name = "usuario_tipo_login_fk"))
	private UsuarioTipoLogin tipoLogin;

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