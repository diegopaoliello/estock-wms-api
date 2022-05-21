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
@Check(constraints = "codigo IN ('USUARIO', 'ADMINISTRADOR', 'OPERADOR', 'GERENTE')")
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
	private List<UsuarioPerfilAutorizacao> autorizacoes = new ArrayList<UsuarioPerfilAutorizacao>();

	public List<UsuarioPerfilAutorizacao> getAutorizacoes() {
		if (!this.codigo.equals("USUARIO")) {
			setAutorizacoes("MENU_PRINCIPAL", Arrays.asList("VISUALIZAR"));

			if (this.codigo.equals("ADMINISTRADOR")) {
				setAutorizacoes("CADASTRO", Arrays.asList("INSERIR", "EDITAR", "EXCLUIR", "VISUALIZAR"));
				setAutorizacoes("PEDIDO", Arrays.asList("INSERIR", "EDITAR", "EXCLUIR", "VISUALIZAR"));
				setAutorizacoes("PEDIDO_STATUS", Arrays.asList("CONCLUIR"));
				setAutorizacoes("VENDA", Arrays.asList("INSERIR", "EDITAR", "EXCLUIR", "VISUALIZAR"));
				setAutorizacoes("ESTOQUE", Arrays.asList("VISUALIZAR"));
				setAutorizacoes("ESTOQUE_ENTRADA", Arrays.asList("VISUALIZAR"));
				setAutorizacoes("ESTOQUE_SAIDA", Arrays.asList("VISUALIZAR"));
			}

			if (this.codigo.equals("GERENTE")) {
				setAutorizacoes("CADASTRO", Arrays.asList("INSERIR", "EDITAR", "EXCLUIR", "VISUALIZAR"));
				setAutorizacoes("PEDIDO", Arrays.asList("INSERIR", "EDITAR", "EXCLUIR", "VISUALIZAR"));
				setAutorizacoes("PEDIDO_STATUS", Arrays.asList("APROVAR", "CONCLUIR"));
				setAutorizacoes("VENDA", Arrays.asList("INSERIR", "EDITAR", "EXCLUIR", "VISUALIZAR"));
				setAutorizacoes("ESTOQUE", Arrays.asList("VISUALIZAR"));
				setAutorizacoes("ESTOQUE_ENTRADA", Arrays.asList("INSERIR", "VISUALIZAR"));
				setAutorizacoes("ESTOQUE_SAIDA", Arrays.asList("VISUALIZAR"));
			}

			if (this.codigo.equals("OPERADOR")) {
				setAutorizacoes("PEDIDO", Arrays.asList("EDITAR", "VISUALIZAR"));
				setAutorizacoes("PEDIDO_STATUS", Arrays.asList("CONCLUIR"));
				setAutorizacoes("VENDA", Arrays.asList("EDITAR", "VISUALIZAR"));
				setAutorizacoes("ESTOQUE", Arrays.asList("VISUALIZAR"));
				setAutorizacoes("ESTOQUE_ENTRADA", Arrays.asList("VISUALIZAR"));
				setAutorizacoes("ESTOQUE_SAIDA", Arrays.asList("VISUALIZAR"));
			}
		}

		return this.autorizacoes;
	}

	private void setAutorizacoes(String nomeAutorizacao, List<String> acoes) {
		UsuarioPerfilAutorizacao autorizacao;
		autorizacao = new UsuarioPerfilAutorizacao(nomeAutorizacao, acoes);
		this.autorizacoes.add(autorizacao);
	}

}
