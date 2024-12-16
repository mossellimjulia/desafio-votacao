package com.dbserver.desafiovotacao.domain.exceptions;

public class AssociadoNaoEncontradoException extends RuntimeException {
    public AssociadoNaoEncontradoException(Long associadoId) {
        super("Associado não encontrado: " + associadoId);
    }
}
