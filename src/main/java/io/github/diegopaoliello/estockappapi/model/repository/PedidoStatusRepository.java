package io.github.diegopaoliello.estockappapi.model.repository;

import io.github.diegopaoliello.estockappapi.model.entity.PedidoStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoStatusRepository extends JpaRepository<PedidoStatus, Integer> {
	PedidoStatus findByCodigo(String codigo);
}