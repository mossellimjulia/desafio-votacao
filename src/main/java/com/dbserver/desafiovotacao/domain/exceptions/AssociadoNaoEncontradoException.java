package com.dbserver.desafiovotacao.domain.exceptions;

public class AssociadoNaoEncontradoException extends RuntimeException {
    public AssociadoNaoEncontradoException(Long id) {
        super("Associado não encontrado com o Id: " + id);
    }
}
