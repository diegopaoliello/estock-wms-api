package io.github.diegopaoliello.estockappapi.model.repository;

import io.github.diegopaoliello.estockappapi.model.entity.ServicoPrestado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServicoPrestadoRepository extends JpaRepository<ServicoPrestado, Integer> {

	@Query(" select s from ServicoPrestado s join s.cliente c "
			+ " where upper( c.nomeFantasia ) like upper( :nome ) and (MONTH(s.data) = :mes or :mes IS NULL)")
	List<ServicoPrestado> findByNomeFantasiaClienteAndMes(@Param("nome") String nome, @Param("mes") Integer mes);
}