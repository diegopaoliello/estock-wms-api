package io.github.diegopaoliello.estockappapi.model.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

	@Transient
	@Setter(AccessLevel.NONE)
	private boolean permiteInserirEntradaEstoque = false;

	@Transient
	@Setter(AccessLevel.NONE)
	private List<UsuarioPerfilAutorizacao> autorizacoes;

	public boolean isPermiteInserirEntradaEstoque() {
		if (this.codigo.equals("GERENTE")) {
			this.permiteInserirEntradaEstoque = true;
		} else {
			this.permiteInserirEntradaEstoque = false;
		}

		return this.permiteInserirEntradaEstoque;
	}

	public List<UsuarioPerfilAutorizacao> getAutorizacoes() {
		List<UsuarioPerfilAutorizacao> autorizacoes = new ArrayList<UsuarioPerfilAutorizacao>();
		UsuarioPerfilAutorizacao autorizacao;

		if (!this.codigo.equals("USUARIO")) {
			autorizacao = new UsuarioPerfilAutorizacao("MENU_PRINCIPAL", Arrays.asList("VISUALIZAR"));
			autorizacoes.add(autorizacao);

			if (!this.codigo.equals("OPERADOR")) {
				autorizacao = new UsuarioPerfilAutorizacao("MENU_CADASTROS",
						Arrays.asList("INSERIR", "EDITAR", "EXCLUIR", "VISUALIZAR"));
				autorizacoes.add(autorizacao);

				autorizacao = new UsuarioPerfilAutorizacao("MENU_OPER_ENTRADA",
						Arrays.asList("INSERIR", "EDITAR", "EXCLUIR", "VISUALIZAR"));
				autorizacoes.add(autorizacao);

				autorizacao = new UsuarioPerfilAutorizacao("MENU_OPER_SAIDA",
						Arrays.asList("INSERIR", "EDITAR", "EXCLUIR", "VISUALIZAR"));
				autorizacoes.add(autorizacao);
			} else {
				autorizacao = new UsuarioPerfilAutorizacao("MENU_OPER_ENTRADA", Arrays.asList("VISUALIZAR"));
				autorizacoes.add(autorizacao);

				autorizacao = new UsuarioPerfilAutorizacao("MENU_OPER_SAIDA", Arrays.asList("VISUALIZAR"));
				autorizacoes.add(autorizacao);
			}

			if (this.codigo.equals("GERENTE")) {
				autorizacao = new UsuarioPerfilAutorizacao("ENTRADA_ESTOQUE", Arrays.asList("INSERIR", "VISUALIZAR"));
				autorizacoes.add(autorizacao);
			}
		}

		return autorizacoes;
	}

}
