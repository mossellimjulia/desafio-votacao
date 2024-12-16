package com.dbserver.desafiovotacao.domain.exceptions;

public class VotoJaValidadoException extends RuntimeException {
    public VotoJaValidadoException(Long pautaId, Long associadoId) {
        super("O associado com o Id: "+ associadoId + " já registrou um voto para a pauta com o Id: " + pautaId);
    }
}
