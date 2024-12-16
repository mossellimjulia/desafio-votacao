package com.dbserver.desafiovotacao.domain.service;

import com.dbserver.desafiovotacao.api.model.input.VotoInput;
import com.dbserver.desafiovotacao.domain.exceptions.AssociadoNaoEncontradoException;
import com.dbserver.desafiovotacao.domain.exceptions.PautaNaoEncontradaException;
import com.dbserver.desafiovotacao.domain.exceptions.VotoJaValidadoException;
import com.dbserver.desafiovotacao.domain.model.Associado;
import com.dbserver.desafiovotacao.domain.model.Pauta;
import com.dbserver.desafiovotacao.domain.model.Voto;
import com.dbserver.desafiovotacao.domain.repository.AssociadoRepository;
import com.dbserver.desafiovotacao.domain.repository.PautaRepository;
import com.dbserver.desafiovotacao.domain.repository.VotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VotoService {

    @Autowired
    private PautaRepository pautaRepository;

    @Autowired
    private VotoRepository votoRepository;

    @Autowired
    private AssociadoRepository associadoRepository;


    public String salvarVoto(VotoInput votoInput) {

        Long pautaId = votoInput.getPautaId();
        Long associadoId = votoInput.getAssociadoId();
        String voto = votoInput.getVoto().toLowerCase();

        // Verificando se a opção de voto é válida
        if (!"sim".equals(voto) && !"nao".equals(voto)) {
            throw new IllegalArgumentException("Voto inválido! A opção deve ser 'sim' ou 'nao'.");
        }

        Optional<Pauta> pautaOptional = pautaRepository.findById(pautaId);
        if (!pautaOptional.isPresent()) {
            throw new PautaNaoEncontradaException(pautaId);
        }

        Optional<Associado> associadoOptional = associadoRepository.findById(associadoId);
        if (!associadoOptional.isPresent()) {
            throw new AssociadoNaoEncontradoException(associadoId);
        }

        Optional<Voto> votoOptional = votoRepository.findByPautaIdAndAssociadoId(pautaId, associadoId);
        if (votoOptional.isPresent()) {
            throw new VotoJaValidadoException(pautaId, associadoId);
        }

        Voto votoEntity = new Voto();
        votoEntity.setPauta(pautaOptional.get());
        votoEntity.setAssociado(associadoOptional.get());
        votoEntity.setVoto(voto);

        votoRepository.save(votoEntity);

        return "Voto registrado com sucesso!";
    }

}


