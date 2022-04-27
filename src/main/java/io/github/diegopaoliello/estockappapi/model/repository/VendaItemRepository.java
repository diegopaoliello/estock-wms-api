package io.github.diegopaoliello.estockappapi.model.repository;

import io.github.diegopaoliello.estockappapi.model.entity.Venda;
import io.github.diegopaoliello.estockappapi.model.entity.VendaItem;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VendaItemRepository extends JpaRepository<VendaItem, Integer> {
	@Query(" select vi from VendaItem vi " + " where (vi.venda.id = :idVenda or :idVenda is null)")
	List<VendaItem> listar(@Param("idVenda") Integer idVenda);

	Optional<List<VendaItem>> findByVenda(Venda venda);
}