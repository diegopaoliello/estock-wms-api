package io.github.diegopaoliello.estockappapi.model.repository;

import io.github.diegopaoliello.estockappapi.model.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

}