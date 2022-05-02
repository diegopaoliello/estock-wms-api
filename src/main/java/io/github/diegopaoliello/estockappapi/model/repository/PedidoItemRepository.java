package io.github.diegopaoliello.estockappapi.model.repository;

import io.github.diegopaoliello.estockappapi.model.entity.Pedido;
import io.github.diegopaoliello.estockappapi.model.entity.PedidoItem;
import io.github.diegopaoliello.estockappapi.model.entity.Produto;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoItemRepository extends JpaRepository<PedidoItem, Integer> {
	@Query("select pi from PedidoItem pi where (pi.pedido.id = :idPedido or :idPedido is null)")
	List<PedidoItem> listar(@Param("idPedido") Integer idPedido);

	Optional<List<PedidoItem>> findByPedido(Pedido pedido);

	Optional<PedidoItem> findByPedidoAndProduto(Pedido pedido, Produto produto);

	@Query("select pi from PedidoItem pi INNER JOIN pi.pedido p where p.id <> :idPedido AND pi.produto.id = :idProduto AND p.status='ABERTO'")
	Optional<List<PedidoItem>> findByProdutoAndStatusEqualAberto(@Param("idPedido") Integer idPedido, @Param("idProduto") Integer idProduto);
}