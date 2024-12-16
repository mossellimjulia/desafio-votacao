package com.dbserver.desafiovotacao.domain.repository;

import com.dbserver.desafiovotacao.domain.model.Voto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VotoRepository extends JpaRepository<Voto, Long> {
    Optional<Voto> findByPautaIdAndAssociadoId(Long pautaId, Long associadoId);

    long countByPautaIdAndVoto(Long pautaId, String voto);
}
