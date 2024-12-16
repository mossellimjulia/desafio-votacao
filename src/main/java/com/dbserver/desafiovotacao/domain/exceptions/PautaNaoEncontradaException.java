package com.dbserver.desafiovotacao.domain.exceptions;

public class PautaNaoEncontradaException extends RuntimeException {
    public PautaNaoEncontradaException(Long pautaId) {
        super("Pauta não encontrada com o Id: " + pautaId);
    }
}
