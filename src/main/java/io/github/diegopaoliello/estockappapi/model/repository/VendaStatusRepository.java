package io.github.diegopaoliello.estockappapi.model.repository;

import io.github.diegopaoliello.estockappapi.model.entity.VendaStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendaStatusRepository extends JpaRepository<VendaStatus, Integer> {
	VendaStatus findByCodigo(String codigo);
}