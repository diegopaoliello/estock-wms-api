package io.github.diegopaoliello.estockappapi.model.repository;

import io.github.diegopaoliello.estockappapi.model.entity.UsuarioTipoLogin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioTipoLoginRepository extends JpaRepository<UsuarioTipoLogin, Integer> {
	UsuarioTipoLogin findByCodigo(String codigo);
}