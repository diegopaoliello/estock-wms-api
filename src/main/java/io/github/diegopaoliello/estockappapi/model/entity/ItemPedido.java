package io.github.diegopaoliello.estockappapi.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemPedido implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GenericGenerator(name = "increment", strategy = "increment")
	private Integer id;
	
	@Column(nullable = false)
	private Integer quantidade;
	
	@Column(nullable = false)
	private Double preco;
	
	private Double desconto;
	
	@ManyToOne
	@JoinColumn(name = "id_pedido")
	@JsonIgnore
	Pedido pedido;
	
	@ManyToOne
	@JoinColumn(name = "id_produto")
	Produto produto;
	
	@Column(nullable = false, name = "data_cadastro", updatable = false)
	@JsonFormat(pattern = "dd/MM/yyyy:HH:mm")
	private LocalDateTime dataCadastro;

	@PrePersist
	public void beforeSave() {
		setDataCadastro(LocalDateTime.now());
	}
}
 	