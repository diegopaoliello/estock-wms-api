package io.github.diegopaoliello.estockappapi.model.repository;

import io.github.diegopaoliello.estockappapi.model.entity.EstoqueEntrada;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstoqueEntradaRepository extends JpaRepository<EstoqueEntrada, Integer> {
}