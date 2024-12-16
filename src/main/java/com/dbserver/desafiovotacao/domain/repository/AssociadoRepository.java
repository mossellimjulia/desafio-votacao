package com.dbserver.desafiovotacao.domain.repository;

import com.dbserver.desafiovotacao.domain.model.Associado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface AssociadoRepository extends JpaRepository<Associado, Long> {
   Optional<Associado> findById(Long id);
}
