package com.dbserver.desafiovotacao.domain.service;

import com.dbserver.desafiovotacao.api.model.input.AssociadoInput;
import com.dbserver.desafiovotacao.domain.exceptions.AssociadoNaoEncontradoException;
import com.dbserver.desafiovotacao.domain.model.Associado;
import com.dbserver.desafiovotacao.domain.repository.AssociadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AssociadoService {

    @Autowired
    private AssociadoRepository associadoRepository;

    public Associado buscarAssociadoPeloId(Long id) {
        Optional<Associado> associado = associadoRepository.findById(id);
        return associadoRepository.findById(id).orElseThrow(() -> new AssociadoNaoEncontradoException(id));
    }


    public List<Associado> listarAssociados() {
        return associadoRepository.findAll();
    }


    public Associado salvarAssociado(AssociadoInput associadoInput) {

        Associado associado = new Associado();
        associado.setCpf(associadoInput.getCpf());
        return associadoRepository.save(associado);
    }
}
