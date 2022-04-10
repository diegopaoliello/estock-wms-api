package io.github.diegopaoliello.estockappapi.model.repository;

import io.github.diegopaoliello.estockappapi.model.entity.Estoque;
import io.github.diegopaoliello.estockappapi.model.entity.Produto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EstoqueRepository extends JpaRepository<Estoque, Integer> {
	Optional<Estoque> findByProduto(Produto produto);

	@Query(" select e from Estoque e "
			+ " where (e.produto.id = :idProduto or :idProduto is null) and (e.quantidade = :quantidade or :quantidade is null)")
	Optional<List<Estoque>> listar(@Param("idProduto") Integer idProduto, @Param("quantidade") BigDecimal quantidade);
}