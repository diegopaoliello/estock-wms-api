package io.github.diegopaoliello.estockappapi.model.repository;

import io.github.diegopaoliello.estockappapi.model.entity.EstoqueEntrada;
import io.github.diegopaoliello.estockappapi.model.entity.Produto;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstoqueEntradaRepository extends JpaRepository<EstoqueEntrada, Integer> {
	Optional<List<EstoqueEntrada>> findByProduto(Produto produto, Pageable pageable);
}