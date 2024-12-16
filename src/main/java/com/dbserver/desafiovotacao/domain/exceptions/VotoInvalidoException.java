package com.dbserver.desafiovotacao.domain.exceptions;

public class VotoInvalidoException extends RuntimeException {
    public VotoInvalidoException(String message) {
        super(message);
    }

    public VotoInvalidoException() {
        super("Voto inválido! A opção deve ser 'sim' ou 'nao'.");
    }
}
