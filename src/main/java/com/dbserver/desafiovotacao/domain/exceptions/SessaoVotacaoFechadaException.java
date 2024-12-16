package com.dbserver.desafiovotacao.domain.exceptions;

public class SessaoVotacaoFechadaException extends RuntimeException {
    public SessaoVotacaoFechadaException(Long pautaId) {
        super("A sessão de votação já foi fechada para a pauta com o Id: " + pautaId);
    }
}
