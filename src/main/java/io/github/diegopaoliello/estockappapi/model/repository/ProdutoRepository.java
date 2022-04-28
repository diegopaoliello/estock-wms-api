package io.github.diegopaoliello.estockappapi.model.repository;

import io.github.diegopaoliello.estockappapi.model.entity.Produto;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
	@Modifying
	@Query("update Produto p set p.precoMedio = :precoMedio where p.id = :id")
	void updatePrecoMedio(@Param("id") Integer id, @Param("precoMedio") BigDecimal precoMedio);
}