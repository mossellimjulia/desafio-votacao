package com.dbserver.desafiovotacao.domain.service;

import com.dbserver.desafiovotacao.api.model.input.PautaInput;
import com.dbserver.desafiovotacao.domain.exceptions.PautaNaoEncontradaException;
import com.dbserver.desafiovotacao.domain.exceptions.SessaoVotacaoFechadaException;
import com.dbserver.desafiovotacao.domain.model.Pauta;
import com.dbserver.desafiovotacao.domain.model.SituacaoPauta;
import com.dbserver.desafiovotacao.domain.repository.PautaRepository;
import com.dbserver.desafiovotacao.domain.repository.VotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PautaService {

    @Autowired
    private PautaRepository pautaRepository;

    @Autowired
    private VotoRepository votoRepository;

    public List<Pauta> listarPautas() {
        return pautaRepository.findAll();
    }

    public Pauta salvarPauta(PautaInput pautaInput) {

        Pauta pauta = new Pauta();
        pauta.setDescricao(pautaInput.getDescricao());
        pauta.setDataAbertura(LocalDateTime.now());
        pauta.setDataEncerramento(LocalDateTime.now());
        pauta.setSituacaoPauta(SituacaoPauta.VOTACAO_ABERTA);

        return pautaRepository.save(pauta);
    }

    public String contabilizarVotos(Long pautaId) {
        Optional<Pauta> pautaOptional = pautaRepository.findById(pautaId);
        if (!pautaOptional.isPresent()) {
            throw new PautaNaoEncontradaException(pautaId);
        }

        long votosSim = votoRepository.countByPautaIdAndVoto(pautaId, "sim");
        long votosNao = votoRepository.countByPautaIdAndVoto(pautaId, "nao");

        return "Resultado da votação: SIM = " + votosSim + ", NÃO = " + votosNao;
    }

    public String abrirSessaoVotacao(Long pautaId, int tempoAbertura) {
        Optional<Pauta> pautaOptional = pautaRepository.findById(pautaId);
        if (!pautaOptional.isPresent()) {
            throw new PautaNaoEncontradaException(pautaId);
        }

        Pauta pauta = pautaOptional.get();
        pauta.setSituacaoPauta(SituacaoPauta.VOTACAO_ABERTA);

        pauta.setDataAbertura(LocalDateTime.now());
        pauta.setDataEncerramento(pauta.getDataAbertura().plusMinutes(tempoAbertura));

        pautaRepository.save(pauta);
        return "Sessão de votação aberta com sucesso.";
    }

    public String fecharSessaoVotacao(Long pautaId) {
        Optional<Pauta> pautaOptional = pautaRepository.findById(pautaId);
        if (!pautaOptional.isPresent()) {
            throw new PautaNaoEncontradaException(pautaId);
        }

        Pauta pauta = pautaOptional.get();

        if (pauta.getSituacaoPauta() == SituacaoPauta.VOTACAO_FECHADA) {
            throw new SessaoVotacaoFechadaException(pautaId);
        }

        pauta.setSituacaoPauta(SituacaoPauta.VOTACAO_FECHADA);
        pautaRepository.save(pauta);

        return "Sessão de votação fechada com sucesso.";
    }

    public SituacaoPauta consultarSituacaoPauta(Long pautaId) {
        Optional<Pauta> pautaOptional = pautaRepository.findById(pautaId);
        if (!pautaOptional.isPresent()) {
            throw new PautaNaoEncontradaException(pautaId);
        }

        Pauta pauta = pautaOptional.get();
        return pauta.getSituacaoPauta();
    }
}
