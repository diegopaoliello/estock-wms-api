package io.github.diegopaoliello.estockappapi.model.repository;

import io.github.diegopaoliello.estockappapi.model.entity.Pedido;
import io.github.diegopaoliello.estockappapi.model.entity.PedidoStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
	@Modifying
	@Query("update Pedido p set p.status = :status where p.id = :id")
	void updateStatus(@Param("id") Integer id, @Param("status") PedidoStatus status);
}