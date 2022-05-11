package io.github.diegopaoliello.estockappapi.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Check;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "codigo", name = "usuario_perfil_codigo_uk"))
@Check(constraints = "codigo IN ('USUARIO', 'COMPRADOR', 'VENDEDOR', 'OPERADOR', 'GERENTE')")
public class UsuarioPerfil {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GenericGenerator(name = "increment", strategy = "increment")
	private Integer id;

	@Column(nullable = false, length = 50)
	@NotNull(groups = BeforeValidInfo.class, message = "{campo.codigo.obrigatorio}")
	private String codigo;

	@Column(nullable = false, length = 50)
	@NotNull(groups = BeforeValidInfo.class, message = "{campo.descricao.obrigatorio}")
	private String descricao;

}
