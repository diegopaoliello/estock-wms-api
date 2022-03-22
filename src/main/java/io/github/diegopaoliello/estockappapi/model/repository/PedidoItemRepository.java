package io.github.diegopaoliello.estockappapi.model.repository;

import io.github.diegopaoliello.estockappapi.model.entity.PedidoItem;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoItemRepository extends JpaRepository<PedidoItem, Integer> {
	@Query(" select pi from PedidoItem pi "
			+ " where (pi.pedido.id = :idPedido or :idPedido is null)")
	List<PedidoItem> listar(@Param("idPedido") Integer idPedido);
}